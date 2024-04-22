package com.trunarrative.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchApplication {

    private static final Logger log = LoggerFactory.getLogger(SearchApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
        log.info("Search Application started ");

    }

}
