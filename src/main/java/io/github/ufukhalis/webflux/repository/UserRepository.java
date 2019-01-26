package io.github.ufukhalis.webflux.repository;

import io.github.ufukhalis.webflux.model.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCassandraRepository<User, Integer> {

    // Bad practise, don't do this production
    // In Cassandra, you should always query for Primary Key fields.
    @AllowFiltering
    Flux<User> findByEmail(String email);
}
