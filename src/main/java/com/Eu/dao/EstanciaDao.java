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
	        String sqlQuery="select estancia.idEstancia, interno.idPersona,estancia.fechaAlta,"+
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
}
