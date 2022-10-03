package com.shmoon.telegramreminderbot.bot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReminderBotTest {

    @Autowired
    ReminderBot reminderBot;

    @Test
    void 기본_설정_테스트() {
        assertThat(reminderBot).isNotNull();
    }

    @Test
    void 명령어_확인_테스트() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // case
        Method method = ReminderBot.class.getDeclaredMethod("hasCommand", Update.class);
        method.setAccessible(true);
        Update update = new Update();
        update.setMessage(new Message());

        ArrayList<MessageEntity> messageEntities = new ArrayList<>();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setType("bot_command");
        messageEntities.add(messageEntity);
        update.getMessage().setEntities(messageEntities);

        // when
        boolean isCommand = (boolean) method.invoke(reminderBot, update);

        // then
        assertTrue(isCommand);
    }

    @Test
    void 일반메시지_확인_테스트() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // case
        Method method = ReminderBot.class.getDeclaredMethod("hasCommand", Update.class);
        method.setAccessible(true);
        Update update = new Update();
        update.setMessage(new Message());

        // when
        boolean isCommand = (boolean) method.invoke(reminderBot, update);

        // then
        assertFalse(isCommand);
    }


}