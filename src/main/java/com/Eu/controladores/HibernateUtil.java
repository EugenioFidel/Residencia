package com.Eu.controladores;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		// TODO Auto-generated method stub
		try {
			Configuration configuration=new Configuration();
			configuration.configure("hibernate.cfg.xml");
			ServiceRegistry serviceRegistry= 
					new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			SessionFactory  sessionFactory= configuration.buildSessionFactory(serviceRegistry);
			return sessionFactory;
			
		} catch (Throwable e) {
			// TODO: handle exception
			System.err.println("error en la conexion "+e );
			throw new ExceptionInInitializerError();
		}
		
	}

	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
	
	
}
