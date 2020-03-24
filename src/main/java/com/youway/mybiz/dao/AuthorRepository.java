package com.youway.mybiz.dao;

import java.util.List;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.youway.commons.JpaDao;
import com.youway.mybiz.entity.Author;



@RepositoryRestResource(path = "authorRepo", itemResourceRel = "resource", collectionResourceRel = "resources")
public interface AuthorRepository extends JpaDao<Author,Long>{
	List<Author> findByPhoneNum(String phoneNum);
}
