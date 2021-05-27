package io.sentry.sample.spring.webflux.sentry;

import io.sentry.IHub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SentryWebfluxConfiguration {

    @Bean
    SentryFilter sentryFilter(IHub hub) {
        return new SentryFilter(hub);
    }

    @Bean
    SentryWebExceptionHandler sentryWebExceptionHandler(IHub hub) {
        return new SentryWebExceptionHandler(hub);
    }
}
