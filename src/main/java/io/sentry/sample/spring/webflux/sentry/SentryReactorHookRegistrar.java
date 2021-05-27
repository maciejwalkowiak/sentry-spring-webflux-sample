package io.sentry.sample.spring.webflux.sentry;

import io.sentry.IHub;
import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;

public class SentryReactorHookRegistrar {
    private static final Logger LOGGER = LoggerFactory.getLogger(SentryReactorHookRegistrar.class);

    @PostConstruct
    void registerHook() {
        Schedulers.onScheduleHook("sentry", runnable -> {
            IHub oldState = Sentry.getCurrentHub();
            LOGGER.info("Executing hook {}", oldState);
            IHub newHub = Sentry.getCurrentHub().clone();
            return () -> {
                Sentry.setCurrentHub(newHub);
                try {
                    runnable.run();
                } finally {
                    Sentry.setCurrentHub(oldState);
                }
            };
        });
    }
}
