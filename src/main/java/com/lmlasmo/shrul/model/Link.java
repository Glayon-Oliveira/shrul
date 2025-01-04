package com.lmlasmo.shrul.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
