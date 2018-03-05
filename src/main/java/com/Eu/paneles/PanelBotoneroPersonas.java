package com.Eu.paneles;

import java.awt.Font;
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
	private JButton jbAnhadir,jbBorrar,jbSalir;
	
	Font fuente=new Font("Ubuntu",Font.PLAIN,16);
	Font fuenteN=new Font("Ubuntu",Font.BOLD,16);
	
	public PanelBotoneroPersonas() {
		// TODO Auto-generated constructor stub
		this.setLayout(new GridLayout(1,32,10,10));
		
		jbAnhadir=new JButton("Añadir");
		jbAnhadir.setFont(fuenteN);
		jbBorrar=new JButton("Borrar");
		jbBorrar.setFont(fuenteN);
		jbSalir=new JButton("SALIR");
		jbSalir.setFont(fuenteN);
		
		jbAnhadir.addActionListener(this);
		jbBorrar.addActionListener(this);
		jbSalir.addActionListener(this);
				
		this.add(jbAnhadir);
		this.add(jbBorrar);		
		this.add(jbSalir);
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
		}else if (e.getSource().equals(jbBorrar)){
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
		}else{
			System.exit(0);
		}
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
}
