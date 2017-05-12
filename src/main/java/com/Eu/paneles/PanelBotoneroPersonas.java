package com.Eu.paneles;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.Eu.formularios.FrmPrincipal;
import com.Eu.model.Empleado;
import com.Eu.model.Interno;
import com.Eu.model.Persona;

public class PanelBotoneroPersonas extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton jbAnhadir,jbBorrar;
	
	public PanelBotoneroPersonas() {
		// TODO Auto-generated constructor stub
		this.setLayout(new GridLayout(1,32,10,10));
		
		jbAnhadir=new JButton("Añadir");
		jbBorrar=new JButton("Borrar");
		
		jbAnhadir.addActionListener(this);
		jbBorrar.addActionListener(this);
				
		this.add(jbAnhadir);
		this.add(jbBorrar);		
	}

	public JButton getJbAnhadir() {
		return jbAnhadir;
	}

	public void setJbAnhadir(JButton jbAnhadir) {
		this.jbAnhadir = jbAnhadir;
	}

	public JButton getJbBorrar() {
		return jbBorrar;
	}

	public void setJbBorrar(JButton jbBorrar) {
		this.jbBorrar = jbBorrar;
	}

	public void actionPerformed(ActionEvent e) {
		//necesitamos el formulario principal
		JRootPane jrp=this.getRootPane();
		FrmPrincipal f=(FrmPrincipal) jrp.getParent();
		JTabbedPane tp=f.getTp();
		int indiceTablaSeleccionada=tp.getSelectedIndex();
		PanelFiltros pf=(PanelFiltros)tp.getComponent(indiceTablaSeleccionada);
		JTable t=pf.getJt();
		@SuppressWarnings("unused")
		boolean r=true;						
			
		//toca ver que queremos añadir o borrar
		if(e.getSource().equals(jbAnhadir)){
			//toca ver que es o que queremos añadir
			switch(indiceTablaSeleccionada){
				//añadimos una persona
				case 0:
					r=Persona.AltaNuevaPersona(t);
					break;
				//añadimos un interno
				case 1:
					r=Interno.AltaNuevoInterno();
					break;
				//añadimos un empleado
				case 2:
					r=Empleado.AltaNuevoEmpleado();														
			}
		}else{
			switch(indiceTablaSeleccionada){
				case 0:
					r=Persona.BorrarPersona(t);
					break;
				case 1:
					r=Interno.BorrarInterno(t);
					break;
				default:
					r=Empleado.BorrarEmpleado(t);
			}
		}	
	}
}
