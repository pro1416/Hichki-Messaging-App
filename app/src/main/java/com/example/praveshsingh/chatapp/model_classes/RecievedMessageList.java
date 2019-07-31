package com.example.praveshsingh.chatapp.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecievedMessageList {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("responseData")
    @Expose
    private List<RecievedMessage> responseData = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RecievedMessage> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<RecievedMessage> responseData) {
        this.responseData = responseData;
    }

}
