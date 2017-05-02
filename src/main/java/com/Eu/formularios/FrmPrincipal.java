package com.Eu.formularios;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.Eu.controladores.FuncionesDiversas;
import com.Eu.controladores.dbConexion;
import com.Eu.dao.ObservacionDao;
import com.Eu.paneles.PanelBotoneroPersonas;
import com.Eu.paneles.PanelContratos;
import com.Eu.paneles.PanelEstancias;
import com.Eu.paneles.PanelFiltros;
import com.Eu.paneles.PanelJornadas;
import com.Eu.paneles.PanelObservaciones;
import com.toedter.calendar.JDateChooser;
import com.Eu.model.Empleado;
import com.Eu.model.Interno;
import com.Eu.model.Persona;
import com.Eu.model.Observacion;


	public class FrmPrincipal extends JFrame implements ActionListener { 		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;		
		
		final static Logger loggeador = Logger.getLogger(FrmPrincipal.class);
		//Declaraciones para panel personas
		JTabbedPane tp = new JTabbedPane();
		JPanel jpTablas=new JPanel();
		JLayeredPane jlpFuncionalidades=new JLayeredPane();
		
		JPanel jpListados=new JPanel();
		PanelBotoneroPersonas jpb=new PanelBotoneroPersonas();
		enum eventos{Añadir,Editar,Borrar};
		public static int PERSONAS=0;
		public static int EMPLEADOS=1;
		public static int INTERNOS=2;
		PanelFiltros pfTodos=null;
		PanelFiltros pfInternos=null;
		PanelFiltros pfEmpleados=null;
		//un icono para el formulario
		ImageIcon vaca=new ImageIcon("./src/main/resources/cowIcon.jpeg");
		
		//JMenuBar, barra de menus
		JMenuBar jmbMenu=new JMenuBar();
		JMenu menuArchivo = new JMenu("Archivo");
		JMenu menuEditar = new JMenu("Editar");
		JMenu menuListados= new JMenu("Listados");
		JMenu menuInformes=new JMenu("Informes");
		JMenu menuJornadas=new JMenu("Jornadas");
		JMenuItem jmiListadoPersonas=new JMenuItem("Personas");
		JMenuItem jmiListadoInternos=new JMenuItem("Internos");
		JMenuItem jmiListadoEmpleados=new JMenuItem("Empleados");
		JSeparator jsSeparador1MenuListados=new JSeparator();
		JMenuItem jmiListadoTelefonos=new JMenuItem("Teléfonos");
		
		JMenuItem jmiInformeCuotas= new JMenuItem("Cuotas");
		JMenuItem jmiInformeDependencias=new JMenuItem("Dependencias");
		JMenuItem jmiNumClientes=new JMenuItem("Internos por día");
		
		JMenuItem jmiPatron=new JMenuItem("Generar patrón");
		
		public PanelObservaciones po=null;	
		public PanelEstancias pe =null;
		public PanelContratos pc=null;
		public PanelJornadas pj=null;
		public JPanel jpTelon=new JPanel();
		
		public JPanel jpBotoneroInternos=new JPanel();
		JButton jbAnhadir =new JButton("Añadir");
		JButton jbBorrar=new JButton("Borrar");
		JRadioButton jrbObservaciones=new JRadioButton("Observaciones");
		JRadioButton jrbEstancias=new JRadioButton("Estancias");
		JRadioButton jrbContratos=new JRadioButton("Contratos");
		JRadioButton jrbJornadas=new JRadioButton("Jornadas");
		
		ButtonGroup bgPaneles=new ButtonGroup();
		
			
	public FrmPrincipal() throws SQLException{
		
		//características del formulario principal
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FrmPrincipal.isDefaultLookAndFeelDecorated();
		this.setTitle("Gestión de residencias 1.0");
				
		bgPaneles.add(jrbObservaciones);		
		bgPaneles.add(jrbEstancias);	
		bgPaneles.add(jrbContratos);	
		bgPaneles.add(jrbJornadas);	
		
		//Declaramos el layout, GridBagLayout
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints col=new GridBagConstraints();
		this.setLayout(gbl);
		this.setIconImage(vaca.getImage());	
		

		pfTodos=new PanelFiltros(PERSONAS);
		pfInternos=new PanelFiltros(INTERNOS);
		pfEmpleados=new PanelFiltros(EMPLEADOS);
		tp.add("Todos",pfTodos);		
		tp.add("Internos",pfInternos);		
		tp.add("Empleados",pfEmpleados);
		
		jpTablas.setLayout(new GridLayout(1,2));
		jpTablas.add(tp);
		
		col.gridx=0;
		col.gridy=0;
		col.anchor=GridBagConstraints.NORTHWEST;
		col.fill=GridBagConstraints.BOTH;
		col.insets=new Insets(5,5,5,5);
		this.getContentPane().add(jpTablas,col);
		col.anchor=GridBagConstraints.CENTER;
        col.fill=GridBagConstraints.NONE;       
        
        col.gridx=0;
        col.gridy=1;
        col.anchor=GridBagConstraints.EAST;
        this.getContentPane().add(jpb,col);
        col.anchor=GridBagConstraints.CENTER;
        
        po=new PanelObservaciones(1000000);
		pe=new PanelEstancias(1000000);	
		pc=new PanelContratos(1000000);
		pj=new PanelJornadas(1000000);
		
		jlpFuncionalidades.add(pe,JLayeredPane.DEFAULT_LAYER);
		jlpFuncionalidades.add(po,JLayeredPane.DEFAULT_LAYER);
		jlpFuncionalidades.add(pc,JLayeredPane.DEFAULT_LAYER);
		jlpFuncionalidades.add(pj,JLayeredPane.DEFAULT_LAYER);
		jlpFuncionalidades.add(jpTelon, JLayeredPane.DEFAULT_LAYER);
		jpTelon.setSize(800, 600);
		
		jlpFuncionalidades.moveToFront(jpTelon);
		jlpFuncionalidades.moveToBack(po);
		jlpFuncionalidades.moveToBack(pe);		
		jlpFuncionalidades.moveToBack(pc);
		jlpFuncionalidades.moveToBack(pj);
				        
        col.gridx=1;
        col.gridy=0;
        col.weightx=1.0;
        col.weighty=1.0;
        col.fill=GridBagConstraints.BOTH;
        col.insets=new Insets(5,5,5,5);
        this.getContentPane().add(jlpFuncionalidades,col);
        col.weightx=0.0;
        col.weighty=0.0;
        
        jpBotoneroInternos.add(jbAnhadir);
		jpBotoneroInternos.add(jbBorrar);
		jpBotoneroInternos.add(jrbObservaciones);
		jpBotoneroInternos.add(jrbEstancias);
		jpBotoneroInternos.add(jrbContratos);
		jpBotoneroInternos.add(jrbJornadas);
		col.gridx=1;
        col.gridy=1;
        col.insets=new Insets(5,5,5,5);
        this.getContentPane().add(jpBotoneroInternos,col);
		
        
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
		menuInformes.add(jmiInformeCuotas);
		menuInformes.add(jmiInformeDependencias);
		menuInformes.add(jmiNumClientes);
		menuJornadas.add(jmiPatron);
		jmbMenu.add(menuListados);
		jmbMenu.add(menuInformes);
		jmbMenu.add(menuJornadas);
		
		jmiListadoPersonas.addActionListener(this);
		jmiListadoInternos.addActionListener(this);
		jmiListadoEmpleados.addActionListener(this);
		jmiListadoTelefonos.addActionListener(this);
		jmiInformeCuotas.addActionListener(this);
		jmiInformeDependencias.addActionListener(this);
		jmiNumClientes.addActionListener(this);
		jmiPatron.addActionListener(this);
		
		this.setJMenuBar(jmbMenu);		
		
		jbAnhadir.addActionListener(this);
		jbBorrar.addActionListener(this);
		
		jrbObservaciones.addActionListener(this);
		jrbEstancias.addActionListener(this);
		jrbContratos.addActionListener(this);
		jrbContratos.addActionListener(this);
		jrbObservaciones.setSelected(true);
	}
	
	public void actionPerformed(ActionEvent e) {	
		
		//comprobamos el objeto que ha producido el evento
		if(e.getSource().equals(jrbJornadas)){
			loggeador.debug("pulso en jrbJornadas");
			jlpFuncionalidades.moveToFront(pj);
		}else if(e.getSource().equals(jrbContratos)){
				loggeador.debug("pulso en jrbContratos");
				jlpFuncionalidades.moveToFront(pc);
			}else if(e.getSource().equals(jrbObservaciones)){
					loggeador.debug("pulso en jrbObservaciones");
					jlpFuncionalidades.moveToFront(po);
				}else if(e.getSource().equals(jrbEstancias)){
					loggeador.debug("pulso en jrbEstancias");
					jlpFuncionalidades.moveToFront(pe);
					}else if(e.getSource().equals(jbAnhadir)){					
							//determinamos si estamos en observaciones o en instancias
							if(jlpFuncionalidades.getPosition(po)==0){
								loggeador.debug("Añadimos una observacion");
								AnhadirObservacion();
							}else if(jlpFuncionalidades.getPosition(pe)==0){
								loggeador.debug("Añadimos una Estancia");
								AnhadirEstancia();						
							}else{
								loggeador.debug("añadir un contrato");
								AnhadirContrato();
							}
						}else if (e.getSource().equals(jbBorrar)){
							if(jlpFuncionalidades.getPosition(po)==0){
								loggeador.debug("Borramos una observacion");
								BorrarObservacion();
							}else{
								loggeador.debug("Borramos una Estancia");
							}
							loggeador.debug("Ha pulsado borrar observación o estancia");
							}else if(e.getSource().equals(jmiInformeCuotas)){
								JDateChooser jd = new JDateChooser();
								String message ="Introduce la fecha del informe:\n";
								Object[] params = {message,jd};
								JOptionPane.showConfirmDialog(null,params,"Día de inicio", JOptionPane.PLAIN_MESSAGE);
								String s="";
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								s=sdf.format(((JDateChooser)params[1]).getDate());//Casting params[1] makes me able to get its information
								Date fecha;
								try {
									fecha = sdf.parse(s);
									FuncionesDiversas.GenerarInformeCuotas(fecha);
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								}else if(e.getSource().equals(jmiInformeDependencias)){
									JDateChooser jd = new JDateChooser();
									String message ="Introduce la fecha del informe:\n";
									Object[] params = {message,jd};
									JOptionPane.showConfirmDialog(null,params,"Día de inicio", JOptionPane.PLAIN_MESSAGE);
									String s="";
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									s=sdf.format(((JDateChooser)params[1]).getDate());//Casting params[1] makes me able to get its information
									Date fecha;
									try {
										fecha = sdf.parse(s);
										FuncionesDiversas.GenerarInformeDependencias(fecha);
									} catch (ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
										}else if(e.getSource().equals(jmiNumClientes)){
													JDateChooser jd = new JDateChooser();
													String message ="Introduce la fecha del informe:\n";
													Object[] params = {message,jd};
													JOptionPane.showConfirmDialog(null,params,"Día del informe", JOptionPane.PLAIN_MESSAGE);
													String s="";
													String sMySQL="";
													SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
													SimpleDateFormat sdfMysql=new SimpleDateFormat("yyyy-MM-dd");
													s=sdf.format(((JDateChooser)params[1]).getDate());//Casting params[1] makes me able to get its information
													sMySQL=sdfMysql.format(((JDateChooser)params[1]).getDate());
													Date fecha;
													try {
														fecha = sdf.parse(s);
														//llamamos al procedure que crea la tabla
														dbConexion con=new dbConexion();
														java.sql.Connection conec=con.getConexion();
														CallableStatement cs=conec.prepareCall("call numClientes(\""+sMySQL+"\");");
														cs.execute();
														FuncionesDiversas.GenerarListadoNumClientes(fecha);
													} catch (ParseException e2) {
														// TODO Auto-generated catch block
														e2.printStackTrace();
													} catch (SQLException e1) {
														// TODO Auto-generated catch block
														e1.printStackTrace();
													}
													
													}else if(e.getSource().equals(jmiListadoInternos)){
														FuncionesDiversas.GenerarListadoInternos();
														}else if(e.getSource().equals(jmiListadoEmpleados)){
															FuncionesDiversas.GenerarListadoEmpleados();
															}else if(e.getSource().equals(jmiListadoTelefonos)){
																FuncionesDiversas.GenerarListadoTelefonos();
																}else if(e.getSource().equals(jmiPatron)){
																	loggeador.debug("abrir formulario Patron");		
																	AltaPatron at=new AltaPatron();
																	at.setVisible(true);
																	}else{
																		String resultado=((JButton)e.getSource()).getText();
																		JTabbedPane jtp=(JTabbedPane)jpTablas.getComponent(0);
																		int indiceTablaSeleccionada=jtp.getSelectedIndex();
																		PanelFiltros pf=(PanelFiltros)jtp.getComponent(indiceTablaSeleccionada);
																		JTable t=pf.getJt();
																		@SuppressWarnings("unused")
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
																	}
	}
					
		
		
	
	private void AnhadirEstancia() {
		// TODO Auto-generated method stub
		DefaultTableModel dtm=(DefaultTableModel)pe.getJtEstancias().getModel();
		AltaEstancia ae=new AltaEstancia(pe.getI(),dtm);
		ae.setVisible(true);	
	}

	private void AnhadirContrato(){
		// TODO Auto-generated method stub
		DefaultTableModel dtm=(DefaultTableModel)pc.getJtContratos().getModel();
		AltaContrato ac=new AltaContrato(pc.getE(),dtm);
		ac.setVisible(true);	
	}

	private void BorrarObservacion() {
		// TODO Auto-generated method stub
		JTable jt=po.getJtObservaciones();
		DefaultTableModel dtm=(DefaultTableModel)jt.getModel();
		
		ObservacionDao od=new ObservacionDao();
		Observacion o=od.getObservacionById(Integer.parseInt(dtm.getValueAt(jt.getSelectedRow(), 0).toString()));
		od.deleteObservacion(o);
		dtm.removeRow(jt.getSelectedRow());
	}

	private void AnhadirObservacion() {
		DefaultTableModel dtm=(DefaultTableModel)po.getJtObservaciones().getModel();
		AltaObservacion ao=new AltaObservacion(po.getI(),dtm);
		ao.setVisible(true);		
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
	
	public PanelEstancias getPe() {
		return pe;
	}

	public void setPe(PanelEstancias pe) {
		this.pe = pe;
	}
	
	public PanelContratos getPc() {
		return pc;
	}

	public void setPc(PanelContratos pc) {
		this.pc = pc;
	}
	
	public PanelJornadas getPj() {
		return pj;
	}

	public void setPj(PanelJornadas pj) {
		this.pj = pj;
	}

	public JRadioButton getJrbObservaciones() {
		return jrbObservaciones;
	}

	public void setJrbObservaciones(JRadioButton jrbObservaciones) {
		this.jrbObservaciones = jrbObservaciones;
	}

	public JRadioButton getJrbEstancias() {
		return jrbEstancias;
	}

	public void setJrbEstancias(JRadioButton jrbEstancias) {
		this.jrbEstancias = jrbEstancias;
	}

	public JRadioButton getJrbContratos() {
		return jrbContratos;
	}

	public void setJrbContratos(JRadioButton jrbContratos) {
		this.jrbContratos = jrbContratos;
	}

	public JRadioButton getJrbJornadas() {
		return jrbJornadas;
	}

	public void setJrbJornadas(JRadioButton jrbJornadas) {
		this.jrbJornadas = jrbJornadas;
	}

	public JPanel getJpTelon() {
		return jpTelon;
	}

	public void setJpTelon(JPanel jpTelon) {
		this.jpTelon = jpTelon;
	}	
	
}

	
	
