package com.youway.mybiz.apis.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.youway.mybiz.entity.Book;

/**
 * 对象Model装配
 * @author youway
 *
 */
@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

	
	 @Override
	  public EntityModel<Book> toModel(Book book) {

	    // Unconditional links to single-item resource and aggregate root

	    EntityModel<Book> bookModel = new EntityModel<>(book,
	      linkTo(methodOn(BookController.class).one(book.getId())).withSelfRel(),
	      linkTo(methodOn(BookController.class).all()).withRel("allbooks")
	    );
	    return bookModel;
	  }
}
