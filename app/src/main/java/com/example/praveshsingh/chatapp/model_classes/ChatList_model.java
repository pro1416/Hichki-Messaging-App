package com.example.praveshsingh.chatapp.model_classes;

public class ChatList_model {
    Integer friendID;
    String friendName;

    public ChatList_model(Integer friendID, String friendName) {
        this.friendID = friendID;
        this.friendName = friendName;
    }

    public Integer getFriendID() {
        return friendID;
    }

    public String getFriendName() {
        return friendName;
    }
}
