package com.youway.mybiz.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.youway.commons.JpaDao;
import com.youway.mybiz.entity.User;



@RepositoryRestResource(path = "users", itemResourceRel = "resource", collectionResourceRel = "resources")
public interface UserRepository extends JpaDao<User,Long>{
}
