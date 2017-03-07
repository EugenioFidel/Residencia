package com.Eu.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Interno;

public class InternoDao {
	public void addInterno(Interno in) throws SQLException {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.save(in);
	        sesion.getTransaction().commit();
	    } catch(Exception e){
	        e.printStackTrace();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	public void updateInterno(Interno in) throws SQLException {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.update(in);
	        sesion.getTransaction().commit();
	    } catch(Exception e){
	        e.printStackTrace();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public List<Object> listaInternos(){
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        @SuppressWarnings("unchecked")
			List<Object>lista=sesion.createQuery("From Interno ORDER BY primerApe,segundoApe,nombre").list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
	
	public void deleteInterno(Interno i) {
		  Session sesion = null;
	        try{
		        sesion = HibernateUtil.getSessionfactory().openSession();
		        sesion.beginTransaction();
	        	Query query = sesion.createQuery("delete Interno where dni like '"+i.getDni()+"'");        	 
	        	int result = query.executeUpdate();        	 
	        	if (result > 0) {
	        	    System.out.println("interno borrado");
	        	}
	        	sesion.getTransaction().commit();
		    } finally {
		        if ((sesion != null) && (sesion.isOpen()))
		        sesion.close();
		    }
		
	}
	
	public void addInternoConId(Object[] datos) {
		Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        String q="insert into interno values("+datos[0].toString()+",'"+datos[1]+"','"+datos[2]+"','"+datos[3]+"')";
        	Query query = sesion.createSQLQuery(q);        	 
        	int result = query.executeUpdate();        	 
        	if (result > 0) {
        	    System.out.println("interno incluido");
        	}
        	sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
	
	public List<Object> listaInternosPorDni(String dni){
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        Query query = sesion.createQuery("from Interno where dni = '"+dni+"'");
	        @SuppressWarnings("unchecked")
			List<Object> lista = query.list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
	
	public Interno getInternoById(int i) {
	    Session sesion = null;
	    Interno interno = null;
	    try {
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        interno = (Interno)sesion.get(Interno.class, i);
	        //Hibernate.initialize(interno);
	    } catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	    return interno;
	}
	
	public void update(Interno i)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        Query query = sesion.createQuery("update Interno set "+
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
	        		", cc=:cc"+
	        		", ss=:ss"+
    				" where dni = :dni");
	        query.setParameter("letraCif", i.getLetraCif());
	        query.setParameter("letraNif", i.getLetraNif());
	        query.setParameter("nombre", i.getNombre());
	        query.setParameter("primerApellido", i.getPrimerApe());
	        query.setParameter("segundoApellido", i.getSegundoApe());
	        query.setParameter("direccion", i.getDireccion());
	        query.setParameter("localidad", i.getLocalidad());
	        query.setParameter("municipio", i.getMunicipio());
	        query.setParameter("cp", i.getCp());
	        query.setParameter("provincia", i.getProvincia());
	        query.setParameter("telefono1", i.getTelefono1());
	        query.setParameter("telefono2", i.getTelefono2());
	        query.setParameter("email", i.getEmail());	        
	        query.setParameter("dni", i.getDni());
	        query.setParameter("cc", i.getCc());
	        query.setParameter("ss", i.getSs());
	        int result = query.executeUpdate();
	        System.out.println(result);
	        sesion.getTransaction().commit();
	        
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
}
