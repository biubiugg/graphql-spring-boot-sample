package com.youway.mybiz.service;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youway.commons.BaseService;
import com.youway.mybiz.dao.AuthorRepository;
import com.youway.mybiz.dao.BookRepository;
import com.youway.mybiz.dto.BookDTO;
import com.youway.mybiz.entity.Author;
import com.youway.mybiz.entity.Book;

@Service
@Transactional(readOnly = true)
public class BookService extends BaseService<Book, Long> {

	@Resource
	private BookRepository bookRepository;

	@Resource
	private AuthorRepository authorRepository;

	@Transactional(readOnly = false)
	public Book create(BookDTO bookDTO) throws Exception {
		Book book = Optional.ofNullable(bookRepository.findByIsbn(bookDTO.getIsbn())).orElseGet(() -> new Book());
		Author author = Optional.ofNullable(authorRepository.findByPhoneNum(bookDTO.getAuthorPhoneNum()))
				.orElseGet(() -> new Author());
		author.setName(bookDTO.getAuthorName());
		author.setPhoneNum(bookDTO.getAuthorPhoneNum());
		author.setAge(bookDTO.getAuthorAge());
		mapper(bookDTO, book);
		book.setAuthor(author);
		return save(book);
	}

}
