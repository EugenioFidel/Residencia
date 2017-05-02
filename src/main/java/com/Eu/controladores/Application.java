package com.Eu.controladores;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.Eu.dao.EmpleadoDao;
import com.Eu.dao.JornadaDao;
import com.Eu.formularios.FrmPrincipal;
import com.Eu.model.Empleado;
import com.Eu.model.Jornada;

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