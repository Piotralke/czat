package com.projekt.czat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMER_MESSAGE")
class Message {

    private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;

    private String text;
    private Long conversationId;
    private Long senderId;
   // private Date date;
    public Message() {}

    Message(Long conversationId,Long senderId, String text) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.text = text;
    //    this.date=date;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }



    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd hh.mm.ss");
        return "Message{" + "id=" + this.id + ", conversationId=" + this.conversationId + ", senderID=" + this.senderId +  ", text='" + this.text + '\'' + ", status="  + ", data=" ;
    }
}
