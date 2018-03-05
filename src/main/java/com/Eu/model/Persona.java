package com.Eu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.Eu.dao.PersonaDao;
import com.Eu.formularios.AltaPersona;

@Entity
@Table (name="persona")
@Inheritance(strategy=InheritanceType.JOINED)
public class Persona{
	
	private int idpersona;	
	private char letraCif;
	private String dni;
	private char letraNif;
	private String nombre;
	private String primerApe;
	private String segundoApe;
	private Date fechaNacimiento;
	private String direccion;
	private String localidad;
	private String municipio;
	private String cp;
	private String provincia;
	private String telefono1;
	private String telefono2;
	private String email;
	
	
	public Persona(){}


	public Persona(char letraCif, String dni, char letraNif, String nombre, String primerApe,
			String segundoApe, Date fechaNacimiento, String direccion, String localidad, String municipio, String cp,
			String provincia, String telefono1, String telefono2, String email) {
		
		this.letraCif = letraCif;
		this.dni = dni;
		this.letraNif = letraNif;
		this.nombre = nombre;
		this.primerApe = primerApe;
		this.segundoApe = segundoApe;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.localidad = localidad;
		this.municipio = municipio;
		this.cp = cp;
		this.provincia = provincia;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.email = email;
	}

	@Id
	@GeneratedValue	
	@Column(name="idPersona")	
	public int getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	
	@Column(name="fechaNacimiento")
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	@Column(name="letraCif")
	public char getLetraCif() {
		return letraCif;
	}

	public void setLetraCif(char letraCif) {
		this.letraCif = letraCif;
	}

	@Column(name="dni")
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Column(name="letraNif")
	public char getLetraNif() {
		return letraNif;
	}

	public void setLetraNif(char letraNif) {
		this.letraNif = letraNif;
	}

	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="primerApe")
	public String getPrimerApe() {
		return primerApe;
	}

	public void setPrimerApe(String primerApe) {
		this.primerApe = primerApe;
	}

	@Column(name="segundoApe")
	public String getSegundoApe() {
		return segundoApe;
	}

	public void setSegundoApe(String segundoApe) {
		this.segundoApe = segundoApe;
	}

	@Column(name="direccion")
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name="localidad")
	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Column(name="municipio")
	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	@Column(name="cp")
	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	@Column(name="provincia")
	public String getProvincia() {
		return provincia;
	}
	
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Column(name="telefono1")
	public String getTelefono1() {
		return telefono1;
	}
	
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	@Column(name="telefono2")
	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public static boolean AltaNuevaPersona(JTable t){
		boolean resultado=true;
		AltaPersona ap=new AltaPersona(t);		
		ap.setVisible(true);
		return resultado;
	}
	public static boolean BorrarPersona(JTable t) {
		DefaultTableModel dtm=(DefaultTableModel)t.getModel();
		int fila=t.getSelectedRow();
		String dni=dtm.getValueAt(fila, 0).toString();
		String[] palabras=dni.split("-");
		Persona p=new Persona();
		p.setDni(palabras[1].trim());
		PersonaDao pd=new PersonaDao();
		pd.deletePersona(p);
		dtm.removeRow(fila);
		return false;
	}
	
	public static Object[] PersonaAArray(Persona p){
		Object[]fila={
				p.getLetraCif()+"-"+p.getDni()+"-"+p.getLetraNif(),p.getPrimerApe()+" "+p.getSegundoApe()+", "+p.getNombre(),
				p.getDireccion(),
				p.getLocalidad(),
				p.getMunicipio(),
				p.getCp(),
				p.getProvincia(),
				p.getTelefono1()+"/"+p.getTelefono2(),
				p.getEmail()
		};
		return fila;
	}
}
