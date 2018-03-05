//package com.Eu.controladores;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
////import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
////import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
////import org.jasypt.hibernate4.encryptor.HibernatePBEEncryptorRegistry;
//public class HibernateUtil {
//	private static final SessionFactory sessionFactory = buildSessionFactory();
//
//	private static SessionFactory buildSessionFactory() {	 
//		try {
//			// TODO Auto-generated method stub
////			EnvironmentStringPBEConfig envConfig = new EnvironmentStringPBEConfig();
////		    envConfig.setPasswordEnvName("PSS_RES");
////		    System.out.println(envConfig.getPasswordEnvName());
////		    System.out.println(envConfig.getPassword());
////		    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor(); 
////		    HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance(); 
////		    encryptor.setAlgorithm("PBEWithMD5AndTripleDES"); 
////		    encryptor.setConfig(envConfig); 
////		    
////		    registry.registerPBEStringEncryptor("configurationHibernateEncryptor", encryptor); 
//
//		    Configuration configuration=new Configuration();
//			configuration.configure("hibernate.cfg.xml");
//			ServiceRegistry serviceRegistry=new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//			SessionFactory  sessionFactory= configuration.buildSessionFactory(serviceRegistry);				
//			
//			return sessionFactory;
//			
//		} catch (Throwable e) {
//			// TODO: handle exception
//			System.err.println("error en la conexion "+e );
//			throw new ExceptionInInitializerError();
//		}
//		
//	}
//
//	public static SessionFactory getSessionfactory() {
//		return sessionFactory;
//	}	
//}

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


