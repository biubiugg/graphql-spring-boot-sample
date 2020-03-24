package com.youway.mybiz.ql.v1;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.youway.mybiz.dao.AuthorRepository;
import com.youway.mybiz.entity.Author;
import com.youway.mybiz.entity.Book;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepo;

    public BookResolver(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    public Author getAuthor(Book book) {
        return authorRepo.findById(book.getAuthor().getId()).get();
    }
}