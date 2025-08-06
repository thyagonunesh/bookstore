package br.com.erudio.service;

import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(Long id) {
        logger.info("Finding one Person with id: " + id);

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Leandro");
        person.setLastName("Costa");
        person.setAddress("Uberlândia - MG");
        person.setGender("Male");
        return person;
    }

    public List<Person> findAll() {
        logger.info("Finding all Persons");
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            persons.add(mockPerson(i));
        }

        return persons;
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Firstname " + i);
        person.setLastName("Lastname " + i);
        person.setAddress("Address " + i);
        person.setGender("Male");
        return person;
    }

    public Person create(Person person) {
        logger.info("Creating one Person");
        return person;
    }

    public Person update(Person person) {
        logger.info("Updating one Person");
        return person;
    }

    public void delete(Long id) {
        logger.info("Deleting one Person with id: " + id);
    }

}
