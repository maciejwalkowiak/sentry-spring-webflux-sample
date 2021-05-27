package io.sentry.sample.spring.webflux.sentry;

import io.sentry.IHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Order(-2) // the DefaultErrorWebExceptionHandler provided by Spring Boot for error handling is ordered at -1
public class SentryWebExceptionHandler implements WebExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SentryWebExceptionHandler.class);
    private final IHub hub;

    public SentryWebExceptionHandler(IHub hub) {
        this.hub = hub;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        LOGGER.error("Capturing exception");
        hub.captureException(throwable);
        return Mono.error(throwable);
    }
}
