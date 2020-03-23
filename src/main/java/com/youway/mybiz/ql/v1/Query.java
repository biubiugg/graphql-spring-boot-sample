package com.youway.mybiz.ql.v1;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.youway.mybiz.dao.AuthorRepository;
import com.youway.mybiz.dao.BookRepository;
import com.youway.mybiz.entity.Author;
import com.youway.mybiz.entity.Book;

public class Query implements GraphQLQueryResolver{
	
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;

	
	public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }
	
	public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public long countBooks() {
        return bookRepository.count();
    }
    public long countAuthors() {
        return authorRepository.count();
    }
}
