package com.signalapp.tradingsignalapp.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class TransactionRepository {

    public static final Logger log = LoggerFactory.getLogger(TransactionRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public static class TransactionMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction transaction = new Transaction(1, "s", 1, 1, 2, 1.0, 100000.0, 100000.0, "aa");
            transaction.setId(rs.getInt("id"));
            transaction.setTitle(rs.getString("title"));
            transaction.setUserId(rs.getInt("userId"));
            transaction.setCryptoIdBought(rs.getInt("cryptoIdBought"));
            transaction.setCryptoIdSold(rs.getInt("cryptoIdSold"));
            transaction.setAmountBought(rs.getDouble("amountBought"));
            transaction.setAmountSold(rs.getDouble("amountSold"));
//            transaction.setCompletedAt(rs.getTimestamp("completed_at").toLocalDateTime());
            transaction.setPrice(rs.getDouble("price"));
            transaction.setDescription(rs.getString("description"));
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
        String sql = "SELECT * FROM \"Transactions\" ORDER BY id ASC";
        return jdbcTemplate.query(sql, new TransactionMapper());
    }

    public Optional<Transaction> getById(int id) {
        String sql = "SELECT * FROM \"Transactions\" WHERE \"id\" = ?";
        try {
            Transaction transaction = jdbcTemplate.queryForObject(sql, new TransactionMapper(), id);
            return Optional.ofNullable(transaction);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public void create(Transaction transaction) {
        String sql = "   INSERT INTO \"Transactions\"(title, userid, cryptoIdBought, cryptoIdSold, amountBought, amountSold, price, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?);;";
//        try {
            var created = jdbcTemplate.update(sql,transaction.getId(), transaction.getTitle(), transaction.getUserId(),
                    transaction.getCryptoIdBought(), transaction.getCryptoIdSold(), transaction.getAmountBought(),
                    transaction.getAmountSold(), transaction.getPrice(), transaction.getDescription());
//        } catch (Exception e){
//            // Implement error handling to frontend
//        }
    }
    public void update(Transaction transaction, int id) {
        String sql = "UPDATE \"Transactions\" SET id = ?, title = ?, userId = ?, crytoidbought = ?, cryptoidsold = ?, amountbought = ?, amountsold = ?, price = ?, description = ? WHERE id = ?";
        try {
            var updated = jdbcTemplate.update(sql,transaction.getId(), transaction.getTitle(), transaction.getUserId(),
                    transaction.getCryptoIdBought(), transaction.getCryptoIdSold(), transaction.getAmountBought(),
                    transaction.getAmountSold(),/*, transaction.getCompletedAt(), */transaction.getPrice(), transaction.getDescription());
        } catch (Exception e){
            // Implement error handling to frontend
        }
    }
    public void delete(int id) {
        // Maybe add some validation so all records in db won't be affected after deletion
        String sql = "DELETE FROM \"Transactions\" WHERE id = ?";
        try {
            var deleted = jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            // Implement error handling to frontend

        }
    }
    public Map<String, Float> getBalance(Integer userId){
        String sql = "SELECT * FROM \"Transactions\" WHERE userId = ?";
        Map<String, Float> balance;
        System.out.println("AAAAAAAA");
        List<Transaction> listOfTransactions = jdbcTemplate.query(sql, new TransactionMapper(), userId);
        System.out.println("BBBBBBB");
        for (Transaction transaction : listOfTransactions) {
            System.out.println(transaction);
        }
        return Map.of("cryptoIdBought", 0.0f, "cryptoIdSold", 0.0f);
    }
}
