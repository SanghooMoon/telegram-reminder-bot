package com.shmoon.telegramreminderbot.core;

import com.shmoon.telegramreminderbot.bot.Message;
import com.shmoon.telegramreminderbot.exception.ProcessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TasksTest {

    @InjectMocks
    Tasks tasks;

    @Test
    void 리마인더_등록_테스트() {
        // case
        Reminder reminder = new Reminder("123", "/add 테스트등록 13:01 테스트입니다.");

        // when
        String result = tasks.addReminder(reminder);

        // then
        assertEquals(result, Message.ADD_SUCCESS);
    }

    @Test()
    void 리마인더_등록_시간파싱실패() {

        ProcessException processException = assertThrows(ProcessException.class, () -> {
            Reminder reminder2 = new Reminder("123", "/add 테스트 25:01 테스트입니다.");
            tasks.addReminder(reminder2);
        });

        assertEquals(Message.TIME_EXCEPTION, processException.getMessage());

    }

    @Test
    void 리마인더_등록_중복ID() {
        ProcessException processException = assertThrows(ProcessException.class, () -> {
            Reminder reminder = new Reminder("123", "/add 테스트 13:01 테스트입니다.");
            Reminder reminder2 = new Reminder("123", "/add 테스트2 13:01 다른메시지");

            tasks.addReminder(reminder);
            tasks.addReminder(reminder2);
        });

        assertEquals(Message.DUPLICATE_ID, processException.getMessage());
    }

    @Test
    void 리마인더_SHOW() {
        // case
        Reminder reminder = new Reminder("123", "/add 테스트 13:01 테스트입니다.");
        tasks.addReminder(reminder);

        // when
        String result = tasks.showReminder("123");

        // then
        assertEquals(reminder.toString(), result);
    }

    @Test
    void 리마인더_등록없음_SHOW() {
        // when
        String result = tasks.showReminder("123");

        // then
        assertEquals(Message.NO_REMINDER, result);
    }


}