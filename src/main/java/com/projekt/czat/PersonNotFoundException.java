package com.projekt.czat;

class PersonNotFoundException extends RuntimeException {

    PersonNotFoundException(Long id) {
        super("Could not find person " + id);
    }
    PersonNotFoundException(String login) {
        super("Could not find person with login" + login);
    }
}
