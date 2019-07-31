package com.example.praveshsingh.chatapp.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitMessage {
    @SerializedName("SenderId")
    @Expose
    private String senderId;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("MessageBody")
    @Expose
    private String messageBody;
    @SerializedName("ReceiverId")
    @Expose
    private String receiverId;

    public SubmitMessage(String senderId, String subject, String messageBody, String receiverId) {
        this.senderId = senderId;
        this.subject = subject;
        this.messageBody = messageBody;
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

}
