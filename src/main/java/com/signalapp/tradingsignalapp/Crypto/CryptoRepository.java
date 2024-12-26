package com.signalapp.tradingsignalapp.Crypto;

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
public class CryptoRepository {
    public static final Logger log = LoggerFactory.getLogger(CryptoRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final Crypto crypto;

    public static class CryptoDetailsMapper implements RowMapper<Crypto.CurrencyDetails> {
        @Override
        public Crypto.CurrencyDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            String symbol = rs.getString("symbol");
            String name = rs.getString("name");
            String description = rs.getString("description");
            String logoUrl = rs.getString("logoUrl");

            return new Crypto(null).new CurrencyDetails(symbol, name, description, logoUrl);
        }
    }

    public CryptoRepository(JdbcTemplate jdbcTemplate, Crypto crypto) {
        this.jdbcTemplate = jdbcTemplate;
        this.crypto = crypto;
        initializeDatabase();
    }

    public List<Crypto.CurrencyDetails> getAll() {
        String sql = "SELECT * FROM \"Crypto\" ORDER BY id ASC";
        return jdbcTemplate.query(sql, new CryptoDetailsMapper());
    }
    public Optional<Crypto.CurrencyDetails> getBySymbol(String symbol) {
        String sql = "SELECT * FROM \"Crypto\" WHERE symbol = ?";
        try {
            Crypto.CurrencyDetails cryptoDetails = jdbcTemplate.queryForObject(sql, new CryptoDetailsMapper(), symbol);
            return Optional.ofNullable(cryptoDetails);
        } catch (Exception e) {
            log.error("Error fetching Crypto by symbol: {}", symbol, e);
            return Optional.empty();
        }
    }
    public Optional<Crypto.CurrencyDetails> getById(Integer id) {
        String sql = "SELECT * FROM \"Crypto\" WHERE id = ?";
        try {
            Crypto.CurrencyDetails cryptoDetails = jdbcTemplate.queryForObject(sql, new CryptoDetailsMapper(), id);
            return Optional.ofNullable(cryptoDetails);
        } catch (Exception e) {
            log.error("Error fetching Crypto by ID: {}", id, e);
            return Optional.empty();
        }
    }
    public void create(Crypto.CurrencyDetails cryptoDetails) {
        String sql = "INSERT INTO \"Crypto\" (id, symbol, name, description, logoUrl) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                cryptoDetails.getSymbol(),
                cryptoDetails.getName(),
                cryptoDetails.getDescription(),
                cryptoDetails.getLogoUrl());
    }
    public void deleteById(Integer id) {
        String sql = "DELETE FROM \"Crypto\" WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public void updateById(Integer id, Crypto.CurrencyDetails cryptoDetails) {
        String sql = "UPDATE \"Crypto\" SET symbol = ?, name = ?, description = ?, logoUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                cryptoDetails.getSymbol(),
                cryptoDetails.getName(),
                cryptoDetails.getDescription(),
                cryptoDetails.getLogoUrl(),
                id);
    }

    public void initializeDatabase() {
        log.info("---Initializing database with dynamic data from Crypto class...");

        crypto.cryptoMap.forEach((id, details) -> {
            try {
                // Verify if the record already exists
                String checkSql = "SELECT COUNT(*) FROM \"Crypto\" WHERE id = ?";
                Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);

                if (count == null || count == 0) {
                    String insertSql = "INSERT INTO \"Crypto\" (id, symbol, name, description, logoUrl) VALUES (?, ?, ?, ?, ?)";
                    jdbcTemplate.update(insertSql, id, details.getSymbol(), details.getName(), details.getDescription(), details.getLogoUrl());
                } else {
//                    log.info("Skipping insert for Crypto ID: {} as it already exists.", id);
                }
            } catch (Exception e) {
                log.error("Error inserting data for Crypto ID: {}", id, e);
            }
        });

        log.info("Database initialization complete.");
    }
}
