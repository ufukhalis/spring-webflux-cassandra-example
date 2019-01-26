package io.github.ufukhalis.webflux.controller;

import io.github.ufukhalis.webflux.model.User;
import io.github.ufukhalis.webflux.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        // Adding dummy users to cassandra
        Flux<User> userFlux = Flux.range(1, 100)
                .map(value -> {
                    User user = new User();
                    user.setId(value);
                    user.setFullName("ufuk halis" + value);
                    user.setAddress("Address " + value);
                    user.setEmail("ufukhalis@gmail.com" + value);
                    return user;
                });
        userService.saveAll(userFlux).subscribe();
    }

    @GetMapping("/list-of-users")
    public Flux<User> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/email/{email}")
    public Mono<ResponseEntity<List<User>>> findByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email)
                .collectList()
                .flatMap(users -> {
                    if (users.isEmpty()) {
                        return Mono.just(notFound().build());
                    } else {
                        return Mono.just(ok().body(users));
                    }
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> find(@PathVariable("id") int id) {
        return userService.find(id)
                .flatMap(user -> Mono.just(ok().body(user)))
                .switchIfEmpty(Mono.just(notFound().build()));
    }
}
