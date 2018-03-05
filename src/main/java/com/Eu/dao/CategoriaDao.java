package com.Eu.dao;

import org.hibernate.Session;

import com.Eu.controladores.HibernateUtil;
import com.Eu.model.Categoria;

public class CategoriaDao {

	public void addCategoria(Categoria c)  {
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
}
