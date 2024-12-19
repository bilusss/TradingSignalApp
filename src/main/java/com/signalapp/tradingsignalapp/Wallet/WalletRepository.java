package com.signalapp.tradingsignalapp.Wallet;
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
public class WalletRepository {

    public static final Logger log = LoggerFactory.getLogger(WalletRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public static class WalletMapper implements RowMapper<Wallet> {
        @Override
        public Wallet mapRow(ResultSet rs, int rowNum) throws SQLException {
            Wallet wallet = new Wallet();
            wallet.setId(rs.getInt("id"));
            wallet.setUserId(rs.getInt("userId"));
            wallet.setCryptoId(rs.getInt("cryptoId"));
            wallet.setAmount(rs.getDouble("amount"));
            return wallet;
        }
    }


    public WalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Pretty simple we make SQL query using jbdc ( its like sql alchemy )
    // We pass to the query, the sql string and Mapper
    // The query returns ResultSet, but it needs to know how to change it to user
    // That's why the mapper is used - simply instructions how to build user
    public List<Wallet> getAll() {
        String sql = "select * from \"Wallet\" ORDER BY id ASC";
        return jdbcTemplate.query(sql, new WalletMapper());
    }

    //Optional returns Object or None
    public Optional<Wallet> getById(Integer id) {
        String sql = "SELECT * FROM \"Wallet\" WHERE id = ?";
        try {
            Wallet wallet = jdbcTemplate.queryForObject(sql, new WalletMapper(), id);
            return Optional.ofNullable(wallet);
        } catch (Exception e) {
            // If no result is found, return None
            return Optional.empty();
        }
    }

    public List <Wallet> getByUserId(Integer userId) {
        String sql = "SELECT * FROM \"Wallet\" WHERE userId = ?";
        return jdbcTemplate.query(sql, new WalletMapper(), userId);
    }

    public void create(Wallet wallet) {
        String sql = "INSERT INTO \"Wallet\"(userId, cryptoId, amount) VALUES (?, ?, ?)";
        try {
            var created = jdbcTemplate.update( sql, wallet.getId(), wallet.getCryptoId(), wallet.getAmount() );

        } catch (Exception e) {
            // Implement error handling to fronend
        }
    }
    public void update(Wallet wallet, Integer id) {
        String sql = "UPDATE \"Wallet\" SET userId = ?, cryptoId = ?, amount = ? WHERE id = ?";
        try {
            var updated = jdbcTemplate.update( sql, wallet.getId(), wallet.getCryptoId(), wallet.getAmount(),id);

        } catch (Exception e) {
            // Implement error handling to fronend

        }
    }public void delete(Integer id) {
        // Maby add some validation so all records in db won't be affected after deletion
        String sql = "DELETE FROM \"Wallet\" WHERE id = ?";
        try {
            var deleted = jdbcTemplate.update(sql, id);

        } catch (Exception e) {
            // Implement error handling to fronend

        }
    }
}
