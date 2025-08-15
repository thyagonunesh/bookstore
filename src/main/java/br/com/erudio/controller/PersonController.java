package br.com.erudio.controller;

import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable Long id) {
        PersonDTO person = service.findById(id);
        person.setBirthDay(new Date());
//        person.setPhoneNumber("99999999");
        person.setPhoneNumber("");
        person.setLastName(null);
        person.setSensitiveData("Foo Bar");
        return person;
    }

    @PostMapping
    public PersonDTO create(@RequestBody PersonDTO person) {
        return service.create(person);
    }

    @PutMapping
    public PersonDTO update(@RequestBody PersonDTO person) {
        return service.update(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
