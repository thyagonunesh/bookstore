package br.com.erudio.service;

import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return objectMapper.parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person with id: {}", id);

        Person person = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        
        return objectMapper.parseObject(person, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one Person");

        Person entity = objectMapper.parseObject(person, Person.class);

        return objectMapper.parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO personDto) {
        logger.info("Updating one Person");

        Person entity = repository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personDto.getId()));

        objectMapper.mapTo(personDto, entity);

        Person saved = repository.save(entity);

        return objectMapper.parseObject(saved, PersonDTO.class);
    }


    public void delete(Long id) {
        logger.info("Deleting one Person with id: {}", id);

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        repository.delete(entity);
    }

}
