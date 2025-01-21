package com.lmlasmo.shrul.model;

import com.lmlasmo.shrul.dto.model.LinkDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;

@Entity
@Table(name = "links")
public class Link {

	@Id
	private String id;

	@Column
	private String destination;

	@ManyToOne
	@JoinColumn(name = "prefix_id")
	private Prefix prefix;

	public Link() {}

	public Link(@Valid LinkDTO linkDTO) {
		this.destination = linkDTO.getDestination();
		this.prefix = new Prefix();
		this.prefix.setId(linkDTO.getPrefix());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Prefix getPrefix() {
		return prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

}
