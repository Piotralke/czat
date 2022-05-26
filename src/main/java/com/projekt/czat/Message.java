package com.projekt.czat;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMER_MESSAGE")
class Message {

    private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;

    private String description;
    private Status status;

    public Message() {}

    Message(String description, Status status) {

        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Message))
            return false;
        Message message = (Message) o;
        return Objects.equals(this.id, message.id) && Objects.equals(this.description, message.description)
                && this.status == message.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status);
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + this.id + ", description='" + this.description + '\'' + ", status=" + this.status + '}';
    }
}
