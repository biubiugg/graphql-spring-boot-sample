package com.youway.mybiz.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.youway.commons.JpaDao;
import com.youway.mybiz.entity.Author;



@RepositoryRestResource(path = "author", itemResourceRel = "resource", collectionResourceRel = "resources")
public interface AuthorRepository extends JpaDao<Author,Long>{
	Author findByPhoneNum(String phoneNum);
}
