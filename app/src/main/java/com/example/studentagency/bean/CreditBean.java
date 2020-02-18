package com.example.studentagency.bean;

import androidx.annotation.NonNull;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class CreditBean {
    private String description;
    private String date;
    private int increasement;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIncreasement() {
        return increasement;
    }

    public void setIncreasement(int increasement) {
        this.increasement = increasement;
    }

    @NonNull
    @Override
    public String toString() {
        return "[ description:"+description+"\n"+
                "date:"+date+"\n"+
                "increasement:"+increasement+"]";
    }
}
