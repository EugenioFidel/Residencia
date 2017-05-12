package com.Eu.controladores;

import java.sql.SQLException;

import com.Eu.formularios.FrmPrincipal;

public class Application {
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
//		EmpleadoDao ed=new EmpleadoDao();
//		Empleado e=ed.getEmpleadoById(1);
//		Jornada j=new Jornada();
//		Calendar c=Calendar.getInstance();
//		j.setInicioJornada(c.getTime());
//		j.setFinJornada(c.getTime());
//		Set<Jornada>conjuntoJornadas=e.getJornadas();
//		conjuntoJornadas.add(j);
//		e.setJornadas(conjuntoJornadas);
//		ed.addEmpleado(e);
		
		FrmPrincipal fPrincipal=new FrmPrincipal();
		fPrincipal.setVisible(true);
		
	}

}