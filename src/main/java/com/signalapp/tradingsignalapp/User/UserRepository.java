package com.signalapp.tradingsignalapp.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setHash(rs.getString("hash"));
            return user;
        }
    }

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM \"User\" ORDER BY id ASC";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    //Optional returns Object or None
    public Optional<User> getById(Integer id) {
        String sql = "SELECT * FROM \"User\" WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            // If no result is found, return None
            return Optional.empty();
        }
    }

    public Optional<User> getByUsername(String username) {
        String sql = "SELECT * FROM \"User\" WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), username);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            // If no result is found, return None
            return Optional.empty();
        }
    }

    public void create(User user) {
        String sql = "INSERT INTO \"User\"(username, hash, credit) VALUES (?, ?, ?)";
        jdbcTemplate.update( sql, user.getUsername(),user.getHash() );
    }

    public void update(User user, Integer id) {
        String sql = "UPDATE \"User\" SET username = ?, hash = ?, credit = ? WHERE id = ?";
        var rows_updated = jdbcTemplate.update( sql, user.getUsername(),user.getHash(),id);
        if (rows_updated == 0) {
            throw new EmptyResultDataAccessException("User with id " + id + " not found.", 1);
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM \"User\" WHERE id = ?";
        var rows_deleted = jdbcTemplate.update(sql, id);
        if (rows_deleted == 0) {
            throw new EmptyResultDataAccessException("User with id " + id + " not found.", 1);
        }
    }
}
