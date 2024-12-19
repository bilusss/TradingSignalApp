package com.signalapp.tradingsignalapp.Wallet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    private final WalletRepository walletRepository;

    public WalletController(WalletRepository walletRepository) {this.walletRepository = walletRepository;}
    @GetMapping("")
    List<Wallet> getAll(){
        return walletRepository.getAll();
    }

    @GetMapping("/{id}")
    Wallet getById(@PathVariable Integer id){
        Optional <Wallet> wallet = walletRepository.getById(id);
        if (wallet.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return wallet.get();
    }

    @GetMapping("/user/{userId}")
    List <Wallet> getByUserId(@PathVariable Integer userId){
        return walletRepository.getByUserId(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Wallet wallet){
        walletRepository.create(wallet);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Wallet wallet, @PathVariable Integer id){
        walletRepository.update(wallet, id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        walletRepository.delete(id);
    }
}
