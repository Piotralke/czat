package com.projekt.czat;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
class MessageController {

    private final MessageRepository messageRepository;
    private final MessageModelAssembler assembler;

    MessageController(MessageRepository messageRepository, MessageModelAssembler assembler) {

        this.messageRepository = messageRepository;
        this.assembler = assembler;
    }
    @PostMapping("/conversations/{convId}/messages")
    ResponseEntity<?> newMessage(@RequestBody Message newMessage,@PathVariable Long convId) {

        EntityModel<Message> entityModel = assembler.toModel(messageRepository.save(newMessage));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }
    @GetMapping("/messages")
    CollectionModel<EntityModel<Message>> allMessages() {

        List<EntityModel<Message>> messages = messageRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(messages, linkTo(methodOn(MessageController.class).allMessages()).withSelfRel());
    }
    @GetMapping("/conversations/{convId}/messages")
    CollectionModel<EntityModel<Message>> all(@PathVariable Long convId) {
        List<Message> temp = new ArrayList<>();
        List<Message> messages = messageRepository.findAll();
        for(Message message : messages){
            if((message.getConversationId().equals(convId)))
            {
                temp.add(message);
            }
        }
        if(temp.isEmpty())
            throw new ConversationNotFoundException(convId);
        List<EntityModel<Message>> messageEntity = temp.stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(messageEntity, linkTo(methodOn(MessageController.class).all(convId)).withSelfRel());

    }

    @DeleteMapping("/conversations/{convId}/messages")
    ResponseEntity<?> deleteMessages(@PathVariable Long convId) {

        List<EntityModel<Message>> messages = messageRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());
        messages.forEach(test->{
            if(test.getContent().getConversationId().equals(convId)){
                messageRepository.deleteById(test.getContent().getId());
            }
        });
        return ResponseEntity.noContent().build();
    }
}