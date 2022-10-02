package com.shmoon.telegramreminderbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:properties/system.properties"})
public class TelegramReminderBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramReminderBotApplication.class, args);
    }

}
