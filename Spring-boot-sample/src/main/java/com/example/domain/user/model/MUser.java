package com.example.domain.user.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "m_user")
public class MUser {
	@Id
	private String userId;
	private String password;
	private String userName;

	private Date birthday;

	private Integer age;
	private Integer gender;

	private Integer departmentId;

	private String role;
	@Transient
	private Department department;
	@Transient
	private List<Salary> salaryList;
}
