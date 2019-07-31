package com.example.praveshsingh.chatapp.model_classes;


public class SignupData {
    String name;
    String address;
    String email;
    String phone;
    String username;
    String password;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public SignupData(String name, String address, String email, String phone, String username, String password) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }


}
