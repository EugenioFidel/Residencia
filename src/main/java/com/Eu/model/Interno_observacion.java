package com.Eu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="interno_observacion")

public class Interno_observacion {
	private int idPersona;
	private int idObservacion;
	
	public Interno_observacion(){
		
	}

	public Interno_observacion(int idPersona, int idObservacion) {
		super();
		this.idPersona = idPersona;
		this.idObservacion = idObservacion;
	}
	
	
	@Column(name="idPersona")
	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	
	@Id
	@Column(name="idObservacion")
	public int getIdObservacion() {
		return idObservacion;
	}

	public void setIdObservacion(int idObservacion) {
		this.idObservacion = idObservacion;
	}
	
	
	
	
}
