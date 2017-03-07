package com.Eu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.Eu.dao.EmpleadoDao;
import com.Eu.formularios.AltaEmpleado;

@Entity
@Table(name="empleado")
@PrimaryKeyJoinColumn(name="idPersona")
public class Empleado extends Persona{
	

	@Column(name="cc")
	private String cc;
	
	@Column (name="ss")
	private String ss;
	
	public Empleado(){}



	

	public Empleado(String cc, String ss) {
		super();
		this.cc = cc;
		this.ss = ss;
	}
	
		public Empleado(char letraCif, String dni, char letraNif, String nombre, String primerApe, String segundoApe,
			Date fechaNacimiento, String direccion, String localidad, String municipio, String cp, String provincia,
			String telefono1, String telefono2, String email,String cc,String ss) {
		super(letraCif, dni, letraNif, nombre, primerApe, segundoApe, fechaNacimiento, direccion, localidad, municipio, cp,
				provincia, telefono1, telefono2, email);
		this.cc = cc;
		this.ss = ss;
		// TODO Auto-generated constructor stub
	}




	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}

	public static boolean BorrarEmpleado(JTable t) {
		DefaultTableModel dtm=(DefaultTableModel)t.getModel();
		int fila=t.getSelectedRow();
		String dni=dtm.getValueAt(fila, 0).toString();
		String[] palabras=dni.split("-");
		Empleado e=new Empleado();
		e.setDni(palabras[1].trim());
		EmpleadoDao ed=new EmpleadoDao();
		ed.deleteEmpleado(e);
		dtm.removeRow(fila);
		return false;
	}

	public static boolean AltaNuevoEmpleado() {
		boolean resultado=true;
		AltaEmpleado ae=new AltaEmpleado();		
		ae.setVisible(true);
		return resultado;
	}	
}

