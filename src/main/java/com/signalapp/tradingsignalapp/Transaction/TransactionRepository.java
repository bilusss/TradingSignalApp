package com.signalapp.tradingsignalapp.Transaction;
import com.signalapp.tradingsignalapp.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class TransactionRepository {

    public static final Logger log = LoggerFactory.getLogger(TransactionRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public static class TransactionMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction transaction = new Transaction();
            //TODO

            return transaction;
        }
    }


    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Pretty simple we make SQL query using jbdc ( its like sql alchemy )
    // We pass to the query, the sql string and Mapper
    // The query returns ResultSet, but it needs to know how to change it to user
    // That's why the mapper is used - simply instructions how to build user
    public List<Transaction> getAll() {
        String sql = "SELECT * FROM \"Transaction\" ORDER BY id AS";
        return jdbcTemplate.query(sql, new TransactionMapper());
    }
    // TODO: Implement rest based on User


}
