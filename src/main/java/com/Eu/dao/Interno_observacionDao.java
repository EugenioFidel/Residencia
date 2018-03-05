package com.Eu.dao;

import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Interno_observacion;

public class Interno_observacionDao {
	public Interno_observacion getInternoObservacionById(Long Interno_ObservacionId) {
        Session sesion = null;
        Interno_observacion io = null;
        try {            
            sesion = HibernateUtil.getSessionfactory().openSession();
            io =  (Interno_observacion)sesion.get(Interno_observacion.class,
            		Interno_ObservacionId);
            Hibernate.initialize(io);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return io;
    }
	
	public void addInterno_observacion(Interno_observacion ino) throws SQLException {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.save(ino);
	        sesion.getTransaction().commit();
	    } catch(Exception e){
	        e.printStackTrace();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
}
