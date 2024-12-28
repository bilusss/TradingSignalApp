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
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        Map<String, String> namesMap;
        try {
            File file = new File("src/main/java/com/signalapp/tradingsignalapp/Crypto/names.json");
            ObjectMapper objectMapper = new ObjectMapper();
            namesMap = objectMapper.readValue(file, Map.class);
        } catch (Exception e) {
            return;
        }
        String sql = "INSERT INTO \"Crypto\"(name, symbol, description, logourl) VALUES (?, ?, ?, ?)";
        Crypto crypto;
        String logoUrl;
        List<String> repeated = new ArrayList<>();
        Map<String, BinanceExchangeInfo.SymbolInfo> symbolInfoMap = binanceExchangeInfo.getSymbolInfoMap();
        for (Map.Entry<String, BinanceExchangeInfo.SymbolInfo> entry : symbolInfoMap.entrySet()) {
            if (entry.getValue().getSymbol().equals("BULLUSDT") && entry.getValue().getStatus().equals("TRADING") || entry.getValue().getSymbol().equals("BEARUSDT") && entry.getValue().getStatus().equals("TRADING")) {
                // Have to be empty here :)
            } else if (!entry.getKey().endsWith("USDT")) {
                continue;
            } else if (entry.getValue().getBaseAsset().endsWith("BULL") || entry.getValue().getBaseAsset().endsWith("BEAR") || entry.getValue().getBaseAsset().endsWith("UP") || entry.getValue().getBaseAsset().endsWith("DOWN")) {
                continue;
            }
            crypto = new Crypto();
            // Symbol
            crypto.setSymbol(entry.getValue().getBaseAsset());
            // Name
            crypto.setName(namesMap.get(entry.getValue().getBaseAsset()));
            // Description TODO maybe make something more interesting here later
            crypto.setDescription("Cryptocurrency");
            // LogoUrl
            https://bin.bnbstatic.com/static/assets/logos/SYMBOL.png
            logoUrl = "https://bin.bnbstatic.com/static/assets/logos/" + crypto.getSymbol().toUpperCase() + ".png";
            if (isValidImageUrl(logoUrl)) {
                crypto.setLogoUrl(logoUrl);
            } else {
                log.warn("Invalid logo URL for symbol: " + crypto.getSymbol() + ", skipping logo");
                crypto.setLogoUrl("");
            }
            // DB
            if (getBySymbol(crypto.getSymbol()).isEmpty()) { // Adding to Database
                jdbcTemplate.update(sql, crypto.getName(), crypto.getSymbol(), crypto.getDescription(), crypto.getLogoUrl());
            } else {
                repeated.add(crypto.getSymbol());
            }
        }
        if (!repeated.isEmpty()) {
            int repCount = repeated.size();
            log.info("Skipped initializing " + repCount + " symbols: [" + String.join(", ", repeated) + "]");
        }
        log.info("Crypto Data initialized into Database");
    }
    /**
     * Checks if the given URL returns an image.
     * @param urlString URL to check
     * @return true if the URL returns an image, false otherwise
     **/
    private boolean isValidImageUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            String contentType = connection.getContentType();
            int responseCode = connection.getResponseCode();

            return responseCode == 200 && contentType != null && contentType.startsWith("image/");
        } catch (Exception e) {
            log.warn("Error validating image URL: " + urlString, e);
            return false;
        }
    }
}

