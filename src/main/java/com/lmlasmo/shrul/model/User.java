package com.lmlasmo.shrul.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.lmlasmo.shrul.dto.register.SignupDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	
	@Column
	private String email;
	
	@Column
	private String fistName;
	
	@Column
	private String lastName;
	
	@Column
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@Column
	private boolean locked = false;
	
	@Enumerated(EnumType.STRING)
	private UserStatus status = UserStatus.PENDING;
	
	@OneToMany(mappedBy = "user")
	private Set<Prefix> prefixes = new HashSet<>(); 
	
	public User() {}

	public User(SignupDTO signup) {
		this.email = signup.getEmail();		
		this.fistName = signup.getFistName();
		this.lastName = signup.getLastName();
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	public String getFistName() {
		return fistName;
	}

	public void setFistName(String fistName) {
		this.fistName = fistName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Set<Prefix> getPrefixes() {
		return prefixes;
	}

	public void setPrefixes(Set<Prefix> prefixes) {
		this.prefixes = prefixes;
	}
		
}
