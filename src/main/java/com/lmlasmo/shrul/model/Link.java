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
	private String destine;

	@ManyToOne
	@JoinColumn(name = "prefix")
	private Prefix prefix;

	public Link() {}

	public Link(@Valid LinkDTO linkDTO) {
		this.destine = linkDTO.getDestine();
		this.prefix = new Prefix();
		this.prefix.setId(linkDTO.getPrefix());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDestine() {
		return destine;
	}

	public void setDestine(String destine) {
		this.destine = destine;
	}

	public Prefix getPrefix() {
		return prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

}
