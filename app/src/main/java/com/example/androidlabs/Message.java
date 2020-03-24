package com.example.androidlabs;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Message {

    private String message;
    private boolean sent;

    Message(){
        this("",false);
    }

    Message (String message, boolean sent){
        setMessage(message);
        setSent(sent);
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setSent(boolean sent) {

        this.sent = sent;
    }

    public String getMessage() {
        return message;
    }
    public boolean isSent() {
        return sent;
    }


}
