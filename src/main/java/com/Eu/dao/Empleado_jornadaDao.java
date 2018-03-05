package com.Eu.dao;

import java.sql.SQLException;

import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Empleado_jornada;

public class Empleado_jornadaDao {
	public void addEmpleado_jornada(Empleado_jornada ej) throws SQLException {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.save(ej);
	        sesion.getTransaction().commit();
	    } catch(Exception e){
	        e.printStackTrace();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
}
