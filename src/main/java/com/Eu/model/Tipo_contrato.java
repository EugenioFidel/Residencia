package com.Eu.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tipoContrato")
public class Tipo_contrato {
	@Id
	@GeneratedValue
	@Column(name="idTipoContrato")
	private int idTipoContrato;	

	@Column(name="tipoContrato")
	private String tipoContrato;
	
	@OneToMany(mappedBy="tipoContrato")
	private List<Contrato> contratos=new ArrayList<Contrato>(0);
	
	public Tipo_contrato(){}

	public Tipo_contrato(int idTipoContrato, String tipoContrato, List<Contrato> contratos) {
		super();
		this.idTipoContrato = idTipoContrato;
		this.tipoContrato = tipoContrato;
		this.contratos = contratos;
	}

	
	public int getIdTipoContrato() {
		return idTipoContrato;
	}

	public void setIdTipoContrato(int idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}
	

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}


	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}	
}

