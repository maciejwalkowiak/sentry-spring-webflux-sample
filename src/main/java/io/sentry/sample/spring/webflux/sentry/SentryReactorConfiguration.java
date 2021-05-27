package io.sentry.sample.spring.webflux.sentry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SentryReactorConfiguration {

    @Bean
    SentryReactorHookRegistrar sentryReactorHookRegistrar() {
        return new SentryReactorHookRegistrar();
    }
}
