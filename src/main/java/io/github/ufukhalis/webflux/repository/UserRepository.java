package io.github.ufukhalis.webflux.repository;

import io.github.ufukhalis.webflux.model.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCassandraRepository<User, Integer> {

    // running cassandra in docker container
    // docker run -d -p 9042:9042 --name single-cassandra cassandra:latest

    // accessing docker cassandra via cqlsh
    // docker run -it --link single-cassandra:cassandra --rm cassandra cqlsh cassandra

    // creating keyspace
    // CREATE KEYSPACE user WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

    // Bad practise, don't do this production
    // In Cassandra, you should always query for Primary Key fields.
    @AllowFiltering
    Flux<User> findByEmail(String email);
}
