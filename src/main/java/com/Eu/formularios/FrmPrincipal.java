package com.Eu.formularios;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import com.Eu.controladores.FuncionesDiversas;
import com.Eu.paneles.PanelBotoneroPersonas;
import com.Eu.paneles.PanelEstancias;
import com.Eu.paneles.PanelFiltros;
import com.Eu.paneles.PanelObservaciones;
import com.Eu.model.Empleado;
import com.Eu.model.Interno;
import com.Eu.model.Persona;


	public class FrmPrincipal extends JFrame implements ActionListener { 		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;		
		
		//Declaraciones para panel personas
		JTabbedPane tp = new JTabbedPane();
		JPanel jpTablas=new JPanel();
		JLayeredPane jlpFuncionalidades=new JLayeredPane();
		
		JPanel jpListados=new JPanel();
		PanelBotoneroPersonas jpb=new PanelBotoneroPersonas();
		enum eventos{Añadir,Editar,Borrar}
		PanelFiltros pfTodos=null;
		PanelFiltros pfInternos=null;
		PanelFiltros pfEmpleados=null;
		//un icono para el formulario
		ImageIcon vaca=new ImageIcon("cowIcon.jpeg");
		
		//JMenuBar, barra de menus
		JMenuBar jmbMenu=new JMenuBar();
		JMenu menuArchivo = new JMenu("Archivo");
		JMenu menuEditar = new JMenu("Editar");
		JMenu menuListados= new JMenu("Listados");
		JMenuItem jmiListadoPersonas=new JMenuItem("Personas");
		JMenuItem jmiListadoInternos=new JMenuItem("Internos");
		JMenuItem jmiListadoEmpleados=new JMenuItem("Empleados");
		JSeparator jsSeparador1MenuListados=new JSeparator();
		JMenuItem jmiListadoTelefonos=new JMenuItem("Teléfonos");
		
		public PanelObservaciones po=null;		
		
		final static Logger loggeador = Logger.getLogger(FrmPrincipal.class);
		
	
	public FrmPrincipal() throws SQLException{
		
		pfTodos=new PanelFiltros(0);
		pfInternos=new PanelFiltros(2);
		pfEmpleados=new PanelFiltros(1);
		
		//características del formulario principal
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		isDefaultLookAndFeelDecorated();
		this.setTitle("Gestión de residencias 1.0");
		
		//Declaramos el layout, GridBagLayout
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints col=new GridBagConstraints();
		this.setLayout(gbl);
		this.setIconImage(vaca.getImage());
		
		
		
		tp.add("Todos",pfTodos);		
		tp.add("Internos",pfInternos);		
		tp.add("Empleados",pfEmpleados);
		
		jpTablas.setLayout(new GridLayout(1,2));
		jpTablas.add(tp);
		
		col.gridx=0;
		col.gridy=0;
		col.anchor=GridBagConstraints.NORTHWEST;
		col.fill=GridBagConstraints.BOTH;
		col.weightx=0.05;
		col.weighty=1.0;
		col.insets=new Insets(5,5,5,5);
		this.getContentPane().add(jpTablas,col);
		col.anchor=GridBagConstraints.CENTER;
        col.fill=GridBagConstraints.NONE;
        col.weightx=0;
        col.weighty=0;
        
        col.gridx=0;
        col.gridy=1;
        col.anchor=GridBagConstraints.EAST;
        this.getContentPane().add(jpb,col);
        col.anchor=GridBagConstraints.CENTER;
        
        po=new PanelObservaciones(16);
		PanelEstancias pe =new PanelEstancias();
		jlpFuncionalidades.add(pe,new Integer(0));
		jlpFuncionalidades.add(po,new Integer(1));
				        
        col.gridx=1;
        col.gridy=0;
        col.weightx=1.0;
        col.weighty=1.0;
        col.fill=GridBagConstraints.BOTH;
        col.insets=new Insets(5,5,5,5);
        this.getContentPane().add(jlpFuncionalidades,col);
        
        ((JButton)jpb.getComponent(0)).addActionListener(this);
        ((JButton)jpb.getComponent(1)).addActionListener(this);
        
        //=========================================================================================
		// BARRA DE MENU ==========================================================================
		//=========================================================================================
		
		jmbMenu.add(menuArchivo);
		jmbMenu.add(menuEditar);
		menuListados.add(jmiListadoPersonas);
		menuListados.add(jmiListadoInternos);
		menuListados.add(jmiListadoEmpleados);
		menuListados.add(jsSeparador1MenuListados);
		menuListados.add(jmiListadoTelefonos);
		jmbMenu.add(menuListados);
		
		jmiListadoPersonas.addActionListener(this);
		jmiListadoInternos.addActionListener(this);
		jmiListadoEmpleados.addActionListener(this);
		jmiListadoTelefonos.addActionListener(this);
		
		this.setJMenuBar(jmbMenu);
		

		
	}
	public void actionPerformed(ActionEvent e) {		
		//comprobamos el objeto que ha producido el evento
		if(e.getSource().equals(jmiListadoPersonas)){
			FuncionesDiversas.GenerarListadoPersonas();
			}else if(e.getSource().equals(jmiListadoInternos)){
				FuncionesDiversas.GenerarListadoInternos();
				}else if(e.getSource().equals(jmiListadoEmpleados)){
					FuncionesDiversas.GenerarListadoEmpleados();
					}else if(e.getSource().equals(jmiListadoTelefonos)){
						FuncionesDiversas.GenerarListadoTelefonos();
						}else{
							String resultado=((JButton)e.getSource()).getText();
							JTabbedPane jtp=(JTabbedPane)jpTablas.getComponent(0);
							int indiceTablaSeleccionada=jtp.getSelectedIndex();
							PanelFiltros pf=(PanelFiltros)jtp.getComponent(indiceTablaSeleccionada);
							JTable t=pf.getJt();
							boolean r=true;						
								
							//toca ver que queremos añadir o borrar
							if(resultado.equals("Añadir")){
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
							System.out.println(r);		
						}
					}
	
	public JPanel getJpTablas() {
		return jpTablas;
	}
	public void setJpTablas(JPanel jpTablas) {
		this.jpTablas = jpTablas;
	}
	
	public JLayeredPane getJlpFuncionalidades() {
		return jlpFuncionalidades;
	}
	public void setJlpFuncionalidades(JLayeredPane jlpFuncionalidades) {
		this.jlpFuncionalidades = jlpFuncionalidades;
	}
	
	public PanelObservaciones getPo() {
		return po;
	}
	public void setPo(PanelObservaciones po) {
		this.po = po;
	}
}

	
	
