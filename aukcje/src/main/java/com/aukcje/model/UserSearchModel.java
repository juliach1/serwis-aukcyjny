package com.aukcje.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@NoArgsConstructor
public class UserSearchModel {
    @Check(constraints = "(isActive IS TRUE OR isBlocked IS TRUE OR isDeleted IS TRUE)")
    private Boolean isActive, isBlocked, isDeleted;

    private String phrase;
    private Integer pageSize;

    public UserSearchModel(Boolean isActive, Boolean isBlocked, Boolean isDeleted, String phrase, Integer pageSize) {
        this.isActive = isActive;
        this.isBlocked = isBlocked;
        this.isDeleted = isDeleted;
        this.phrase = phrase;
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return ">>>>>UserSearchModel{" +
                "isActive=" + isActive +
                ", isBlocked=" + isBlocked +
                ", isDeleted=" + isDeleted +
                ", phrase='" + phrase + '\'' +
                ", pageSize=" + pageSize +
                '}';
    }
}
