package com.writeabyte.entities;

import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique=true)
	private String username;

	private String name;

	private String email;

	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registration_date")
	private Date registrationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_date")
	private Date lastUpdatedDate;

	@JsonIgnore
	@Transient
	private HibernateProxy hibernateLazyInitializer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
