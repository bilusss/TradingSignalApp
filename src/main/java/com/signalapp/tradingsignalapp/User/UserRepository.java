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
        String sql = "select * from \"User\" ORDER BY id ASC";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    //Optional returns Object or None
    public Optional<User> getById(Integer id) {
        String sql = "SELECT * FROM \"User\" WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            // If no result is found, return None
            return Optional.empty();
        }
    }
    public Optional<User> getByUsername(String username) {
        String sql = "SELECT * FROM \"User\" WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), username);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            // If no result is found, return None
            return Optional.empty();
        }
    }
    public void create(User user) {
        String sql = "INSERT INTO \"User\"(username, hash, credit) VALUES (?, ?, ?)";
        try {
            var created = jdbcTemplate.update( sql, user.getUsername(),user.getHash(),user.getCredit() );

        } catch (Exception e) {
            // Implement error handling to fronend

        }
    }
    public void update(User user, Integer id) {
        String sql = "UPDATE \"User\" SET username = ?, hash = ?, credit = ? WHERE id = ?";
        try {
            var updated = jdbcTemplate.update( sql, user.getUsername(),user.getHash(),user.getCredit(),id);

        } catch (Exception e) {
            // Implement error handling to fronend

        }
    }public void delete(Integer id) {
        // Maby add some validation so all records in db won't be affected after deletion
        String sql = "DELETE FROM \"User\" WHERE id = ?";
        try {
            var deleted = jdbcTemplate.update(sql, id);

        } catch (Exception e) {
            // Implement error handling to fronend

        }
    }
}
