package com.Eu.controladores;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
* Clase que permite conectar con la base de datos
*/

public class dbConexion {
	   static String bd = "residentor";
	   static String login = "root";
	   static String password = "hierba artificial";
	   static String url = "jdbc:mysql://localhost/"+bd;

	   Connection con = null;

	   /** Constructor de DbConnection */
	   public dbConexion() {
	      try{
	         //obtenemos el driver de para mysql
	         Class.forName("com.mysql.jdbc.Driver");
	         //obtenemos la conexi�n
	         con= DriverManager.getConnection(url,login,password);
	         if (con==null){
	            System.out.println("error en la conexión a la base "+bd);
	         }
	      }catch(SQLException e){
	         System.out.println(e);
	      }catch(ClassNotFoundException e){
	         System.out.println(e);
	      }
	   }
	   /**Permite retornar la conexi�n*/
	   public Connection getConexion(){
	      return con;
	   }

	   public void desconectar(){
	      con = null;
	   }
}
