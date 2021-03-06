package com.projekt.czat;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ConversationModelAssembler implements RepresentationModelAssembler<Conversation, EntityModel<Conversation>> {

    @Override
    public EntityModel<Conversation> toModel(Conversation conversation) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Conversation> conversationModel = EntityModel.of(conversation,
                linkTo(methodOn(ConversationController.class).getPersonConversations(conversation.getFirstId())).withRel("conversations"),
                linkTo(methodOn(ConversationController.class).getPersonConversations(conversation.getSecondId())).withRel("conversations"),
                linkTo(methodOn(ConversationController.class).allConversations()).withSelfRel());

        return conversationModel;
    }
}

