package com.Eu.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Observacion;

public class ObservacionDao {
	public List<Observacion> listaObservaciones(int idPersona){
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        String sqlQuery="select observacion.* from observacion,interno_observacion where"
	        		+" interno_observacion.idObservacion=observacion.idObservacion and "
	        		+ "interno_observacion.idPersona like '"+idPersona
	        		+ "' order by observacion.fechaObservacion desc";
	        SQLQuery query = sesion.createSQLQuery(sqlQuery);
	        @SuppressWarnings("unchecked")
			List<Observacion> lista = (List<Observacion>)query.list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public void updateObservacion(Observacion o)  {
        Session sesion = null;
        try{
        	
        	sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        Query query = sesion.createQuery("update Observacion set "+
	        		"fechaObservacion=:fechaObservacion"+
	        		", alimentacion=:alimentacion"+
	        		", movilidad=:movilidad"+
	        		", aseo=:aseo"+
	        		", vestido=:vestido"+
	        		", inodoro=:inodoro"+
	        		", esfinteres=:esfinteres"+
	        		", gradoDependencia=:gradoDependencia"+
    				" where idObservacion = :idObservacion");
	        query.setParameter("fechaObservacion", o.getFechaObservacion());
	        query.setParameter("alimentacion", o.getAlimentacion());
	        query.setParameter("movilidad", o.getMovilidad());
	        query.setParameter("aseo", o.getAseo());
	        query.setParameter("vestido", o.getVestido());
	        query.setParameter("inodoro", o.getInodoro());
	        query.setParameter("esfinteres",o.getInodoro());
	        query.setParameter("gradoDependencia", o.getGradoDependencia());
	        query.setParameter("idObservacion", o.getIdObservacion());
	        
	        int result = query.executeUpdate();
	        System.out.println(result);
	        sesion.getTransaction().commit();
	        
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public void addObservacion(Observacion o)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.save(o);
	        sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public void deleteObservacion(Observacion o)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.delete(o);
	        sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public Observacion getObservacionById(int i) {
	    Session sesion = null;
	    Observacion o = null;
	    try {
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        o = (Observacion)sesion.get(Observacion.class, i);
	        //Hibernate.initialize(interno);
	    } catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	    return o;
	}
	
	
}
