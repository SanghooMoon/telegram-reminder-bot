package com.shmoon.telegramreminderbot.core;

import com.shmoon.telegramreminderbot.bot.Message;
import com.shmoon.telegramreminderbot.exception.ProcessException;
import lombok.Getter;
import lombok.ToString;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
public class Reminder {

    private final String chatId;
    private final String id;
    private final String msg;
    private final LocalTime reqTime;

    // 생성자
    public Reminder(String chatId, String messageText) {
        try {
            String[] info = messageText.split(" ");
            if (info.length < 4) { // >> /add ID 23:59 MSG
                throw new ProcessException(Message.ADD_EXCEPTION, chatId);
            }

            this.chatId = chatId;
            this.id = info[1];
            this.msg = concatMsg(info);
            this.reqTime = LocalTime.parse(info[2], DateTimeFormatter.ofPattern("H:mm"));
        } catch (DateTimeException de) {
            throw new ProcessException(Message.TIME_EXCEPTION, chatId);
        }
    }

    /**
     * 등록메시지 연결 메서드
     *
     * @param info
     */
    private String concatMsg(String[] info) {
        StringBuilder sb = new StringBuilder();

        for (int i = 3; i < info.length; i++) {
            sb.append(info[i]).append(" ");
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[이름(ID)=").append(id).append(", 요청시간=").append(reqTime.format(DateTimeFormatter.ofPattern("H:mm")))
          .append("]\n").append(msg).append("\n\n");

        return sb.toString();
    }
}
