package com.Eu.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Empleado;

public class EmpleadoDao {
	public void addEmpleado(Empleado emp) throws SQLException {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        sesion.saveOrUpdate(emp);
	        sesion.getTransaction().commit();
	    } catch(Exception e){
	        e.printStackTrace();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public List<Empleado> listaEmpleados(){
		Session sesion=null;
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        @SuppressWarnings("unchecked")
	        List<Empleado>lista=sesion.createQuery("From Empleado e ORDER BY e.primerApe,e.segundoApe,e.nombre").list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
	
	public List<Object> listaEmpleadosAlta() {
		
		Session sesion=null;		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
			String query="select distinct "
					+"persona.dni,persona.primerApe,persona.segundoApe, persona.nombre "
					+"from persona,empleado,empleado_contrato,contrato "
					+"where persona.idPersona=empleado.idPersona "
					+"and empleado.idPersona=empleado_contrato.idEmpleado "
					+"and empleado_contrato.idContrato=contrato.idContrato "
					+"and contrato.fechaFin is null";
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


	public void deleteEmpleado(Empleado e) {
		  Session sesion = null;
	        try{
		        sesion = HibernateUtil.getSessionfactory().openSession();
		        sesion.beginTransaction();
	        	Query query = sesion.createQuery("delete Empleado where dni like '"+e.getDni()+"'");        	 
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

	public void addEmpleadoConId(Object[] datos) {
		Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        String q="insert into empleado values("+datos[0].toString()+",'"+datos[1]+"','"+datos[2]+"')";
        	Query query = sesion.createSQLQuery(q);        	 
        	int result = query.executeUpdate();        	 
        	if (result > 0) {
        	    System.out.println("empleado incluido");
        	}
        	sesion.getTransaction().commit();
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
	
	public void update(Empleado e)  {
        Session sesion = null;
        try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        Query query = sesion.createQuery("update Empleado set "+
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
	        query.setParameter("letraCif", e.getLetraCif());
	        query.setParameter("letraNif", e.getLetraNif());
	        query.setParameter("nombre", e.getNombre());
	        query.setParameter("primerApellido", e.getPrimerApe());
	        query.setParameter("segundoApellido", e.getSegundoApe());
	        query.setParameter("direccion", e.getDireccion());
	        query.setParameter("localidad", e.getLocalidad());
	        query.setParameter("municipio", e.getMunicipio());
	        query.setParameter("cp", e.getCp());
	        query.setParameter("provincia", e.getProvincia());
	        query.setParameter("telefono1", e.getTelefono1());
	        query.setParameter("telefono2", e.getTelefono2());
	        query.setParameter("email", e.getEmail());	        
	        query.setParameter("dni", e.getDni());
	        query.setParameter("cc", e.getCc());
	        query.setParameter("ss", e.getSs());
	        int result = query.executeUpdate();
	        System.out.println(result);
	        sesion.getTransaction().commit();
	        
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
	}
	
	public Empleado getEmpleadoById(int i) {
	    Session sesion = null;
	    Empleado e = null;
	    try {
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        e = (Empleado)sesion.get(Empleado.class, i);
	        
	    } catch (Exception e1) {
	       e1.printStackTrace();
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	    return e;
	}
	
	@SuppressWarnings("unchecked")
	public List<Empleado> listaEmpleadoPorDni(String dni){
		Session sesion=null;
		List<Empleado> lista=new ArrayList<Empleado>();
		
		try{
	        sesion = HibernateUtil.getSessionfactory().openSession();
	        sesion.beginTransaction();
	        lista = sesion.createCriteria(Empleado.class)
	        	    .add( Restrictions.like("dni", dni) ) 
	        	    .list();
	        sesion.getTransaction().commit();
	        return lista;
	    } finally {
	        if ((sesion != null) && (sesion.isOpen()))
	        sesion.close();
	    }
		
	}
}
