package com.youway.mybiz.ql.v1;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.youway.mybiz.dao.AuthorRepository;
import com.youway.mybiz.entity.Author;
import com.youway.mybiz.entity.Book;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        return authorRepository.findById(book.getAuthor().getId()).get();
    }
}