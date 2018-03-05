package com.Eu.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Estancia;

public class EstanciaDao {
	public List<Estancia> listaEstancias(int idPersona){
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        String sqlQuery="select estancia.idEstancia,estancia.fechaAlta,"+
	        				"tipoEstancia.tipoEstancia,estancia.fechaBaja,motivoBaja.motivoBaja "+
	        				"from interno,interno_estancia,estancia,tipoEstancia,motivoBaja "+
	        				"where interno.idPersona='"+idPersona+"' "+
	        				"and interno.idPersona=interno_estancia.idPersona "+
	        				"and interno_estancia.idEstancia=estancia.idEstancia "+
	        				"and estancia.tipoEstancia=tipoEstancia.idTipoEstancia "+
	        				"and estancia.motivoBaja=motivoBaja.idMotivoBaja "+
	        				"order by estancia.fechaAlta desc";
	        SQLQuery query = sesion.createSQLQuery(sqlQuery);
	        @SuppressWarnings("unchecked")
			List<Estancia> lista = (List<Estancia>)query.list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public void addEstancia(Estancia e)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.save(e);
	        sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public void updateEstancia(Estancia e)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.saveOrUpdate(e);
	        sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public void deleteEstancia(Estancia e)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.delete(e);
	        sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public Estancia getEstanciaById(int i) {
	    Session sesion = null;
	    Estancia es = null;
	    try {
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        es = (Estancia)sesion.get(Estancia.class, i);
	    } catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	    return es;
	}
}