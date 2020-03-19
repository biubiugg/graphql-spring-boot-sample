package com.youway.mybiz.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BookDTO {
	private Integer id;
	private String name;
	private String publisher;
	private String strISBN;
	private String authorName;
	private String authorPhoneNum;
    private Integer authorAge;
}
