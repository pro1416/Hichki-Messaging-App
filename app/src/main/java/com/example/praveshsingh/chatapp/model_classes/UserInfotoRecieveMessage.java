package com.example.praveshsingh.chatapp.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfotoRecieveMessage {
    @SerializedName("SenderId")
    @Expose
    private String senderId;
    @SerializedName("RecipientId")
    @Expose
    private String recipientId;

    public UserInfotoRecieveMessage(String senderId, String recipientId) {
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
}
