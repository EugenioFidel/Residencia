package com.Eu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="interno_estancia")

public class Interno_estancia {
	private int idPersona;
	private int idEstancia;
	
	public Interno_estancia(){
		
	}

	public Interno_estancia(int idPersona, int idEstancia) {
		super();
		this.idPersona = idPersona;
		this.idEstancia = idEstancia;
	}
	
	
	@Column(name="idPersona")
	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	
	@Id
	@Column(name="idEstancia")
	public int getIdEstancia() {
		return idEstancia;
	}

	public void setIdEstancia(int idEstancia) {
		this.idEstancia = idEstancia;
	}
	
	
	
	
}
