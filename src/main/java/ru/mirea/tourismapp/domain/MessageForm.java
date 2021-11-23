package ru.mirea.tourismapp.domain;

import lombok.Data;
import java.util.Date;

@Data
public class MessageForm {

    private String message;
    private String postDate;
    private Long userId;

    public Message toMessage(User user, Date date) {
        Message msge = new Message();
        msge.setMessage(message);
        msge.setPostDate(date.toString());
        msge.setUserId(user.getId());
        return msge;
    }
}

