package edu.esprit.entities;

import java.sql.Date;
import java.util.Objects;


public class Message {

    private int id;
    private int senderId;
    private int receiverId;
    private String content;
    private Date dateSent;

    public Message() {
    }

    public Message(int id, int senderId, int receiverId, String content, Date dateSent) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.dateSent = dateSent;
    }

    public Message(int senderId, int receiverId, String content, Date dateSent) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.dateSent = dateSent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", content='" + content + '\'' +
                ", dateSent=" + dateSent +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderId, receiverId, content, dateSent);
    }



}
