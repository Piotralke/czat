package com.projekt.czat;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class MessageModelAssembler implements RepresentationModelAssembler<Message, EntityModel<Message>> {

    @Override
    public EntityModel<Message> toModel(Message message) {


        EntityModel<Message> messageModel = EntityModel.of(message,
                linkTo(methodOn(MessageController.class).all(message.getConversationId())).withRel("messages"),
                linkTo(methodOn(MessageController.class).allMessages()).withSelfRel());




        return messageModel;
    }
}
