package com.shmoon.telegramreminderbot.exception;

import lombok.Getter;

@Getter
public class ProcessException extends RuntimeException {

    private String chatId;

    public ProcessException(String message, String chatId) {
        super(message);
        this.chatId = chatId;
    }

}
