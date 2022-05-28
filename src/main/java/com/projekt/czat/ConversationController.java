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

@RestController
class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ConversationModelAssembler assembler;

    ConversationController(ConversationRepository conversationRepository, ConversationModelAssembler assembler) {

        this.conversationRepository = conversationRepository;
        this.assembler = assembler;
    }

    @GetMapping("/people/{id}/conversations")
    public CollectionModel<EntityModel<Conversation>> getPersonConversations(@PathVariable Long id) {
        List<Conversation> temp = new ArrayList<>();
        List<Conversation> conversations = conversationRepository.findAll();
        for(Conversation conversation : conversations){
            if((conversation.getFirstId().equals(id))||conversation.getSecondId().equals(id))
            {
                temp.add(conversation);
            }
        }
        List<EntityModel<Conversation>> conversationEntity = temp.stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(conversationEntity, linkTo(methodOn(ConversationController.class).getPersonConversations(id)).withSelfRel());

    }

}
