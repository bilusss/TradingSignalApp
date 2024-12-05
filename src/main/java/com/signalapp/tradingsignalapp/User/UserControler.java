package com.signalapp.tradingsignalapp.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserControler {
    private final UserRepository userRepository;

    // It's the best practise, using new Repo may cause performance issues
    public UserControler(UserRepository userRepository) {this.userRepository = userRepository;}
    @GetMapping("")
    List<User> getAll(){
        return userRepository.getAll();
    }

    @GetMapping("/{id}")
    User getById(@PathVariable Integer id){
        Optional <User> user = userRepository.getById(id);
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    // post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody User user){
        userRepository.create(user);
    }

    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody User user, @PathVariable Integer id){
        userRepository.update(user, id);
    }

    // delete
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        userRepository.delete(id);
    }
}
