package io.sentry.sample.spring.webflux;

import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@RestController
public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
    private final SlowService slowService;

    public HelloController(SlowService slowService) {
        this.slowService = slowService;
    }

    @GetMapping("/")
    Mono<HelloDto> hello(@RequestParam String name) {
        return Mono.delay(Duration.ofMillis(100))
                .publishOn(Schedulers.boundedElastic())
                .map(__ -> slowService.slowMethod())
                .publishOn(Schedulers.parallel())
                .map(slowMessage -> {
                    LOGGER.info("mapping to DTO");
                    return new HelloDto("hello " + name + " " + slowMessage);
                })
                .doOnNext(dto -> {
                    LOGGER.info("Capturing message");
                    Sentry.captureMessage(dto.getMessage());
                    throw new RuntimeException("uff");
                });
    }
}
