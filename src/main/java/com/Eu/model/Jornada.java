package com.Eu.model;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jornada")
public class Jornada {
	private int idJornada;
	private Date inicioJornada;
	private Date finJornada;
	
	public Jornada(){
		
	}

	public Jornada(int idJornada, Date inicioJornada, Date finJornada) {
		super();
		this.idJornada = idJornada;
		this.inicioJornada = inicioJornada;
		this.finJornada = finJornada;
	}

	@Id
	@GeneratedValue	
	@Column(name="idJornada")
	public int getIdJornada() {
		return idJornada;
	}

	public void setIdJornada(int idJornada) {
		this.idJornada = idJornada;
	}
	
	@Column(name="inicioJornada")
	public Date getInicioJornada() {
		return inicioJornada;
	}

	public void setInicioJornada(Date inicioJornada) {
		this.inicioJornada = inicioJornada;
	}

	@Column(name="finJornada")
	public Date getFinJornada() {
		return finJornada;
	}

	public void setFinJornada(Date finJornada) {
		this.finJornada = finJornada;
	}	
}

