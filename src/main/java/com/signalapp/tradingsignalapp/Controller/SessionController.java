package com.signalapp.tradingsignalapp.Controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.signalapp.tradingsignalapp.Crypto.Crypto;
import com.signalapp.tradingsignalapp.Crypto.CryptoRepository;
import com.signalapp.tradingsignalapp.Service.BinanceCurrentPrice;
import com.signalapp.tradingsignalapp.Transaction.Transaction;
import com.signalapp.tradingsignalapp.Transaction.TransactionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final TransactionRepository transactionRepository;
    private final BinanceCurrentPrice binanceCurrentPrice;
    private final CryptoRepository cryptoRepository;

    public SessionController(TransactionRepository transactionRepository, BinanceCurrentPrice binanceCurrentPrice, CryptoRepository cryptoRepository) {
        this.transactionRepository = transactionRepository;
        this.binanceCurrentPrice = binanceCurrentPrice;
        this.cryptoRepository = cryptoRepository;
    }
    @RequestMapping("/set")
    public void setSession(HttpSession session, @RequestBody JsonNode json){
        Integer userId = json.get("userId").asInt();
        session.setAttribute("userId", userId);
    }

    @RequestMapping("/get")
    public Integer getSession(HttpSession session){
        try{
            return (Integer) session.getAttribute("userId");
        } catch ( Exception e ){
            return null;
        }
    }

    @RequestMapping("/clear")
    public void clearSession(HttpSession session){
        session.removeAttribute("userId");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addBalance")
    void addBalance(HttpSession session, @RequestBody JsonNode json){
        Integer cryptoIdBought = json.get("cryptoIdBought").asInt();
        Double amountBought = json.get("amountBought").asDouble();
        Transaction transaction = new Transaction();
        transaction.setCryptoIdBought(cryptoIdBought);
        transaction.setAmountBought(amountBought);
        Integer userId = getSession(session);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }else{
            transaction.setUserId(userId);
        }

        try {
            transactionRepository.addBalance(transaction);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @GetMapping("/getAmount")
    Map<Integer, Double> getAmount(HttpSession session){
        Integer userId = getSession(session);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }
        return transactionRepository.getAmount(userId);
    }

    @GetMapping("/getBalance")
    Map<Integer, Double> getBalance(HttpSession session){
        Integer userId = getSession(session);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }
        return transactionRepository.getBalance(userId);
    }

    @GetMapping("/getNetworth")
    Double getNetworth(HttpSession session){
        Integer userId = getSession(session);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }
        return transactionRepository.getNetworth(userId);
    }

    @GetMapping("/getTransactions")
    List<Transaction> getTransactions(HttpSession session){
        Integer userId = getSession(session);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }
        return transactionRepository.getByUserId(userId);
    }
    @PostMapping("/buy")
    ResponseEntity<String> buy(HttpSession session,@RequestBody  JsonNode json) {
        Integer userId = getSession(session);
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setTitle(json.get("title").asText());
        transaction.setDescription(json.get("description").asText());
        transaction.setAmountSold(json.get("amountSold").asDouble());
        transaction.setCryptoIdSold(json.get("cryptoIdSold").asInt());
        transaction.setAmountBought(json.get("amountBought").asDouble());
        transaction.setCryptoIdBought(json.get("cryptoIdBought").asInt());
        if (transaction.getCryptoIdSold() == 9 && transaction.getAmountBought() != 0) {
            transaction.setPrice(transaction.getAmountSold() / transaction.getAmountBought());
        } else if (transaction.getCryptoIdSold() != 9 && transaction.getAmountBought() != 0) {
//            Map<String, Double> currentUSDTPrices = binanceCurrentPrice.currentUSDTPrices;
//            Double sold = currentUSDTPrices.get(cryptoRepository.getById(transaction.getCryptoIdSold()).getSymbol());
//            Double bought = currentUSDTPrices.get(cryptoRepository.getById(transaction.getCryptoIdBought()).getSymbol());
//            transaction.setPrice(sold / bought);
            transaction.setPrice(transaction.getAmountSold() / transaction.getAmountBought());
        }else {
            transaction.setPrice(0d);
        }

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not logged in");
        }
        try {
            transactionRepository.create(transaction);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return new ResponseEntity<>("Transaction created successfully", HttpStatus.CREATED);
    }
}