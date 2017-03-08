package com.Eu.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/*
 * Clase Observacion. Define cada una de las valoraciones de dependencia para un interno
 * 		idObservacion:		Clave primaria que identifica cada acto de observación de dependencias
 * 		fechaObservacion:	Fecha de realización de la observación
 * 		alimentacion:		Varchar que marca si existe dependencia en este area
 * 		movilidad:			Varchar que marca si existe dependencia en este area
 * 		vestido:			Varchar que marca si existe dependencia en este area
 * 	    aseo:			    Varchar que marca si existe dependencia en este area
 * 		inodoro				Varchar que marca si existe dependencia en este area
 * 		esfinteres			Varchar que marca si existe dependencia en este area
 * 		gradoDependencia	String que establece el grad de dependencia determinado por la observación (0 = válido,1= AG I, 2=AG II)
 */

@Entity
@Table(name="observacion")
public class Observacion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6929146278055416713L;
	private int idObservacion;	
	private Date fechaObservacion;	
	private String alimentacion;
	private String movilidad;
	private String aseo;
	private String vestido;	
	private String inodoro;	
	private String esfinteres;
	private String gradoDependencia;	
	
	public Observacion(){
		
	}
	
	public Observacion(Date fechaObservacion, String alimentacion, String movilidad,
			String aseo, String vestido, String inodoro, String esfinteres, String gradoDependencia) {
		super();
		this.fechaObservacion = fechaObservacion;
		this.alimentacion = alimentacion;
		this.movilidad = movilidad;
		this.aseo=aseo;
		this.vestido = vestido;
		this.inodoro = inodoro;
		this.esfinteres = esfinteres;
		this.gradoDependencia = gradoDependencia;
	}
	

	@Id
	@GeneratedValue	
	@Column(name="idObservacion")
	public int getIdObservacion() {
		return idObservacion;
	}

	public void setIdObservacion(int idObservacion) {
		this.idObservacion = idObservacion;
	}

	//@Column(name="fechaObservacion",columnDefinition = "TINYINT")
	@Column(name="fechaObservacion")
	public Date getFechaObservacion() {
		return fechaObservacion;
	}

	public void setFechaObservacion(Date fechaObservacion) {
		this.fechaObservacion = fechaObservacion;
	}

	//@Column(name="alimentacion",columnDefinition = "TINYINT")
	@Column(name="alimentacion")
	public String getAlimentacion() {
		return alimentacion;
	}

	public void setAlimentacion(String alimentacion) {
		this.alimentacion = alimentacion;
	}

	//@Column(name="movilidad",columnDefinition = "TINYINT")
	@Column(name="movilidad")
	public String getMovilidad() {
		return movilidad;
	}

	public void setMovilidad(String movilidad) {
		this.movilidad = movilidad;
	}	
	
	//@Column(name="aseo",columnDefinition = "TINYINT")
	@Column(name="aseo")
	public String getAseo() {
		return aseo;
	}

	public void setAseo(String aseo) {
		this.aseo = aseo;
	}

	//@Column(name="vestido",columnDefinition = "TINYINT")
	@Column(name="vestido")
	public String getVestido() {
		return vestido;
	}

	public void setVestido(String vestido) {
		this.vestido = vestido;
	}

	//@Column(name="inodoro",columnDefinition = "TINYchar")
	@Column(name="inodoro")
	public String getInodoro() {
		return inodoro;
	}

	public void setInodoro(String inodoro) {
		this.inodoro = inodoro;
	}

	//@Column(name="esfinteres",columnDefinition = "TINYINT")
	@Column(name="esfinteres")
	public String getEsfinteres() {
		return esfinteres;
	}

	public void setEsfinteres(String esfinteres) {
		this.esfinteres = esfinteres;
	}

	@Column(name="gradoDependencia")
	public String getGradoDependencia() {
		return gradoDependencia;
	}

	public void setGradoDependencia(String gradoDependencia) {
		this.gradoDependencia = gradoDependencia;
	}	
		
}
