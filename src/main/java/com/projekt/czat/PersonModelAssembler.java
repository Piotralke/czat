package com.projekt.czat;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class PersonModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {

    @Override
    public EntityModel<Person> toModel(Person person) {

        return EntityModel.of(person, //
                linkTo(methodOn(PersonController.class).one(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).getByLogin(person.getLogin())).withSelfRel(),
                linkTo(methodOn(PersonController.class).all()).withRel("people"));
    }
}
