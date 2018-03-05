package com.Eu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="empleado_contrato")
public class Empleado_contrato {
	private int idContrato;
	private int idEmpleado;
	private int idSustituido;
	
	public Empleado_contrato(){}

	public Empleado_contrato(int idContrato, int idEmpleado, int idSustituido) {
		super();
		this.idContrato = idContrato;
		this.idEmpleado = idEmpleado;
		this.idSustituido = idSustituido;
	}

	@Id
	@Column(name="idContrato")
	public int getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}

	@Column(name="idEmpleado")
	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	@Column(name="idSustituido")
	public int getIdSustituido() {
		return idSustituido;
	}

	public void setIdSustituido(int idSustituido) {
		this.idSustituido = idSustituido;
	}
	
	
}
