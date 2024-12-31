package com.signalapp.tradingsignalapp.Crypto;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {
    private final CryptoRepository cryptoRepository;

    public CryptoController(CryptoRepository CryptoRepository) {this.cryptoRepository = CryptoRepository;}
    @GetMapping("")
    List<Crypto> getAll(){
        return cryptoRepository.getAll();
    }

    @GetMapping("/{id}")
    Crypto getById(@PathVariable Integer id){
        Crypto crypto = cryptoRepository.getById(id);
        if (crypto == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return crypto;
    }

    @GetMapping("/{symbol}")
    Crypto getBySymbol(@PathVariable String symbol){
        Crypto crypto = cryptoRepository.getBySymbol(symbol);
        if (crypto == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return crypto;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Crypto crypto){
        try{
            cryptoRepository.create(crypto);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Crypto crypto, @PathVariable Integer id){
        try{
            cryptoRepository.update(crypto, id);
        }catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        try{
            cryptoRepository.delete(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
