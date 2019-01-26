package io.github.ufukhalis.webflux.service;

import io.github.ufukhalis.webflux.model.User;
import io.github.ufukhalis.webflux.repository.UserRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Flux<User> saveAll(Publisher<User> users) {
        return userRepository.saveAll(users);
    }

    public Flux<User> listUsers() {
        return userRepository.findAll();
    }

    public Mono<User> find(int id) {
        return userRepository.findById(id);
    }
}
