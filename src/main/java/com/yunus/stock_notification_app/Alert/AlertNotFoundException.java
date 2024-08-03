package com.yunus.stock_notification_app.Alert;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlertNotFoundException extends RuntimeException{
    public AlertNotFoundException(String message) {
        super(message);
    }
}