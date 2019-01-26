Spring Web-Flux Example 
===================

This project created for an example to understanding Spring Web Flux module
with using Reactive Cassandra repositories.

As you know or not, Spring Web-Flux is a reactive approach for the Spring
Boot based applications. It became to available in Spring Boot `2.0.0` version.
Spring Web-Flux uses the Reactor library which is an implementation of Reactive Streams.
So Reactor and Spring Web-Flux are fully compatible each other.

I strongly recommend to take a look each of these projects documentations.
Here are the links.

https://projectreactor.io/docs/core/release/reference/

https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html


Let's Start
---

If you want to try this example project locally. It can be good for you to follow
below instructions.

Firstly, i used Cassandra as a storage. You can install manually or using docker image like as i did.

    docker run -d -p 9042:9042 --name single-cassandra cassandra:latest
    
If you want to access your cassandra via `cqlsh`.

    docker run -it --link single-cassandra:cassandra --rm cassandra cqlsh cassandra
    
That's it. Our cassandra is ready.

Then we need to create `Keyspace` for our table. We can do this via `cqlsh`.

    CREATE KEYSPACE user WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
    
For testing we don't care about `replication factor` so we set as `1`.

Our project structure is same as traditional Spring projects. 
Like, we have

    controller/
    model/
    repository/
    service/
    
In `repository` package, we have UserRepository class.

    public interface UserRepository extends ReactiveCassandraRepository<User, Integer> {
    
        @AllowFiltering
        Flux<User> findByEmail(String email);
    }
    
If you familiar with Spring Data general concepts, this class does'nt look different for you.
If you don't have knowledge about Spring Data, i strongly recommend to take a look that project.
As you see, for Spring Web-Flux purposes, you should use `Reactive` repositories.
And you can use this repository in your `service` class like other Spring projects.
The main difference you need to deal with `Flux` and `Mono` types. 
