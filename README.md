# Jaru Server

[![Build Status](https://travis-ci.org/andrewvora/jaru-server.svg?branch=master)](https://travis-ci.org/andrewvora/jaru-server)

The server for the [Jaru app](https://github.com/andrewvora/jaru-app). Contains the content API and mobile API.

## Set up

There's a dev profile included in the repo that you can update as needed.

To run it for production purposes, you need an `application.properties`.

**application.properties**
```
admin.username=username
admin.password=password

# db config
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://something:port/jaru
spring.datasource.username=username
spring.datasource.password=password
```

## Running

There's two flavors, dev and prod.

### Development

Run with the `mvn` arguments

    -Dspring.profiles.active=dev

And make sure you have a database to point to defined in `application-dev.properties`.

### Production

You need a valid database with the details laid out in `application.properties`.

### Testing

This project uses h2 in-memory databases for its tests.

## Contributing

Make a fork, then a PR :)