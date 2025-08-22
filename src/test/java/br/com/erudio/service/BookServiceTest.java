package br.com.erudio.service;

import br.com.erudio.data.dto.BookDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import br.com.erudio.unitetests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        ObjectMapper objectMapper = new ObjectMapper(new org.modelmapper.ModelMapper());
        service = new BookService(repository, objectMapper);
    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<BookDTO> books = service.findAll();

        assertNotNull(books);
        assertEquals(14, books.size());

        var book1 = books.get(1);
        assertNotNull(book1);
        assertNotNull(book1.getId());
        assertNotNull(book1.getLinks());
        assertEquals("Some Author1", book1.getAuthor());
        assertEquals(BigDecimal.valueOf(25), book1.getPrice());
        assertEquals("Some Title1", book1.getTitle());
        assertNotNull(book1.getLaunchDate());

        assertHateoas(book1);

        var book4 = books.get(4);
        assertNotNull(book4);
        assertNotNull(book4.getId());
        assertNotNull(book4.getLinks());
        assertEquals("Some Author4", book4.getAuthor());
        assertEquals(BigDecimal.valueOf(25), book4.getPrice());
        assertEquals("Some Title4", book4.getTitle());
        assertNotNull(book4.getLaunchDate());

        assertHateoas(book4);

        var book7 = books.get(7);
        assertNotNull(book7);
        assertNotNull(book7.getId());
        assertNotNull(book7.getLinks());
        assertEquals("Some Author7", book7.getAuthor());
        assertEquals(BigDecimal.valueOf(25), book7.getPrice());
        assertEquals("Some Title7", book7.getTitle());
        assertNotNull(book7.getLaunchDate());

        assertHateoas(book7);

    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        BookDTO result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertEquals("Some Author1", result.getAuthor());
        assertEquals(BigDecimal.valueOf(25), result.getPrice());
        assertEquals("Some Title1", result.getTitle());
        assertNotNull(result.getLaunchDate());

        assertHateoas(result);
    }

    @Test
    void create() {
//        Book book = input.mockEntity(1);
        Book persisted = input.mockEntity(1);
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.save(any(Book.class))).thenReturn(persisted);
        BookDTO result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertEquals("Some Author1", result.getAuthor());
        assertEquals("Some Title1", result.getTitle());
        assertNotNull(result.getLaunchDate());

        assertHateoas(result);
    }

    @Test
    void testCreateWithNullBook() {
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
        Book book = input.mockEntity(1);
        Book persisted = input.mockEntity(1);
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        BookDTO result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertEquals("Some Author1", result.getAuthor());
        assertEquals(BigDecimal.valueOf(25), result.getPrice());
        assertEquals("Some Title1", result.getTitle());
        assertNotNull(result.getLaunchDate());

        assertHateoas(result);
    }

    @Test
    void testUpdateWithNullBook() {
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
        Book book = input.mockEntity(1);
        book.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repository);
    }

    private static void assertHateoas(BookDTO book) {
        Long id = book.getId();

        assertTrue(book.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/book/v1/" + id) &&
                                link.getType().equals("GET")
                ));

        assertTrue(book.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("GET")
                ));

        assertTrue(book.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("POST")
                ));

        assertTrue(book.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("PUT")
                ));

        assertTrue(book.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/book/v1/" + id) &&
                                link.getType().equals("DELETE")
                ));
    }

}