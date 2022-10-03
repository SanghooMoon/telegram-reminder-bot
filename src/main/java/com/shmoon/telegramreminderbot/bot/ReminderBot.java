package com.shmoon.telegramreminderbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class ReminderBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        try {
            handleCommands(update);
        } catch (TelegramApiException e) {
            logger.info("onUpdateReceived()", e);
        }

    }

    /**
     * 명령어 핸들링 메서드
     *
     * @param update
     * @throws TelegramApiException
     */
    private void handleCommands(Update update) throws TelegramApiException {
        if (hasCommand(update)) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            String messageText = update.getMessage().getText();

            if (messageText.startsWith(Commands.help)) {
                sendHelpMessage(chatId);
            }
        }
    }

    /**
     * 명령어(command)인지 확인하는 메서드
     *
     * @param update
     */
    private boolean hasCommand(Update update) {
        return update.getMessage().getEntities() != null && "bot_command".equals(update.getMessage().getEntities().get(0).getType());
    }

    /**
     * /help 처리 메서드
     *
     * @param chatId
     * @throws TelegramApiException
     */
    private void sendHelpMessage(String chatId) throws TelegramApiException {
        execute(SendMessage.builder().chatId(chatId).text("도움말입니다.").build());
    }
}
