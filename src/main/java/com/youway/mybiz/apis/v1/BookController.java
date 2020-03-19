package com.youway.mybiz.apis.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youway.mybiz.dao.AuthorRepository;
import com.youway.mybiz.dao.BookRepository;
import com.youway.mybiz.entity.Author;
import com.youway.mybiz.entity.Book;
import com.youway.mybiz.exception.BookNotFoundException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class BookController {
	
	@Resource
	private BookRepository bookRepository;
	@Resource
	private AuthorRepository authorRepository;
	@Autowired
	private BookModelAssembler assembler;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Aggregate root
	@GetMapping("/books")
	public List<Book> allBooks() {
		logger.info("run /books");
		return bookRepository.findAll();
	}
	
	@GetMapping("/books/add")
    public String add(){
        return "book/add";
    }

	@GetMapping("/authors")
	public List<Author> allAuthors() {
		return authorRepository.findAll();
	}

	@PostMapping("/books")
	public Book newEmployee(@RequestBody Book book) {
		return bookRepository.save(book);
	}

	@GetMapping("/books/{id}")
	public Book one(@PathVariable Long id) {

		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}
	
	
	@GetMapping("/allbooks")
	CollectionModel<EntityModel<Book>> all() {
		List<EntityModel<Book>> books = bookRepository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return new CollectionModel<>(books, linkTo(methodOn(BookController.class).all()).withSelfRel());
	}
	

	@PutMapping("/books/{id}")
	public Book updateBook(@RequestBody Book newBook, @PathVariable Long id) {
		Author author = Optional.ofNullable(authorRepository.findByPhoneNum(newBook.getAuthor().getPhoneNum()))
				.orElseGet(() -> new Author());

		return bookRepository.findById(id).map(book -> {
			book.setName(newBook.getName());
			book.setStrISBN(newBook.getStrISBN());
			book.setPublisher(newBook.getPublisher());
			book.setAuthor(author);
			return bookRepository.save(book);
		}).orElseGet(() -> {
			newBook.setId(id);
			newBook.setAuthor(author);
			return bookRepository.save(newBook);
		});
	}

	@DeleteMapping("/books/{id}")
	void deleteEmployee(@PathVariable Long id) {
		bookRepository.deleteById(id);
	}

}
