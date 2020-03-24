package com.youway.mybiz.ql.v1;

import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.youway.mybiz.dao.AuthorRepository;
import com.youway.mybiz.dao.BookRepository;
import com.youway.mybiz.entity.Author;
import com.youway.mybiz.entity.Book;
import com.youway.mybiz.graphql.exception.BookNotFoundGraphqlException;


public class Mutation implements GraphQLMutationResolver {
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;
	
	public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }
	
	public Author newAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }
	
	public Book newBook(String isbn, String phoneNum) {
		
		Author author = Optional.ofNullable(authorRepository.findByPhoneNum(phoneNum).get(1))
				.orElseGet(() -> new Author());
       return Optional.ofNullable(bookRepository.findByIsbn(isbn).get(1)).orElseGet(() -> {
    	    Book newBook = new Book();
    	    newBook.setIsbn(isbn);
			newBook.setAuthor(author);
			return bookRepository.save(newBook);
		});
    }
	
	public boolean deleteBook(Long id) {
		bookRepository.deleteById(id);
        return true;
    }
	
	public Book updateBookPageCount(Long id) {
        Book book = bookRepository.findById(id).orElse(new Book());
        if(book == null) {
            throw new BookNotFoundGraphqlException("The book to be updated was found", id);
        }
        bookRepository.save(book);

        return book;
    }
}
