package com.signalapp.tradingsignalapp.Transaction;

import com.signalapp.tradingsignalapp.Crypto.CryptoRepository;
import com.signalapp.tradingsignalapp.Service.BinanceCurrentPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepository {

    public static final Logger log = LoggerFactory.getLogger(TransactionRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final BinanceCurrentPrice binanceCurrentPrice;
    private final CryptoRepository cryptoRepository;

    public static class TransactionMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction transaction = new Transaction(1, "s", 1, 1, 2, 1.0, 100000.0, 100000.0, "aa");
            transaction.setId(rs.getInt("id"));
            transaction.setTitle(rs.getString("title"));
            transaction.setUserId(rs.getInt("userId"));
            transaction.setCryptoIdBought(rs.getInt("cryptoidbought"));
            transaction.setCryptoIdSold(rs.getInt("cryptoidsold"));
            transaction.setAmountBought(rs.getDouble("amountBought"));
            transaction.setAmountSold(rs.getDouble("amountSold"));
            transaction.setPrice(rs.getDouble("price"));
            transaction.setDescription(rs.getString("description"));
            return transaction;
        }
    }

    public TransactionRepository(JdbcTemplate jdbcTemplate, BinanceCurrentPrice binanceCurrentPrice, CryptoRepository cryptoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.binanceCurrentPrice = binanceCurrentPrice;
        this.cryptoRepository = cryptoRepository;
    }

    // Pretty simple we make SQL query using jbdc ( its like sql alchemy )
    // We pass to the query, the sql string and Mapper
    // The query returns ResultSet, but it needs to know how to change it to user
    // That's why the mapper is used - simply instructions how to build user
    public List<Transaction> getAll() {
        String sql = "SELECT * FROM \"Transactions\" ORDER BY id ASC";
        return jdbcTemplate.query(sql, new TransactionMapper());
    }

    public Transaction getById(int id) {
        String sql = "SELECT * FROM \"Transactions\" WHERE \"id\" = ?";
        try {
            Transaction transaction = jdbcTemplate.queryForObject(sql, new TransactionMapper(), id);
            return transaction;
        } catch (Exception e) {
            return null;
        }
    }
    public List<Transaction> getByUserId(int id) {
        String sql = "SELECT * FROM \"Transactions\" WHERE \"userid\" = ?";
        return jdbcTemplate.query(sql, new TransactionMapper(), id);

    }
    public void create(Transaction transaction) {
        String sql = "INSERT INTO \"Transactions\"(title, userid, cryptoidbought, cryptoidsold, amountbought, amountsold, price, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Map<Integer, Double> amount = getAmount(transaction.getUserId());
            if (amount.get(cryptoRepository.getById(transaction.getCryptoIdSold()).getId()) < transaction.getAmountSold()) {
                throw new Exception("Insufficient funds!");
            } else if (amount.get(cryptoRepository.getById(transaction.getCryptoIdSold()).getId()) == null) {
                throw new Exception("No such crypto on your wallet!");
            }
            var created = jdbcTemplate.update(sql, transaction.getTitle(), transaction.getUserId(),
                    transaction.getCryptoIdBought(), transaction.getCryptoIdSold(), transaction.getAmountBought(),
                    transaction.getAmountSold(), transaction.getPrice(), transaction.getDescription());
        } catch (Exception e){
            // Implement error handling to frontend
        }
    }
    public void update(Transaction transaction, int id) {
        String sql = "UPDATE \"Transactions\" SET id = ?, title = ?, userId = ?, cryptoIdBought = ?, cryptoidsold = ?, amountbought = ?, amountsold = ?, price = ?, description = ? WHERE id = ?";
        try {
            /**
             * // Excluding the tx from his amount \\
             * Imagine this scenario
             * User has amount of $90k
             * Then buys 1 BTC for $90k
             * His amount will be +1 BTC, $0.0
             * We need to exclude this tx that he made
             * to make his amount be like before the tx
             * to allow him update his tx
             */
            Map<Integer, Double> amountExclude = getAmountExclude(transaction, transaction.getUserId());
            if (amountExclude.get(cryptoRepository.getById(transaction.getCryptoIdSold())) < transaction.getAmountSold()) {
                throw new Exception("Insufficient funds!");
            } else if (amountExclude.get(cryptoRepository.getById(transaction.getCryptoIdSold())) == null) {
                throw new Exception("No such crypto on your wallet!");
            }
            var updated = jdbcTemplate.update(sql,id, transaction.getTitle(), transaction.getUserId(),
                    transaction.getCryptoIdBought(), transaction.getCryptoIdSold(), transaction.getAmountBought(),
                    transaction.getAmountSold(), transaction.getPrice(), transaction.getDescription());
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
    public void addBalance(Transaction transaction) {
        String sql = "INSERT INTO \"Transactions\"(title, userid, cryptoidbought, cryptoidsold, amountbought, amountsold, price, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        var created = jdbcTemplate.update(sql, "Adding balance", transaction.getUserId(),
                transaction.getCryptoIdBought(), 1, transaction.getAmountBought(),
                0.0, 0.0, "Adding balance");
    }
    public Map<Integer, Double> getAmount(Integer userId){ // Amount in coins
        String sql = "SELECT * FROM \"Transactions\" WHERE userId = ?";
        Map<Integer, Double> amount = new HashMap<>();;
        List<Transaction> listOfTransactions = jdbcTemplate.query(sql, new TransactionMapper(), userId);

        for (Transaction transaction : listOfTransactions) {
            Integer idbought = transaction.getCryptoIdBought();
            Double amountbought = transaction.getAmountBought();
            Integer idsold = transaction.getCryptoIdSold();
            Double amountsold = transaction.getAmountSold();

            // BOUGHT
            if (amount.containsKey(idbought)) { // multiple txs on certain coin
                amount.replace(idbought, amount.get(idbought) + amountbought);
            } else { // first tx on coin
                amount.put(idbought, amountbought);
            }
            // SOLD
            if (amount.containsKey(idsold)) { // multiple txs on certain coin
                amount.replace(idsold, amount.get(idsold) - amountsold);
            } else { // first tx on coin
                amount.put(idsold, -amountsold);
            }
        }
        return amount;
    }
    public Map<Integer, Double> getAmountExclude(Transaction transaction,Integer userId){
        Map<Integer, Double> AmountExclude = getAmount(userId);
        Integer idbought = transaction.getCryptoIdBought();
        Double amountbought = transaction.getAmountBought();
        Integer idsold = transaction.getCryptoIdSold();
        Double amountsold = transaction.getAmountSold();
        AmountExclude.replace(idbought, AmountExclude.get(idbought)-amountbought);
        AmountExclude.replace(idsold, AmountExclude.get(idsold)+amountsold);
        return AmountExclude;
    }
    public Double getNetworth(Integer userId) {
        double balance = 0.0;
        Map<Integer, Double> amount = getAmount(userId);
        Map<String, Double> currentUSDTPrices = binanceCurrentPrice.currentUSDTPrices;
        for (Map.Entry<Integer, Double> entry : amount.entrySet()) {
            Integer id = entry.getKey();
            Double amountCoins = entry.getValue();
            balance += amountCoins * currentUSDTPrices.get(cryptoRepository.getById(id).getSymbol());
        }
        return balance;
    }

    public Map<Integer, Double> getBalance(Integer userId) {
        Map<Integer, Double> balance = new HashMap<>();
        Map<Integer, Double> amount = getAmount(userId);
        Map<String, Double> currentUSDTPrices = binanceCurrentPrice.currentUSDTPrices;
        for (Map.Entry<Integer, Double> entry : amount.entrySet()) {
            Integer id = entry.getKey();
            Double amountCoins = entry.getValue();
            balance.put(id, amountCoins * currentUSDTPrices.get(cryptoRepository.getById(id).getSymbol()));
        }
        return balance;
    }

}