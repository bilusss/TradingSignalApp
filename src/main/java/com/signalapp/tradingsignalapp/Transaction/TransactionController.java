package com.signalapp.tradingsignalapp.Transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
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
        Optional<Transaction> transaction = transactionRepository.getById(id);
        if (transaction.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return transaction.get();
    }
    // post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Transaction transaction){
        transactionRepository.create(transaction);
    }
    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Transaction transaction, @PathVariable Integer id){
        transactionRepository.update(transaction, id);
    }
    // delete
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        transactionRepository.delete(id);
    }
    // balance
    @GetMapping("/balance/{userId}")
    Map<Integer, Double> getBalance(@PathVariable Integer userId){
        return transactionRepository.getBalance(userId);
    }
}
