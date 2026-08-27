package com.harsh.nursebookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point.
 *
 * {@code @SpringBootApplication} combines configuration, auto-configuration,
 * and component scanning. Spring scans this package and its subpackages for
 * application classes such as future controllers, services, and repositories.
 */
@SpringBootApplication
public class NurseBookingSystemApplication {

    public static void main(String[] args) {
        // Starts Spring's application context and the embedded web server.
        SpringApplication.run(NurseBookingSystemApplication.class, args);
    }

}
