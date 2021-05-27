package io.sentry.sample.spring.webflux;

public class HelloDto {
    private final String message;

    public HelloDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
