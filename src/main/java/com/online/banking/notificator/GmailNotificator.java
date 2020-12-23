package com.online.banking.notificator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GmailNotificator implements BaseNotificator {
    @Override
    public void send(String from, String to, String title, String message) {
        log.info("NOTIFICATION: PROVIDER - {}, from -> {}, to -> {}, title -> {}, message -> {}", GmailNotificator.class.getName(), from, to, title, message);
    }
}
