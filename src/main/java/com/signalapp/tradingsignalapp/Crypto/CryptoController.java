package com.signalapp.tradingsignalapp.Crypto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {
    private final CryptoRepository cryptoRepository;

    public CryptoController(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @GetMapping("")
    public List<Crypto.CurrencyDetails> getAll() {
        return cryptoRepository.getAll();
    }

    @GetMapping("/{id}")
    public Crypto.CurrencyDetails getById(@PathVariable Integer id) {
        return cryptoRepository.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/symbol/{symbol}")
    public Crypto.CurrencyDetails getBySymbol(@PathVariable String symbol) {
        return cryptoRepository.getBySymbol(symbol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Crypto.CurrencyDetails cryptoDetails) {
        cryptoRepository.create(cryptoDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Crypto.CurrencyDetails cryptoDetails, @PathVariable Integer id) {
        cryptoRepository.updateById(id, cryptoDetails);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        cryptoRepository.deleteById(id);
    }
}
