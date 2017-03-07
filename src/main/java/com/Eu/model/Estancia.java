package com.Eu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * Clase Estancia. Define cada uno de los periodos de ingreso en la residencia de un internọ
 * Atributos:
 * 		fechaAlta: 		Fecha de inicio de la estancia en la residencia
 * 		fechaBaja: 		Fecha de finalización de la estancia en la residencia
 * 		motivoBaja: 	En caso de que el usuario se haya dado de baja se señalará el motivo 
 * 							1.- Exitus
 * 							2.- Salida a su domicilio
 * 							3.- Salida a otro centro
 * 		tipoEstancia:	Tipo de estancia del usuario
 * 							1.- Residencial
 * 							2.- Centro de Día
 */

@Entity
@Table(name="estancia")
public class Estancia {
	@Id
	@Column(name="idEstancia")
	private int idEstancia;
	@Column(name="idPersona")
	private int idPersona;
	@Column(name="fechaAlta")
	private Date fechaAlta;
	@Column(name="fechaBaja")
	private Date fechaBaja;
	@Column(name="motivoBaja")
	private int motivoBaja;
	@Column(name="tipoEstancia")
	private int tipoEstancia;
	
	@ManyToOne
	@JoinColumn(name="idPersona")
	private Interno interno;
	
	public Estancia(){		
	}

	public Estancia(int idEstancia, int idPersona, Date fechaAlta, Date fechaBaja, int motivoBaja, int tipoEstancia) {
		super();
		this.idEstancia = idEstancia;
		this.idPersona = idPersona;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
		this.motivoBaja = motivoBaja;
		this.tipoEstancia = tipoEstancia;
	}

	public int getIdEstancia() {
		return idEstancia;
	}

	public void setIdEstancia(int idEstancia) {
		this.idEstancia = idEstancia;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public int getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(int motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public int getTipoEstancia() {
		return tipoEstancia;
	}

	public void setTipoEstancia(int tipoEstancia) {
		this.tipoEstancia = tipoEstancia;
	}

	
}
