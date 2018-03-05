package com.Eu.dao;

import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Contrato;
import com.Eu.model.Empleado_contrato;

public class Empleado_contratoDao {
	public Empleado_contrato getEmpleado_contratoById(int contratoId) {
        Session sesion = null;
        Empleado_contrato ec = null;
        try {            
            sesion = HibernateUtil.getSessionfactory().openSession();
            ec =  (Empleado_contrato)sesion.get(Empleado_contrato.class,
            		contratoId);
            Hibernate.initialize(ec);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return ec;
    }
	
	public void addEmpleado_contrato(Empleado_contrato ec) throws SQLException {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.save(ec);
	        sesion.getTransaction().commit();
	    } catch(Exception e){
	        e.printStackTrace();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public void deleteEmpleado_contrato(Empleado_contrato ec)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
        	sesion.delete(ec);
        	sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
}
