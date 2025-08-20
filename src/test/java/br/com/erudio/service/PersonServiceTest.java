package br.com.erudio.service;

import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import br.com.erudio.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    private PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        ObjectMapper objectMapper = new ObjectMapper(new org.modelmapper.ModelMapper());
        service = new PersonService(repository, objectMapper);
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<PersonDTO> people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var person1 = people.get(1);
        assertNotNull(person1);
        assertNotNull(person1.getId());
        assertNotNull(person1.getLinks());
        assertEquals("Address Test1", person1.getAddress());
        assertEquals("First Name Test1", person1.getFirstName());
        assertEquals("Last Name Test1", person1.getLastName());
        assertEquals("Female", person1.getGender());

        assertHateoas(person1);

        var person4 = people.get(4);
        assertNotNull(person4);
        assertNotNull(person4.getId());
        assertNotNull(person4.getLinks());
        assertEquals("Address Test4", person4.getAddress());
        assertEquals("First Name Test4", person4.getFirstName());
        assertEquals("Last Name Test4", person4.getLastName());
        assertEquals("Male", person4.getGender());

        assertHateoas(person4);

        var person7 = people.get(7);
        assertNotNull(person7);
        assertNotNull(person7.getId());
        assertNotNull(person7.getLinks());
        assertEquals("Address Test7", person7.getAddress());
        assertEquals("First Name Test7", person7.getFirstName());
        assertEquals("Last Name Test7", person7.getLastName());
        assertEquals("Female", person7.getGender());

        assertHateoas(person7);

    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        PersonDTO result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

        assertHateoas(result);
    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = input.mockEntity(1);
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.save(person)).thenReturn(persisted);
        PersonDTO result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

        assertHateoas(result);
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.create(null);
                });
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        Person persisted = input.mockEntity(1);
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);

        PersonDTO result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

        assertHateoas(result);
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    private static void assertHateoas(PersonDTO person) {
        Long id = person.getId();

        assertTrue(person.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/" + id) &&
                                link.getType().equals("GET")
                ));

        assertTrue(person.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                ));

        assertTrue(person.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                ));

        assertTrue(person.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                ));

        assertTrue(person.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/" + id) &&
                                link.getType().equals("DELETE")
                ));
    }

}