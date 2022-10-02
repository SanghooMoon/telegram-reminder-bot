package com.shmoon.telegramreminderbot.bot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


}