package com.lmlasmo.shrul.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "links")
public class Link {

	@Id
	@NonNull
	private String id;

	@Column
	private String destination;

	@ManyToOne
	@JoinColumn(name = "prefix_id")
	private Prefix prefix;

	@OneToMany(mappedBy = "link", cascade = CascadeType.REMOVE)
	private Set<UrlAccess> access = new HashSet<>();

}
