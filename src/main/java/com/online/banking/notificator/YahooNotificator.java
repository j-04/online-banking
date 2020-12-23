package com.online.banking.notificator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class YahooNotificator implements BaseNotificator {
    @Override
    public void send(String from, String to, String title, String message) {
        log.info("NOTIFICATION: PROVIDER - {}, from -> {}, to -> {}, title -> {}, message -> {}", YahooNotificator.class.getName(), from, to, title, message);
    }
}
