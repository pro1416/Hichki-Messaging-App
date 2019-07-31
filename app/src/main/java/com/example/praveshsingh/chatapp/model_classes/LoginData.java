package com.example.praveshsingh.chatapp.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    public LoginData(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @SerializedName("UserName")
    private String userName;
    @SerializedName("Password")
    private String password;

    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }

}
