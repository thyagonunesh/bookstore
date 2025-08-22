package br.com.erudio.service;

import br.com.erudio.controller.BookController;
import br.com.erudio.data.dto.BookDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository repository;

    private final ObjectMapper objectMapper;

    public BookService(BookRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public List<BookDTO> findAll() {
        logger.info("Finding all Books");

        List<BookDTO> bookDTOS = objectMapper.parseListObjects(repository.findAll(), BookDTO.class);
        bookDTOS.forEach(BookService::addHateoasLinks);

        return bookDTOS;
    }

    public BookDTO findById(Long id) {
        logger.info("Finding one Book with id: {}", id);

        Book entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        BookDTO dto = objectMapper.parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public BookDTO create(BookDTO bookDTO) {

        if (bookDTO == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Creating one Book");

        Book entity = objectMapper.parseObject(bookDTO, Book.class);

        BookDTO dto = objectMapper.parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public BookDTO update(BookDTO bookDto) {
        logger.info("Updating one Book");

        if (bookDto == null) {
            throw new RequiredObjectIsNullException();
        }

        Book entity = repository.findById(bookDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookDto.getId()));

        objectMapper.mapTo(bookDto, entity);

        Book saved = repository.save(entity);

        BookDTO dto = objectMapper.parseObject(saved, BookDTO.class);
        addHateoasLinks(dto);

        return dto;
    }


    public void delete(Long id) {
        logger.info("Deleting one Book with id: {}", id);

        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        repository.delete(entity);
    }

    private static void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
