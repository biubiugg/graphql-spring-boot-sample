package com.youway.mybiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.youway.commons.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//父类和子类都有@Data注解，要在子类加上EqualsAndHashCode(callSuper = true)
@EqualsAndHashCode(callSuper = true) 
@Entity
@Table(name = "author",indexes = {@Index(name = "PHONENUM",columnList = "phoneNum")})
public class Author extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	private String phoneNum;
    private String name;
    private Integer age;
}
