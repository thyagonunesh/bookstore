package br.com.erudio.service;

import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll() {
        logger.info("Finding all Persons");

        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one Person with id: " + id);

        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
    }

    public Person create(Person person) {
        logger.info("Creating one Person");

        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one Person");

        Person entity = repository
                .findById(person.getId())
                .map(person1 -> {
                    person1.setFirstName(person.getFirstName());
                    person1.setLastName(person.getLastName());
                    person1.setAddress(person.getAddress());
                    person1.setGender(person.getGender());
                    return person1;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + person.getId()));

        return repository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person with id: " + id);

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        repository.delete(entity);
    }

}
