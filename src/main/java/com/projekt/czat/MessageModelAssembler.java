package com.projekt.czat;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class MessageModelAssembler implements RepresentationModelAssembler<Message, EntityModel<Message>> {

    @Override
    public EntityModel<Message> toModel(Message message) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Message> messageModel = EntityModel.of(message,
                linkTo(methodOn(MessageController.class).all(message.getConversationId())).withRel("messages"));

        // Conditional links based on state of the order



        return messageModel;
    }
}
