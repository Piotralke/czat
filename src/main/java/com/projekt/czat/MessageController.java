package com.projekt.czat;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    
    @DeleteMapping("/messages/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {

        Message message = messageRepository.findById(id) //
                .orElseThrow(() -> new MessageNotFoundException(id));

        if (message.getStatus() == Status.IN_PROGRESS) {
            message.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(messageRepository.save(message)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't cancel an message that is in the " + message.getStatus() + " status"));
    }
    @PutMapping("/messages/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {

        Message message = messageRepository.findById(id) //
                .orElseThrow(() -> new MessageNotFoundException(id));

        if (message.getStatus() == Status.IN_PROGRESS) {
            message.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(messageRepository.save(message)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't complete an message that is in the " + message.getStatus() + " status"));
    }
}