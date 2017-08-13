package com.Eu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.Eu.dao.InternoDao;
import com.Eu.formularios.AltaInterno;

@Entity
@Table(name="interno")
@PrimaryKeyJoinColumn(name="idPersona")
public class Interno extends Persona implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cc;	
	private String ss;
	private int habitacion;
	private String dniResponsable;	
	
	private Set<Observacion> observaciones=new HashSet<Observacion>();
	
	public Interno(){}

	public Interno(char letraCif, String dni, char letraNif,
			String nombre, String primerApe, String segundoApe,
			Date fechaNacimiento, String direccion, String localidad,
			String municipio, String cp, String provincia, String telefono1,
			String telefono2, String email,String cc, String ss,int habitacion,String dniResponsable) {
		super(letraCif,dni,letraNif,nombre,primerApe,segundoApe,fechaNacimiento,
				direccion,localidad,municipio,cp,provincia,telefono1,telefono2,email);
		this.cc = cc;
		this.ss = ss;
		this.habitacion=habitacion;
		this.dniResponsable=dniResponsable;
	}

	@Column(name="cc")
	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	@Column (name="ss")
	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}	
	
	@Column (name="habitacion")
	public int getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(int habitacion) {
		this.habitacion = habitacion;
	}
	
	@Column (name="dniResponsable")	
	public String getDniResponsable() {
		return dniResponsable;
	}

	public void setDniResponsable(String dniPersonaResponsable) {
		this.dniResponsable = dniPersonaResponsable;
	}	
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name = "interno_observacion", joinColumns = { @JoinColumn(name = "idPersona") }, inverseJoinColumns = { @JoinColumn(name = "idObservacion") })
	public Set<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(Set<Observacion> observaciones) {
		this.observaciones = observaciones;
	}
	
	public static boolean AltaNuevoInterno() {
		boolean resultado=true;
		AltaInterno ai=new AltaInterno();		
		ai.setVisible(true);
		return resultado;
	}

	public static boolean BorrarInterno(JTable t) {
		DefaultTableModel dtm=(DefaultTableModel)t.getModel();
		int fila=t.getSelectedRow();
		String dni=dtm.getValueAt(fila, 0).toString();
		String[] palabras=dni.split("-");
		Interno i=new Interno();
		i.setDni(palabras[1].trim());
		InternoDao id=new InternoDao();
		id.deleteInterno(i);
		dtm.removeRow(fila);
		return false;
	}
	
	public boolean addObservacion(Observacion o){
		Set<Observacion>observaciones=this.getObservaciones();
		try{
			observaciones.add(o);
		}catch(Exception e){
			System.out.print(e.getMessage());
			return false;
		}
		return true;
	}
	
}
