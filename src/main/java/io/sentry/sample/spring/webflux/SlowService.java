package io.sentry.sample.spring.webflux;

import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SlowService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlowService.class);

    String slowMethod() {
        LOGGER.info("Executing slow method");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Sentry.captureMessage("from slow service");
        return "slow";
    }
}
