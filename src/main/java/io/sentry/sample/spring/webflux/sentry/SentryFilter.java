package io.sentry.sample.spring.webflux.sentry;

import io.sentry.IHub;
import io.sentry.protocol.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

class SentryFilter implements WebFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SentryFilter.class);
    private final IHub hub;

    SentryFilter(IHub hub) {
        this.hub = hub;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        LOGGER.info("Pushing scope");
        hub.pushScope();
        hub.configureScope(scope -> scope.setRequest(resolveSentryRequest(serverWebExchange.getRequest())));
        return webFilterChain.filter(serverWebExchange).then(Mono.create(__ -> {
            LOGGER.info("Popping scope");
            hub.popScope();
        }));
    }

    private Request resolveSentryRequest(ServerHttpRequest request) {
        Request sentryRequest = new Request();
        sentryRequest.setUrl(request.getURI().toString());
        sentryRequest.setMethod(request.getMethodValue());
        sentryRequest.setHeaders(request.getHeaders().toSingleValueMap());
        return sentryRequest;
    }
}
