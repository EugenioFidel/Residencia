package com.Eu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="empleado_jornada")
public class Empleado_jornada {
	private int idEmpleado;
	private int idJornada;
	
	public Empleado_jornada(){}

	public Empleado_jornada(int idEmpleado, int idJornada) {
		super();
		this.idEmpleado = idEmpleado;
		this.idJornada = idJornada;
	}
	
	@Column(name="idEmpleado")
	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	@Id
	@Column(name="idJornada")
	public int getIdJornada() {
		return idJornada;
	}

	public void setIdJornada(int idJornada) {
		this.idJornada = idJornada;
	}
	
	
}
