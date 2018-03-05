package com.Eu.dao;

import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Interno_estancia;

public class Interno_estanciaDao {
	public Interno_estancia getInternoEstanciaById(int InternoEstanciaId) {
        Session sesion = null;
        Interno_estancia ie = null;
        try {            
            sesion = HibernateUtil.getSessionfactory().openSession();
            ie =  (Interno_estancia)sesion.get(Interno_estancia.class,
            		InternoEstanciaId);
            Hibernate.initialize(ie);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return ie;
    }
	
	public void addInterno_estancia(Interno_estancia ine) throws SQLException {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.save(ine);
	        sesion.getTransaction().commit();
	    } catch(Exception e){
	        e.printStackTrace();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
}

