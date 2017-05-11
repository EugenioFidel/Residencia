package com.Eu.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Empleado;
import com.Eu.model.Interno;
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

	@SuppressWarnings("unchecked")
	public List<Jornada> getJornadasPorFecha(Calendar c) {
		// TODO Auto-generated method stub
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND,0);
		Date dIni=c.getTime();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND,59);
		Date dFin=c.getTime();
		
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        Criteria criteria = sesion.createCriteria(Jornada.class ); 
	        criteria.add( Restrictions.between("inicioJornada", dIni, dFin));
	        return criteria.list();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }

	}

	public int getIdEmpleado(Jornada j) {
		
		Session sesion=null;		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
			String query="select distinct persona.idPersona "
					+"from persona,empleado,empleado_jornada,jornada "
					+"where persona.idPersona=empleado.idPersona "
					+"and empleado.idPersona=empleado_jornada.idEmpleado "
					+"and empleado_jornada.idJornada="+j.getIdJornada();
	        SQLQuery q = sesion.createSQLQuery(query);
	        @SuppressWarnings("unchecked")
			List<Integer> lista= q.list();
	        sesion.getTransaction().commit();
	        int idEmpleado=lista.get(0);
	        return idEmpleado;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }			
	}
	
	public void addJornada(Jornada j) throws SQLException {
	      Session sesion = null;
	        try{
		        sesion = HibernateUtil.getSessionfactory().openSession();
		        sesion.beginTransaction();
		        sesion.saveOrUpdate(j);
		        sesion.getTransaction().commit();
		    } catch(Exception e){
		        e.printStackTrace();
		    } finally {
		        if ((sesion != null) && (sesion.isOpen()))
		        sesion.close();
		    }
		}
}
