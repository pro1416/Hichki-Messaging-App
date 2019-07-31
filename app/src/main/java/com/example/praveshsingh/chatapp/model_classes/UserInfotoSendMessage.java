package com.example.praveshsingh.chatapp.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfotoSendMessage {
    @SerializedName("SenderId")
    @Expose
    private String senderId;
    @SerializedName("ReceiverId")
    @Expose
    private String recieverId;

    public UserInfotoSendMessage(String senderId, String recieverId) {
        this.senderId = senderId;
        this.recieverId = recieverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }
}