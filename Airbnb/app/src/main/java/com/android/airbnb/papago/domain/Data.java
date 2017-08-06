package com.android.airbnb.papago.domain;

/**
 * Created by JunHee on 2017. 8. 6..
 */

public class Data {

    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + "]";
    }


}
