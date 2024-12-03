package com.signalapp.tradingsignalapp.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// So basically I wanted to move the db management away from Controllers
// It would be too much code there, so Repository manages the SELECTS etc
@Repository
public class UserRepository {

    public static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setHash(rs.getString("hash"));
            user.setCredit(rs.getDouble("credit"));
            return user;
        }
    }


    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Pretty simple we make SQL query using jbdc ( its like sql alchemy )
    // We pass to the query, the sql string and Mapper
    // The query returns ResultSet, but it needs to know how to change it to user
    // That's why the mapper is used - simply instructions how to build user
    public List<User> getAll() {
        String sql = "select * from \"User\"";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    //Optional returns Object or None
    public Optional<User> getById(Integer id) {
        String sql = String.format("select * from \"User\" where id = %s", id);
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            // If no result is found catches EmptyResultDataAccessException and returns None
            return Optional.empty();
        }

    }
    public void create(User user) {
        // TODO
    }
    public void update(User user) {
        // TODO
    }public void delete(User user) {
        // TODO
    }
}
