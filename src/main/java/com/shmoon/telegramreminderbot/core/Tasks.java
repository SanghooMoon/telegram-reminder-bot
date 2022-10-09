package com.shmoon.telegramreminderbot.core;

import com.shmoon.telegramreminderbot.bot.Message;
import com.shmoon.telegramreminderbot.exception.ProcessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Tasks {

    private Tasks() {}
    private final static List<Reminder> TASK_LIST = new ArrayList<>();

    /**
     * reminder 등록
     *
     * @param reminder
     * @return
     */
    public String addReminder(Reminder reminder) {
        if (idDuplicateCheck(reminder)) {
            throw new ProcessException(Message.DUPLICATE_ID, reminder.getChatId());
        }

        TASK_LIST.add(reminder);
        return Message.ADD_SUCCESS;
    }

    /**
     * 같은 id 사용 검사
     *
     * @param reminder
     * @return
     */
    private boolean idDuplicateCheck(Reminder reminder) {
        boolean isDuplicate = false;

        for (Reminder target : TASK_LIST) {
            if (reminder.getChatId().equals(target.getChatId()) && reminder.getId().equals(target.getId())) {
                isDuplicate = true;
                break;
            }
        }

        return isDuplicate;
    }

}
