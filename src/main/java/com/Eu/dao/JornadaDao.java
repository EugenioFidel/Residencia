package com.Eu.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Jornada;

public class JornadaDao {

	
	public List<Jornada> listaJornadas(int id){
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        @SuppressWarnings("unchecked")
			List<Jornada>lista=sesion.createQuery("select j From Jornada j,Empleado_jornada e where j.idJornada=e.idJornada and e.idEmpleado="+id).list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
}
