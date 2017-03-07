package com.Eu.dao;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Persona;

public class PersonaDao {

	public void addPersona(Persona p)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.saveOrUpdate(p);
	        sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public void deletePersona(Persona p)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
        	Query query = sesion.createQuery("delete Persona where dni like '"+p.getDni()+"'");        	 
        	int result = query.executeUpdate();        	 
        	if (result > 0) {
        	    System.out.println("persona borrada");
        	}
        	sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public List<Object> listaPersonas(){
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        @SuppressWarnings("unchecked")
			List<Object>lista=sesion.createQuery("From Persona ORDER BY primerApe,segundoApe,nombre").list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
	
	public List<Object> listaPersonasPorDni(String dni){
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        Query query = sesion.createQuery("from Persona where dni = '"+dni+"'");
	        @SuppressWarnings("unchecked")
			List<Object> lista = query.list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
	
	public void updatePersona(Persona pe)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        Query query = sesion.createQuery("update Persona set "+
	        		"nombre = :nombre" +
	        		", letraCif=:letraCif"+
	        		", letraNif=:letraNif"+
	        		", primerApe=:primerApellido"+
	        		", segundoApe=:segundoApellido"+
	        		", direccion=:direccion"+
	        		", localidad=:localidad"+
	        		", municipio=:municipio"+
	        		", cp=:cp"+
	        		", provincia=:provincia"+
	        		", telefono1=:telefono1"+
	        		", telefono2=:telefono2"+
	        		", email=:email"+
    				" where dni = :dni");
	        query.setParameter("letraCif", pe.getLetraCif());
	        query.setParameter("letraNif", pe.getLetraNif());
	        query.setParameter("nombre", pe.getNombre());
	        query.setParameter("primerApellido", pe.getPrimerApe());
	        query.setParameter("segundoApellido", pe.getSegundoApe());
	        query.setParameter("direccion", pe.getDireccion());
	        query.setParameter("localidad", pe.getLocalidad());
	        query.setParameter("municipio", pe.getMunicipio());
	        query.setParameter("cp", pe.getCp());
	        query.setParameter("provincia", pe.getProvincia());
	        query.setParameter("telefono1", pe.getTelefono1());
	        query.setParameter("telefono2", pe.getTelefono2());
	        query.setParameter("email", pe.getEmail());	        
	        query.setParameter("dni", pe.getDni());
	        int result = query.executeUpdate();
	        System.out.println(result);
	        sesion.getTransaction().commit();
	        
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public List<Object> listaPersonasSinEmpleados(){
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        @SuppressWarnings("unchecked")
			List<Object>lista=sesion.createQuery("From Persona p where p.dni not in (select e.dni from Empleado e )and p.dni not in (select i.dni from Interno i) order by primerApe,segundoApe,nombre").list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
		
}
