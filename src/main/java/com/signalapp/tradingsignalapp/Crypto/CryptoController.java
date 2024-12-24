//package com.signalapp.tradingsignalapp.Crypto;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/crypto")
//public class CryptoController {
//    private final CryptoRepository cryptoRepository;
//
//    public CryptoController(CryptoRepository CryptoRepository) {this.cryptoRepository = CryptoRepository;}
//    @GetMapping("")
//    List<Crypto> getAll(){
//        return cryptoRepository.getAll();
//    }
//
//    @GetMapping("/{id}")
//    Crypto getById(@PathVariable Integer id){
//        Optional <Crypto> crypto = cryptoRepository.getById(id);
//        if (crypto.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        return crypto.get();
//    }
//
//    @GetMapping("/{symbol}")
//    Crypto getBySymbol(@PathVariable String symbol){
//        Optional <Crypto> crypto = cryptoRepository.getBySymbol(symbol);
//        if (crypto.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        return crypto.get();
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("")
//    void create(@RequestBody Crypto crypto){
//        cryptoRepository.create(crypto);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PutMapping("/{id}")
//    void update(@RequestBody Crypto crypto, @PathVariable Integer id){
//        cryptoRepository.update(crypto, id);
//    }
//
//    @DeleteMapping("/{id}")
//    void delete(@PathVariable Integer id){
//        cryptoRepository.delete(id);
//    }
//}
