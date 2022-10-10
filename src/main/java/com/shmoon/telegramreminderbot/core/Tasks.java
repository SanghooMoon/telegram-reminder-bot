package com.shmoon.telegramreminderbot.core;

import com.shmoon.telegramreminderbot.bot.Message;
import com.shmoon.telegramreminderbot.exception.ProcessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Tasks {

    private Tasks() {
    }

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

    /**
     * 등록된 리마인더 정보 반환
     *
     * @param chatId
     * @return
     */
    public String showReminder(String chatId) {
        StringBuilder sb = new StringBuilder();

//        for (Reminder reminder : TASK_LIST) {
//            if(chatId.equals(reminder.getChatId())) {
//                sb.append(reminder);
//            }
//        }
        TASK_LIST.stream().filter(n -> chatId.equals(n.getChatId())).forEach(sb::append);

        if (sb.length() < 1) {
            sb.append(Message.NO_REMINDER);
        }

        return sb.toString();
    }
}
