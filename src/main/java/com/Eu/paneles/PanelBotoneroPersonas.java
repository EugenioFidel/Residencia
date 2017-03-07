package com.Eu.paneles;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotoneroPersonas extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton jbAnhadir,jbBorrar;
	
	public PanelBotoneroPersonas() {
		// TODO Auto-generated constructor stub
		this.setLayout(new GridLayout(1,32,10,10));
		
		jbAnhadir=new JButton("Añadir");
		jbBorrar=new JButton("Borrar");
				
		this.add(jbAnhadir);
		this.add(jbBorrar);		
		
	}
}
