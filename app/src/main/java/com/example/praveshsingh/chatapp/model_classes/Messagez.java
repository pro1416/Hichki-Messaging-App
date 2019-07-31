package com.example.praveshsingh.chatapp.model_classes;

public class Messagez implements Comparable<Messagez> {
    String Message_content;
    String msg_created;
    String msg_type;

    public static final String MSG_TYPE_SENT = "MESSAGE_SENT";
    public static final String MSG_TYPE_RECIEVED = "MESSAGE_RECIEVED";

    public Messagez(String message_content, String msg_type, String msg_created) {
        Message_content = message_content;
        this.msg_type = msg_type;
        this.msg_created = msg_created;
    }

    public String getMessage_content() {
        return Message_content;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public String getMsg_created() {
        return msg_created;
    }

    @Override
    public int compareTo(Messagez o) {
        return getMsg_created().compareTo(o.getMsg_created());
    }
}
