package com.shmoon.telegramreminderbot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ReminderBot extends TelegramLongPollingBot {

    @Value("${BOT_TOKEN_KEY}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return "SH_reminder_bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        String messageText = update.getMessage().getText();

        try {
            execute(SendMessage.builder().chatId(chatId).text(messageText).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
