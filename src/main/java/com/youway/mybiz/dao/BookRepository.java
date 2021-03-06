package com.youway.mybiz.dao;

import java.util.List;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.youway.commons.JpaDao;
import com.youway.mybiz.entity.Book;


@RepositoryRestResource(path = "bookRepo", itemResourceRel = "resource", collectionResourceRel = "resources")
public interface BookRepository extends JpaDao<Book,Long>{
	List<Book> findByIsbn(String isbn);
}
