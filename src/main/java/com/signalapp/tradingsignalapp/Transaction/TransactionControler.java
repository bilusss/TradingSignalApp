package com.signalapp.tradingsignalapp.Transaction;

import com.signalapp.tradingsignalapp.User.User;
import com.signalapp.tradingsignalapp.User.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("TODO")
public class TransactionControler {
    private final TransactionRepository transactionRepository;

    // It's the best practise, using new Repo may cause performance issues
    public TransactionControler(TransactionRepository transactionRepository) {this.transactionRepository = transactionRepository;}
    @GetMapping("")
    List<Transaction> getAll(){
        return transactionRepository.getAll();
    }
    //TODO finish all as in users
    // DONT forget to put  CREATE TABLE in resources/schema.sql
}
