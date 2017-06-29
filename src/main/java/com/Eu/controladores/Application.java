package com.Eu.controladores;

import java.sql.SQLException;

import com.Eu.formularios.FrmPrincipal;

public class Application {
	
	public static void main(String[] args) throws SQLException {
			
		FrmPrincipal fPrincipal=new FrmPrincipal();
		fPrincipal.setVisible(true);
		
	}

}