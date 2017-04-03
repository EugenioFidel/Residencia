package com.Eu.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Contrato;

public class ContratoDao {
		
	public Contrato getContratoById(int i) {
	    Session sesion = null;
	    Contrato c = null;
	    try {
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        c = (Contrato)sesion.get(Contrato.class, i);
	    } catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	    return c;
	}
	
	public void addContrato(Contrato c)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.saveOrUpdate(c);
	        sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}

	public List<Object> listaContratos(int id) {
		
		Session sesion=null;		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
			String query="SELECT DISTINCT "
					+ "contrato.idContrato, "
					+ "contrato.fechaInicio, contrato.fechaFin, contrato.horas, "
					+ "tipoContrato.tipoContrato, categoria.categoria, empleado_contrato.idSustituido "
					+ "FROM empleado_contrato, contrato, categoria, tipoContrato "
					+ "WHERE empleado_contrato.idEmpleado ="+id+" "
					+ "AND empleado_contrato.idContrato = contrato.idContrato "
					+ "AND contrato.idTipoContrato = tipoContrato.idTipoContrato "
					+ "AND contrato.idCategoria = categoria.idCategoria";
	        SQLQuery q = sesion.createSQLQuery(query);
	        @SuppressWarnings("unchecked")
			List<Object> lista = (List<Object>)q.list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }		
	}
	
	public void deleteContrato(Contrato c)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
        	sesion.delete(c);
        	sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}

}
