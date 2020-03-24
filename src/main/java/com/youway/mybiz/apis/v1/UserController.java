package com.youway.mybiz.apis.v1;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youway.mybiz.dao.UserRepository;
import com.youway.mybiz.entity.Book;
import com.youway.mybiz.entity.User;
import com.youway.mybiz.exception.BookNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

	@Resource
	private UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/u")
	public User newUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	// Aggregate root
	@GetMapping("/allu")
	public List<User> all() {
		logger.info("allUser");
		return userRepository.findAll();
	}
	
	@GetMapping("/u/{id}")
	public User one(@PathVariable Long id) {

		return userRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}
	
	@DeleteMapping("/u/{id}")
	void deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
	}

}
