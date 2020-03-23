package com.youway.mybiz.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.youway.commons.JpaDao;
import com.youway.mybiz.entity.Book;


@RepositoryRestResource(path = "bookRepo", itemResourceRel = "resource", collectionResourceRel = "resources")
public interface BookRepository extends JpaDao<Book,Long>{
	Book findByIsbn(String isbn);
}
