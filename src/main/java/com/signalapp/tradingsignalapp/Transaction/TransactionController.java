package com.signalapp.tradingsignalapp.Transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;

    // It's the best practise, using new Repo may cause performance issues
    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    @GetMapping("")
    List<Transaction> getAll(){
        return transactionRepository.getAll();
    }

    @GetMapping("/{id}")
    Transaction getById(@PathVariable Integer id){
        Transaction transaction = transactionRepository.getById(id);
        if (transaction == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return transaction;
    }
    // post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Transaction transaction){
        try {
            transactionRepository.create(transaction);
        } catch (Exception e) {
            if (e.getMessage().equals("Amount bought can't be negative") || e.getMessage().equals("Amount sold can't be negative")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't add negative amount to the transaction");
            }else if (e.getMessage().equals("No such crypto on your wallet!")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have this cryptocurrency in your wallet.");
            } else if (e.getMessage().equals("Insufficient funds!")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds!");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred: " + e.getMessage());
            }
        }
    }
    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Transaction transaction, @PathVariable Integer id){
        try {
            transactionRepository.update(transaction, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    // delete
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        try {
            transactionRepository.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    // post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addBalance")
    void addBalance(@RequestBody Transaction transaction){
        try {
            transactionRepository.addBalance(transaction);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    // amount
    @GetMapping("/amount/{userId}")
    Map<Integer, Double> getAmount(@PathVariable Integer userId){
        return transactionRepository.getAmount(userId);
    }
    // balance
    @GetMapping("/balance/{userId}")
    Map<Integer, Double> getBalance(@PathVariable Integer userId){
        return transactionRepository.getBalance(userId);
    }
}