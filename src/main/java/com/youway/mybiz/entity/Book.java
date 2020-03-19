package com.youway.mybiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.youway.commons.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//父类和子类都有@Data注解，要在子类加上EqualsAndHashCode(callSuper = true)
@EqualsAndHashCode(callSuper = true) 
@Entity
@Table(name = "book")
public class Book extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	private String name;
	private String strISBN;
	
	@ManyToOne  
	@JoinColumn(name = "AUTHOR_ID", nullable = false)  
	@NotNull
	private Author author;
	private String publisher;
}
