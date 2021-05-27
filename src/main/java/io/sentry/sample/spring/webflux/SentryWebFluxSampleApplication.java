package io.sentry.sample.spring.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SentryWebFluxSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentryWebFluxSampleApplication.class, args);
    }
}

