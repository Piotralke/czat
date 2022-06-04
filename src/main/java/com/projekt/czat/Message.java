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
    private Status status;
    private Long conversationId;
    private Long senderId;
    private Date date;
    public Message() {}

    Message(Long conversationId,Long senderId, String text, Status status,Date date) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.text = text;
        this.status = status;
        this.date=date;
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

    public Status getStatus() {
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Message))
            return false;
        Message message = (Message) o;
        return Objects.equals(this.id, message.id) && Objects.equals(this.conversationId,message.conversationId)
                && Objects.equals(this.text, message.text)
                && this.status == message.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.conversationId, this.text, this.status);
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd hh.mm.ss");
        return "Message{" + "id=" + this.id + ", conversationId=" + this.conversationId + ", senderID=" + this.senderId +  ", text='" + this.text + '\'' + ", status=" + this.status + ", data=" + format.format(this.date);
    }
}
