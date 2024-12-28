package com.signalapp.tradingsignalapp.Crypto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signalapp.tradingsignalapp.Service.BinanceExchangeInfo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// So basically I wanted to move the db management away from Controllers
// It would be too much code there, so Repository manages the SELECTS etc
@Repository
public class CryptoRepository {

    public static final Logger log = LoggerFactory.getLogger(CryptoRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final BinanceExchangeInfo binanceExchangeInfo;


    public static class CryptoMapper implements RowMapper<Crypto> {
        @Override
        public Crypto mapRow(ResultSet rs, int rowNum) throws SQLException {
            Crypto crypto = new Crypto();
            crypto.setId(rs.getInt("id"));
            crypto.setName(rs.getString("name"));
            crypto.setSymbol(rs.getString("symbol"));
            crypto.setDescription(rs.getString("description"));
            crypto.setLogoUrl(rs.getString("logoUrl"));
            return crypto;
        }
    }


    public CryptoRepository(JdbcTemplate jdbcTemplate, BinanceExchangeInfo binanceExchangeInfo) {
        this.jdbcTemplate = jdbcTemplate;
        this.binanceExchangeInfo = binanceExchangeInfo;
    }

    public List<Crypto> getAll() {
        String sql = "select * from \"Crypto\" ORDER BY id ASC";
        return jdbcTemplate.query(sql, new CryptoMapper());
    }

    // Optional returns Object or None
    public Optional<Crypto> getById(Integer id) {
        String sql = "SELECT * FROM \"Crypto\" WHERE id = ?";
        try {
            Crypto crypto = jdbcTemplate.queryForObject(sql, new CryptoMapper(), id);
            return Optional.ofNullable(crypto);
        } catch (Exception e) {
            // If no result is found, return None
            return Optional.empty();
        }
    }

    public Optional<Crypto> getBySymbol(String symbol) {
        String sql = "SELECT * FROM \"Crypto\" WHERE symbol = ?";
        try {
            Crypto crypto = jdbcTemplate.queryForObject(sql, new CryptoMapper(), symbol);
            return Optional.ofNullable(crypto);
        } catch (Exception e) {
            // If no result is found, return None
            return Optional.empty();
        }
    }

    public void create(Crypto crypto) {
        String sql = "INSERT INTO \"Crypto\"(name, symbol, description, logoUrl) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, crypto.getName(), crypto.getSymbol(), crypto.getDescription(), crypto.getLogoUrl());
    }

    public void update(Crypto crypto, Integer id) {
        String sql = "UPDATE \"Crypto\" SET name = ?, symbol = ?, description = ?, logoUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, crypto.getName(), crypto.getSymbol(), crypto.getDescription(), crypto.getLogoUrl(), id);

    }

    public void delete(Integer id) {
        String sql = "DELETE FROM \"Crypto\" WHERE id = ?";
        var deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) {
            throw new EmptyResultDataAccessException("Crypto with id " + id + " not found.", 1);
        }
    }
    @DependsOn("binanceExchangeInfo")
    @PostConstruct
    public void initializeCryptoData() {
        log.info("Initializing Crypto Data into Database");
        String sql = "INSERT INTO \"Crypto\"(name, symbol, description, logourl) VALUES (?, ?, ?, ?)";
        Crypto crypto;
        Map<String, BinanceExchangeInfo.SymbolInfo> symbolInfoMap = binanceExchangeInfo.getSymbolInfoMap();
        for (Map.Entry<String, BinanceExchangeInfo.SymbolInfo> entry : symbolInfoMap.entrySet()) {
            if (entry.getValue().getSymbol().equals("BULLUSDT") && entry.getValue().getStatus().equals("TRADING") || entry.getValue().getSymbol().equals("BEARUSDT") && entry.getValue().getStatus().equals("TRADING")){
                // Have to be empty here :)
            } else if (!entry.getKey().endsWith("USDT")) {
                continue;
            } else if (entry.getValue().getBaseAsset().endsWith("BULL") || entry.getValue().getBaseAsset().endsWith("BEAR") || entry.getValue().getBaseAsset().endsWith("UP") || entry.getValue().getBaseAsset().endsWith("DOWN")){
                continue;
            }
            crypto = new Crypto();
            // Symbol
            crypto.setSymbol(entry.getKey().replace("USDT", ""));
            // Name
            try {
                File file = new File("src/main/java/com/signalapp/tradingsignalapp/Crypto/names.json");
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> namesMap = objectMapper.readValue(file, Map.class);
                crypto.setName(namesMap.get(crypto.getSymbol()));
            }catch (Exception e){
                crypto.setName(String.valueOf(e));
            }
            // Description TODO maybe make something more interesting here later
            crypto.setDescription("Cryptocurrency");
            // LogoUrl
            crypto.setLogoUrl("https://cdn.jsdelivr.net/gh/vadimmalykhin/binance-icons/crypto/" + crypto.getSymbol().toLowerCase() + ".svg");
            jdbcTemplate.update(sql, crypto.getName(), crypto.getSymbol(), crypto.getDescription(), crypto.getLogoUrl());
        }
    }
}

