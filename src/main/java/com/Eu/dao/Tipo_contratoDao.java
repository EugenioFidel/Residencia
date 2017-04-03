package com.Eu.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Contrato;
import com.Eu.model.Tipo_contrato;

public class Tipo_contratoDao {

	public void addTipo_contrato(Tipo_contrato tc)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.saveOrUpdate(tc);
	        sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public List<Tipo_contrato>getTipo_contratoPorNombre(String tipo){
		Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        @SuppressWarnings("unchecked")
			List<Tipo_contrato> t = sesion.createCriteria(Tipo_contrato.class)
				    .add( Restrictions.like("tipoContrato",tipo) )
				    .list();
	        return t;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }			
	}
	
	public Tipo_contrato getTipo_contratoById(int i) {
	    Session sesion = null;
	    Tipo_contrato tc = null;
	    try {
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        tc = (Tipo_contrato)sesion.get(Contrato.class, i);
	    } catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	    return tc;
	}
}
