package com.aukcje.dto;

import com.aukcje.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class NewestMessage {
    private Long otherUser;
    private LocalDateTime sendTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewestMessage that = (NewestMessage) o;
        return Objects.equals(otherUser, that.otherUser) && sendTime.isBefore(that.sendTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(otherUser, sendTime);
    }
}

