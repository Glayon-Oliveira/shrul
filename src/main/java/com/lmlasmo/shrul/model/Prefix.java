package com.lmlasmo.shrul.model;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.lmlasmo.shrul.dto.model.PrefixDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "prefixes")
public class Prefix {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	private BigInteger id;

	@Column
	private String prefix;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "prefix", cascade = CascadeType.REMOVE)
	private Set<Link> links = new HashSet<>();

	public Prefix(PrefixDTO prefixDTO) {
		this.prefix = prefixDTO.getPrefix();
		this.user = new User(prefixDTO.getId());		
	}	

}
