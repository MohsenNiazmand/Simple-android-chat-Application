package com.example.simplechatandroid.data;

public class Message {
    private String _id;
    private String date;
    private String time;
    private String sender;
    private String receiver;
    private String sender_type;
    private String message;



    public java.lang.String getTime() {
        return time;
    }

    public void setTime(java.lang.String time) {
        this.time = time;
    }

    public java.lang.String get_id() {
        return _id;
    }

    public void set_id(java.lang.String _id) {
        this._id = _id;
    }

    public java.lang.String getDate() {
        return date;
    }

    public void setDate(java.lang.String date) {
        this.date = date;
    }

    public java.lang.String getMessage() {
        return message;
    }

    public void setMessage(java.lang.String message) {
        this.message = message;
    }

    public java.lang.String getReceiver() {
        return receiver;
    }

    public void setReceiver(java.lang.String receiver) {
        this.receiver = receiver;
    }

    public java.lang.String getSender() {
        return sender;
    }

    public void setSender(java.lang.String sender) {
        this.sender = sender;
    }

    public java.lang.String getSender_type() {
        return sender_type;
    }

    public void setSender_type(java.lang.String sender_type) {
        this.sender_type = sender_type;
    }

}
