package com.projekt.czat;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class PersonController {

    private final PersonRepository repository;

    private final PersonModelAssembler assembler;

    PersonController(PersonRepository repository, PersonModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }


    @PostMapping("/people")
    ResponseEntity<?> newPeople(@RequestBody Person newPerson) {

        EntityModel<Person> entityModel = assembler.toModel(repository.save(newPerson));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item




    @GetMapping("/people")
    CollectionModel<EntityModel<Person>> all() {

        List<EntityModel<Person>> people = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(people, linkTo(methodOn(PersonController.class).all()).withSelfRel());
    }

    @GetMapping("/people/{id}")
    EntityModel<Person> getByName(@PathVariable Long id) {
        Person temp = null;
        List<Person> people = repository.findAll();
        for(Person person : people){
            if((person.getId()).equals(id))
            {
                temp=person;
                break;
            }
        }
        return assembler.toModel(temp);
    }
    @PutMapping("/people/{id}")
    ResponseEntity<?> replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {

        Person updatedPerson = repository.findById(id) //
                .map(person -> {
                    person.setName(newPerson.getName());
                    person.setRole(newPerson.getRole());
                    return repository.save(person);
                }) //
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return repository.save(newPerson);
                });

        EntityModel<Person> entityModel = assembler.toModel(updatedPerson);


        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/people/{id}")
    ResponseEntity<?> deletePerson(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }






}