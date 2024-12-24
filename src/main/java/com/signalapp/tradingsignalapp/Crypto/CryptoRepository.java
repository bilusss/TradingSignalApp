//package com.signalapp.tradingsignalapp.Crypto;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//
//// So basically I wanted to move the db management away from Controllers
//// It would be too much code there, so Repository manages the SELECTS etc
//@Repository
//public class CryptoRepository {
//
//    public static final Logger log = LoggerFactory.getLogger(CryptoRepository.class);
//    private final JdbcTemplate jdbcTemplate;
//
//    public static class CryptoMapper implements RowMapper<Crypto> {
//        @Override
//        public Crypto mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Crypto crypto = new Crypto();
//            crypto.setId(rs.getInt("id"));
//            crypto.setName(rs.getString("name"));
//            crypto.setSymbol(rs.getString("symbol"));
//            crypto.setDescription(rs.getString("description"));
//            crypto.setLogoUrl(rs.getString("logoUrl"));
//            return crypto;
//        }
//    }
//
//
//    public CryptoRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Crypto> getAll() {
//        String sql = "select * from \"Crypto\" ORDER BY id ASC";
//        return jdbcTemplate.query(sql, new CryptoMapper());
//    }
//
//    // Optional returns Object or None
//    public Optional<Crypto> getById(Integer id) {
//        String sql = "SELECT * FROM \"Crypto\" WHERE id = ?";
//        try {
//            Crypto crypto = jdbcTemplate.queryForObject(sql, new CryptoMapper(), id);
//            return Optional.ofNullable(crypto);
//        } catch (Exception e) {
//            // If no result is found, return None
//            return Optional.empty();
//        }
//    }
//    public Optional<Crypto> getBySymbol(String symbol) {
//        String sql = "SELECT * FROM \"Crypto\" WHERE symbol = ?";
//        try {
//            Crypto crypto = jdbcTemplate.queryForObject(sql, new CryptoMapper(), symbol);
//            return Optional.ofNullable(crypto);
//        } catch (Exception e) {
//            // If no result is found, return None
//            return Optional.empty();
//        }
//    }
//    public void create(Crypto crypto) {
//        String sql = "INSERT INTO \"Crypto\"(name, symbol, description, logoUrl) VALUES (?, ?, ?, ?)";
//        try {
//            var created = jdbcTemplate.update( sql, crypto.getName(), crypto.getSymbol(), crypto.getDescription(), crypto.getLogoUrl());
//
//        } catch (Exception e) {
//            // Implement error handling to fronend
//
//        }
//    }
//    public void update(Crypto crypto, Integer id) {
//        String sql = "UPDATE \"Crypto\" SET name = ?, symbol = ?, description = ?, logoUrl = ? WHERE id = ?";
//        try {
//            var updated = jdbcTemplate.update( sql,  crypto.getName(), crypto.getSymbol(), crypto.getDescription(), crypto.getLogoUrl(),id);
//
//        } catch (Exception e) {
//            // Implement error handling to fronend
//
//        }
//    }public void delete(Integer id) {
//        String sql = "DELETE FROM \"Crypto\" WHERE id = ?";
//        try {
//            var deleted = jdbcTemplate.update(sql, id);
//
//        } catch (Exception e) {
//            // Implement error handling to fronend
//
//        }
//    }
//}
