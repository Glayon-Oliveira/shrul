package com.lmlasmo.shrul.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails{

	private static final long serialVersionUID = 6465663904620035131L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "create_at")
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
		this.password = signup.getPassword();
		this.firstName = signup.getFirstName();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return new ArrayList<>();		
	}

	@Override
	public String getUsername() {
		return getEmail();
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return !isLocked();
	}
	
	public boolean isEnabled() {		
		return this.status == UserStatus.ACTIVE;
	}
}
