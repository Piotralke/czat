package com.projekt.czat;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static com.projekt.czat.ConversationController.*;
@RestController
class MessageController {

    private final MessageRepository messageRepository;
    private final MessageModelAssembler assembler;

    MessageController(MessageRepository messageRepository, MessageModelAssembler assembler) {

        this.messageRepository = messageRepository;
        this.assembler = assembler;
    }
    @PostMapping("/messages")
    ResponseEntity<?> newMessage(@RequestBody Message newMessage) {

        EntityModel<Message> entityModel = assembler.toModel(messageRepository.save(newMessage));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }
    @GetMapping("/people/{id}/conversations/{convId}/messages")
    CollectionModel<EntityModel<Message>> all(@PathVariable Long id,@PathVariable Long convId) {
        List<Message> temp = new ArrayList<>();
        List<Message> messages = messageRepository.findAll();
        for(Message message : messages){
            if((message.getConversationId().equals(convId)))
            {
                temp.add(message);
            }
        }
        List<EntityModel<Message>> messageEntity = temp.stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(messageEntity, linkTo(methodOn(MessageController.class).all(id,convId)).withSelfRel());

    }

    @PutMapping("/messages/{id}")
    ResponseEntity<?> updateMessage(@PathVariable Long id) {

        Message message = messageRepository.findById(id) //
                .orElseThrow(() -> new MessageNotFoundException(id));

        message.setStatus(Status.ODCZYTANO);
        return ResponseEntity.ok(assembler.toModel(messageRepository.save(message)));

    }
}