package com.projekt.czat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository, MessageRepository messageRepository, ConversationRepository conversationRepository) {

        return args -> {
            personRepository.save(new Person("Bilbo", "Baggins", "burglar"));
            personRepository.save(new Person("Frodo", "Baggins", "thief"));

            personRepository.findAll().forEach(person -> log.info("Preloaded " + person));


            messageRepository.save(new Message("MacBook Pro", Status.COMPLETED));
            messageRepository.save(new Message("iPhone", Status.IN_PROGRESS));

            messageRepository.findAll().forEach(message -> {
                log.info("Preloaded " + message);

            });
            conversationRepository.save(new Conversation(1l,2l));

            conversationRepository.findAll().forEach(conversation -> log.info("Preloaded " + conversation));
        };
    }
}
