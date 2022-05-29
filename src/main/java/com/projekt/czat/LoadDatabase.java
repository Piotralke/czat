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
            personRepository.save(new Person("Bilbo", "Baggins", "bilbo123", "gandalf321"));
            personRepository.save(new Person("Frodo", "Baggins", "frodo420", "ring2137"));

            personRepository.findAll().forEach(person -> log.info("Preloaded " + person));


            messageRepository.save(new Message(5l,1l,"MacBook Pro", Status.WYSLANO));
            messageRepository.save(new Message(5l,2l,"iPhone", Status.ODCZYTANO));

            messageRepository.findAll().forEach(message -> {
                log.info("Preloaded " + message);

            });
            conversationRepository.save(new Conversation(1l,2l));

            conversationRepository.findAll().forEach(conversation -> log.info("Preloaded " + conversation));
        };
    }
}
