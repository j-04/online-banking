package com.online.banking.notificator;

public interface BaseNotificator {
    void send(String from, String to, String title, String message);
}
