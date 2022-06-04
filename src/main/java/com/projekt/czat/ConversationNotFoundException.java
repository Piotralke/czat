package com.projekt.czat;

public class ConversationNotFoundException extends RuntimeException{
    ConversationNotFoundException(Long id) {
        super("Could not find conversation " + id);
    }
}
