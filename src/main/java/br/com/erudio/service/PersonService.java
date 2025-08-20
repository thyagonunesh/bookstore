package br.com.erudio.service;

import br.com.erudio.controller.PersonController;
import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository repository;
    private final ObjectMapper objectMapper;

    public PersonService(PersonRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all Persons");

        List<PersonDTO> personDTOS = objectMapper.parseListObjects(repository.findAll(), PersonDTO.class);
        personDTOS.forEach(PersonService::addHateoasLinks);

        return personDTOS;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person with id: {}", id);

        Person entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        PersonDTO dto = objectMapper.parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO create(PersonDTO person) {

        if (person == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Creating one Person");

        Person entity = objectMapper.parseObject(person, Person.class);

        PersonDTO dto = objectMapper.parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO update(PersonDTO personDto) {
        logger.info("Updating one Person");

        if (personDto == null) {
            throw new RequiredObjectIsNullException();
        }

        Person entity = repository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personDto.getId()));

        objectMapper.mapTo(personDto, entity);

        Person saved = repository.save(entity);

        PersonDTO dto = objectMapper.parseObject(saved, PersonDTO.class);
        addHateoasLinks(dto);

        return dto;
    }


    public void delete(Long id) {
        logger.info("Deleting one Person with id: {}", id);

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        repository.delete(entity);
    }

    private static void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
