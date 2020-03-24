package com.youway.mybiz.apis.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.youway.mybiz.entity.User;

@Component
public class UserModelAssembler implements  RepresentationModelAssembler<User, EntityModel<User>> {

	
	 @Override
	  public EntityModel<User> toModel(User user) {

	    // Unconditional links to single-item resource and aggregate root

	    EntityModel<User> bookModel = new EntityModel<>(user,
	      linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
	      linkTo(methodOn(UserController.class).all()).withRel("allbooks")
	    );
	    return bookModel;
	  }
}
