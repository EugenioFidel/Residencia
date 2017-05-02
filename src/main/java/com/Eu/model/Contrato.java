package com.Eu.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="contrato")
public class Contrato {	
	private int IdContrato;	
	private Date fechaInicio;	
	private Date fechaFin;	
	private int horas;	
	private Tipo_contrato tipoContrato;
	private Categoria categoria;
	
	public Contrato(){}
	
	public Contrato(int idContrato, Date fechaInicio, Date fechaFin, int horas) {
		super();
		IdContrato = idContrato;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.horas = horas;
	}

	public Contrato(int idContrato, Date fechaInicio, Date fechaFin, int horas, Tipo_contrato tipoContrato,
			Categoria categoriaProfesional) {
		super();
		IdContrato = idContrato;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.horas = horas;
	}

	@Id
	@GeneratedValue	
	@Column(name="idContrato")
	public int getIdContrato() {
		return IdContrato;
	}

	public void setIdContrato(int idContrato) {
		IdContrato = idContrato;
	}
	
	@Column(name="fechaInicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Column(name="fechaFin")
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Column(name="horas")
	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idTipoContrato")
	public Tipo_contrato getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(Tipo_contrato tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idCategoria")
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}			
}
