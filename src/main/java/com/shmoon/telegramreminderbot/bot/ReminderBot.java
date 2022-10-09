package com.shmoon.telegramreminderbot.bot;

import com.shmoon.telegramreminderbot.core.Reminder;
import com.shmoon.telegramreminderbot.core.Tasks;
import com.shmoon.telegramreminderbot.exception.ProcessException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@RequiredArgsConstructor
public class ReminderBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Tasks tasks;

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

    @SneakyThrows(TelegramApiException.class)
    @Override
    public void onUpdateReceived(Update update) {
        try {
            handleCommands(update);
        } catch (TelegramApiException e) {
            logger.info("onUpdateReceived()", e);
        } catch (ProcessException pe) {
            logger.info(pe.getMessage());
            execute(SendMessage.builder().chatId(pe.getChatId()).text(pe.getMessage()).build());
        }

    }

    /**
     * 명령어 핸들링 메서드
     *
     * @param update
     * @throws TelegramApiException
     */
    private void handleCommands(Update update) throws TelegramApiException {
        String chatId = String.valueOf(update.getMessage().getChatId());
        String messageText = update.getMessage().getText();

        if (messageText.startsWith(Commands.help)) {
            sendHelpMessage(chatId);
        } else if (messageText.startsWith(Commands.add)) {
            addReminder(chatId, messageText);
        }
    }

    /**
     * 
     * 리마인더 등록
     * 
     * @param chatId
     * @param messageText
     * @throws TelegramApiException
     */
    private void addReminder(String chatId, String messageText) throws TelegramApiException {
        Reminder reminder = new Reminder(chatId, messageText);

        String result = tasks.addReminder(reminder);

        execute(SendMessage.builder().chatId(chatId).text(result).build());
    }

    /**
     * 명령어(command)인지 확인하는 메서드
     *
     * @param update
     * @deprecated 명령어 검증에 있어 불필요하여 처리
     */
    @Deprecated
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
        execute(SendMessage.builder().chatId(chatId).text(Message.helpMsg).build());
    }
}
