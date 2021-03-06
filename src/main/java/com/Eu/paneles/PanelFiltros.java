package com.Eu.paneles;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.Eu.controladores.FuncionesDiversas;
import com.Eu.controladores.MiRender;
import com.Eu.dao.EmpleadoDao;
import com.Eu.dao.InternoDao;
import com.Eu.dao.PersonaDao;
import com.Eu.formularios.FrmPrincipal;
import com.Eu.model.Empleado;
import com.Eu.model.Interno;
import com.Eu.model.Persona;

public class PanelFiltros extends JPanel implements TableModelListener, ActionListener{ 
	
	private static final long serialVersionUID = 1L;
	final static Logger loggeador = Logger.getLogger(PanelFiltros.class);
	private final int PERSONAS=9;
	private final int INTERNOS=13;
	private final int ALTOFILA=20;
	
	private JTable jt= new JTable();
	//Un JScrollPane para alojar la tabla 
	private JScrollPane jsp = new JScrollPane();
	private JPanel jpRb=new JPanel();
	private JRadioButton jrbTodos;
	private JRadioButton jrbAltas;
	private JRadioButton jrbBajas;
	private int tipoPanel;	
	
	Font fuente=new Font("Verdana",Font.PLAIN,16);
	Font fuenteN=new Font("Verdana",Font.BOLD,16);

	public PanelFiltros(int tipo){		
		setLayout(new BorderLayout());	
		jrbTodos=new JRadioButton("Todos");
		jrbTodos.setFont(fuenteN);
		jrbAltas=new JRadioButton("Altas");
		jrbAltas.setFont(fuenteN);
		jrbBajas=new JRadioButton("Bajas");
		jrbBajas.setFont(fuenteN);
		jrbTodos.addActionListener(this);
		jrbAltas.addActionListener(this);
		jrbBajas.addActionListener(this);
		ButtonGroup bg=new ButtonGroup();
		bg.add(jrbTodos);
		bg.add(jrbAltas);
		bg.add(jrbBajas);
		jrbTodos.setSelected(true);
		jpRb.add(jrbTodos);
		jpRb.add(jrbAltas);
		jpRb.add(jrbBajas);
		
		
		switch (tipo){
			case 0:
				RellenarTablaPersonas();	
				break;
			case 1:
				RellenarTablaEmpleados();
				break;
			case 2:
				RellenarTablaInternos();
				break;
			case 3:
				RellenarTablaPersonasParaEmpleados();
		}			
		AsignarEventosEnTablas();		
	}

	private void AsignarEventosEnTablas() {
		final ListSelectionModel m_modelSelection = jt.getSelectionModel();
		m_modelSelection.addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent e){
				int numColumnas=jt.getColumnCount();
				//Comprobamos Si tenemos seleccionado observaciones o estancias
				JRootPane jrp=jt.getRootPane();
				FrmPrincipal f=(FrmPrincipal) jrp.getParent();
								
				switch(numColumnas){
					//panel personas
					case PERSONAS:
						loggeador.info("evento en panel personas");	
						JPanel jpTelon=f.getJpTelon();
						f.getJlpFuncionalidades().moveToFront(jpTelon);
						break;
					case INTERNOS:
						loggeador.info("evento en panel internos");
						ActualizarTablaObservaciones(jt);
						ActualizarTablaEstancias(jt);
						f.jpBotoneroInternos.setVisible(true);
						JRadioButton jrbObservaciones=f.getJpBotoneroInternos().getjrbObservaciones();
						PanelObservaciones po=f.getPo();
						PanelEstancias pe=f.getPe();
						if(jrbObservaciones.isSelected()){
							f.getJlpFuncionalidades().moveToBack(pe);
							f.getJlpFuncionalidades().moveToFront(po);
						}else{
							f.getJlpFuncionalidades().moveToBack(po);
							f.getJlpFuncionalidades().moveToFront(pe);
						}						
						break;
					default:
						loggeador.debug("evento en panel empleados");
						ActualizarTablaContratos(jt);
						ActualizarTablaJornadas(jt);
						JRadioButton jrbContratos=f.getJpBotoneroEmpleados().getjrbContratos();
						PanelContratos pc=f.getPc();
						PanelJornadas pj=f.getPj();
						if(jrbContratos.isSelected()){
							f.getJlpFuncionalidades().moveToFront(pc);
							f.getJlpFuncionalidades().moveToBack(pj);
						}else {
							f.getJlpFuncionalidades().moveToFront(pj);
							f.getJlpFuncionalidades().moveToBack(pc);
						}					
				}
				}
			});		
	}

	protected void ActualizarTablaObservaciones(JTable tabla) {
		JRootPane jrp=tabla.getRootPane();
		FrmPrincipal f=(FrmPrincipal) jrp.getParent();
		String dni=ObtenerDniEvento(tabla,1);
		InternoDao id=new InternoDao();
		List<Object>lista=id.listaInternosPorDni(dni);
		Interno i=(Interno)lista.get(0);
		PanelObservaciones p=f.getPo();
		p.setI(i);
		p.RellenarTablaObservaciones(i.getIdpersona());
		f.setPo(p);
	}	

	protected void ActualizarTablaEstancias(JTable tabla) {
		JRootPane jrp=tabla.getRootPane();
		FrmPrincipal f=(FrmPrincipal) jrp.getParent();
		String dni=ObtenerDniEvento(tabla,1);
		InternoDao id=new InternoDao();
		//obtenemos el interno que tiene el dni obtenido
		List<Object>lista=id.listaInternosPorDni(dni);
		Interno i=(Interno)lista.get(0);
		
		PanelEstancias p=f.getPe();
		p.setI(i);
		p.RellenarTablaEstancias(i.getIdpersona());
		f.setPe(p);
	}
	
	private void ActualizarTablaContratos(JTable tabla) {
		JRootPane jrp=tabla.getRootPane();
		FrmPrincipal f=(FrmPrincipal) jrp.getParent();
		String dni=ObtenerDniEvento(tabla,2);
		EmpleadoDao ed=new EmpleadoDao();
		//obtenemos el interno que tiene el dni obtenido
		List<Empleado>lista=ed.listaEmpleadoPorDni(dni);
		Empleado e=lista.get(0);
		
		PanelContratos p=f.getPc();
		p.setE(e);
		p.RellenarTablaContratos(e.getIdpersona());
		f.setPc(p);
	}
	
	private void ActualizarTablaJornadas(JTable tabla) {
		JRootPane jrp=tabla.getRootPane();
		FrmPrincipal f=(FrmPrincipal) jrp.getParent();
		String dni=ObtenerDniEvento(tabla,2);
		EmpleadoDao ed=new EmpleadoDao();
		//obtenemos el interno que tiene el dni obtenido
		List<Empleado>lista=ed.listaEmpleadoPorDni(dni);
		Empleado e=lista.get(0);
				
		PanelJornadas pj=f.getPj();
		pj.setE(e);
		pj.rellenarJornadas();
		f.setPj(pj);
	}	

	private void RellenarTablaEmpleados() {
		//decimos que el panel es de Empleados
		setTipoPanel(2);
										
		//renders
		MiRender miRender=new MiRender();		
				
		//el array con las cabeceras de la tabla
		String[]cabecerasTablaEmpleados=new String[11];
		cabecerasTablaEmpleados[0]="D.N.I/N.I.E";
		cabecerasTablaEmpleados[1]="Apellidos y nombre";
		cabecerasTablaEmpleados[2]="Dirección";
		cabecerasTablaEmpleados[3]="Localidad";
		cabecerasTablaEmpleados[4]="Municipio";
		cabecerasTablaEmpleados[5]="C.P.";		
		cabecerasTablaEmpleados[6]="Provincia";
		cabecerasTablaEmpleados[7]="Teléfono";
		cabecerasTablaEmpleados[8]="e-mail";
		cabecerasTablaEmpleados[9]="IBAN";
		cabecerasTablaEmpleados[10]="Número afiliac.";
				
		EmpleadoDao em=new EmpleadoDao();
		List<Empleado> empleados=null;
		if(jrbTodos.isSelected()){
			empleados = em.listaEmpleados();	
		}else if(jrbAltas.isSelected()){
			empleados = em.listaEmpleadosAlta();
		}else{
			empleados = em.listaEmpleadosBaja();
		}		
		
		jt=FuncionesDiversas.cargaDatosEnTablaEmpleados(empleados, cabecerasTablaEmpleados);
		jt.setFont(fuente);
		jt.setRowHeight(ALTOFILA);
		TableColumnModel conjuntoColumnas=jt.getColumnModel();
		for (int i=0;i<conjuntoColumnas.getColumnCount();i++){
			conjuntoColumnas.getColumn(i).setCellRenderer(miRender);
		}
				
		TableColumn columna=jt.getColumn("D.N.I/N.I.E");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("Apellidos y nombre");
		columna.setPreferredWidth(300);
		columna=jt.getColumn("Dirección");			
		columna.setPreferredWidth(300);
		columna=jt.getColumn("Localidad");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("Municipio");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("C.P.");
		columna.setPreferredWidth(70);
		columna=jt.getColumn("Teléfono");
		columna.setPreferredWidth(200);
		columna=jt.getColumn("e-mail");
		columna.setPreferredWidth(200);
		columna=jt.getColumn("IBAN");
		columna.setPreferredWidth(200);
		columna=jt.getColumn("Número afiliac.");
		columna.setPreferredWidth(200);
		
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.doLayout();
		jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setJt(jt);
				
		TableModel dtm=jt.getModel();
		dtm.addTableModelListener(this);
				
		jsp.setViewportView(jt);
				
		this.add(jsp,BorderLayout.CENTER);
		this.add(jpRb, BorderLayout.SOUTH);		
	}

	private void RellenarTablaInternos() {
		//decimos que el panel es de Personas
		setTipoPanel(1);
				
		//renders
		MiRender miRender=new MiRender();		
				
		//el array con las cabeceras de la tabla
		String[]cabecerasTablaInternos=new String[13];
		cabecerasTablaInternos[0]="D.N.I/N.I.E";
		cabecerasTablaInternos[1]="Apellidos y nombre";
		cabecerasTablaInternos[2]="Dirección";
		cabecerasTablaInternos[3]="Localidad";
		cabecerasTablaInternos[4]="Municipio";
		cabecerasTablaInternos[5]="C.P.";		
		cabecerasTablaInternos[6]="Provincia";
		cabecerasTablaInternos[7]="Teléfono";
		cabecerasTablaInternos[8]="e-mail";
		cabecerasTablaInternos[9]="IBAN";
		cabecerasTablaInternos[10]="Número afiliac.";
		cabecerasTablaInternos[11]="Hab.";
		cabecerasTablaInternos[12]="Persona responsable";
				
		InternoDao in=new InternoDao();	
		List<Interno> internos=null;	
		
		if(jrbTodos.isSelected()){
			internos = in.listaInternos();	
		}else if(jrbAltas.isSelected()){
			internos = in.listaInternosAlta();
		}else{
			internos = in.listaInternosBaja();
		}
		
		jt=FuncionesDiversas.cargaDatosEnTablaInternos(internos, cabecerasTablaInternos);		
		jt.setFont(fuente);
		
		TableColumnModel conjuntoColumnas=jt.getColumnModel();
		for (int i=0;i<conjuntoColumnas.getColumnCount();i++){
			conjuntoColumnas.getColumn(i).setCellRenderer(miRender);
		}
				
		TableColumn columna=jt.getColumn("D.N.I/N.I.E");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("Apellidos y nombre");
		columna.setPreferredWidth(300);
		columna=jt.getColumn("Dirección");			
		columna.setPreferredWidth(300);
		columna=jt.getColumn("Localidad");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("Municipio");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("C.P.");
		columna.setPreferredWidth(100);
		columna=jt.getColumn("Teléfono");
		columna.setPreferredWidth(200);
		columna=jt.getColumn("e-mail");
		columna.setPreferredWidth(200);
		columna=jt.getColumn("IBAN");
		columna.setPreferredWidth(200);
		columna=jt.getColumn("Número afiliac.");
		columna.setPreferredWidth(200);
		columna=jt.getColumn("Hab.");
		columna.setPreferredWidth(40);
		columna=jt.getColumn("Persona responsable");
		columna.setPreferredWidth(300);				
		
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.doLayout();
		jt.setRowHeight(ALTOFILA);
		jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setJt(jt);
				
		TableModel dtm=jt.getModel();
		dtm.addTableModelListener(this);
				
		jsp.setViewportView(jt);
				
		this.add(jsp,BorderLayout.CENTER);
		this.add(jpRb, BorderLayout.SOUTH);
	}

	private void RellenarTablaPersonas() {
		//decimos que el panel es de Personas
		setTipoPanel(0);
	
		//renders
		MiRender miRender=new MiRender();		
		
		//el array con las cabeceras de la tabla
		String[]cabecerasTablaPersonas=new String[9];
		cabecerasTablaPersonas[0]="D.N.I/N.I.E";
		cabecerasTablaPersonas[1]="Apellidos y nombre";
		cabecerasTablaPersonas[2]="Dirección";
		cabecerasTablaPersonas[3]="Localidad";
		cabecerasTablaPersonas[4]="Municipio";
		cabecerasTablaPersonas[5]="C.P.";		
		cabecerasTablaPersonas[6]="Provincia";
		cabecerasTablaPersonas[7]="Teléfono";
		cabecerasTablaPersonas[8]="e-mail";
		
		PersonaDao pd=new PersonaDao();		
		List<Object> personas = pd.listaPersonas();			
		jt=FuncionesDiversas.cargaDatosEnTablaPersonas(personas, cabecerasTablaPersonas);		
		jt.setFont(fuente);
		jt.setRowHeight(ALTOFILA);
		
		jrbTodos.setFont(fuenteN);
		TableColumnModel conjuntoColumnas=jt.getColumnModel();
		for (int i=0;i<conjuntoColumnas.getColumnCount();i++){
			conjuntoColumnas.getColumn(i).setCellRenderer(miRender);
		}
		
		TableColumn columna=jt.getColumn("D.N.I/N.I.E");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("Apellidos y nombre");
		columna.setPreferredWidth(300);
		columna=jt.getColumn("Dirección");			
		columna.setPreferredWidth(300);
		columna=jt.getColumn("Localidad");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("Municipio");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("C.P.");
		columna.setPreferredWidth(100);
		columna=jt.getColumn("Teléfono");
		columna.setPreferredWidth(200);
		columna=jt.getColumn("e-mail");
		columna.setPreferredWidth(250);
		
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.doLayout();
		jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setJt(jt);
		
		TableModel dtm=jt.getModel();
		dtm.addTableModelListener(this);
		
		jsp.setViewportView(jt);
		
		this.add(jsp,BorderLayout.CENTER);	
	}
	
	private void RellenarTablaPersonasParaEmpleados() {		
		
		//Un JScrollPane para alojar la tabla 
		JScrollPane jsp = new JScrollPane();
		
		//renders
		MiRender miRender=new MiRender();		
		
		//el array con las cabeceras de la tabla
		String[]cabecerasTablaPersonas=new String[9];
		cabecerasTablaPersonas[0]="D.N.I/N.I.E";
		cabecerasTablaPersonas[1]="Apellidos y nombre";
		cabecerasTablaPersonas[2]="Dirección";
		cabecerasTablaPersonas[3]="Localidad";
		cabecerasTablaPersonas[4]="Municipio";
		cabecerasTablaPersonas[5]="C.P.";		
		cabecerasTablaPersonas[6]="Provincia";
		cabecerasTablaPersonas[7]="Teléfono";
		cabecerasTablaPersonas[8]="e-mail";
		
		PersonaDao pd=new PersonaDao();		
		List<Object> personas = pd.listaPersonasSinEmpleados();
		jt=FuncionesDiversas.cargaDatosEnTablaPersonas(personas, cabecerasTablaPersonas);		
		jt.setRowHeight(ALTOFILA);
		
		TableColumnModel conjuntoColumnas=jt.getColumnModel();
		for (int i=0;i<conjuntoColumnas.getColumnCount();i++){
			conjuntoColumnas.getColumn(i).setCellRenderer(miRender);
		}		

		TableColumn columna=jt.getColumn("D.N.I/N.I.E");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("Apellidos y nombre");
		columna.setPreferredWidth(300);
		columna=jt.getColumn("Dirección");			
		columna.setPreferredWidth(300);
		columna=jt.getColumn("Localidad");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("Municipio");
		columna.setPreferredWidth(150);
		columna=jt.getColumn("C.P.");
		columna.setPreferredWidth(100);
		columna=jt.getColumn("Teléfono");
		columna.setPreferredWidth(200);
		columna=jt.getColumn("e-mail");
		columna.setPreferredWidth(200);
		
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.doLayout();
		jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setJt(jt);
		
		TableModel dtm=jt.getModel();
		dtm.addTableModelListener(this);
		
		jsp.setViewportView(jt);
		
		this.add(jsp,BorderLayout.CENTER);		
	}	
	public void actionPerformed(ActionEvent e) {
		int tipoPanel=this.getTipoPanel();
		switch (tipoPanel){		
		//evento en panel internos
		case 1:
			loggeador.info("Evento en tabla internos");
			System.out.println("internos");
			RellenarTablaInternos();
			break;
		//evento en el panel de empleados
		case 2:
			loggeador.info("Evento en tabla empleados");
			System.out.println("empleados");
			RellenarTablaEmpleados();
			break;
		default:
			loggeador.debug("Error en la selección de panel");
		}	
		AsignarEventosEnTablas();
		
	}
	
	public void tableChanged(TableModelEvent ev) {
		int tipoPanel=this.getTipoPanel();
		switch (tipoPanel){
		//evento en panel personas
		case 0:
			loggeador.info("Edición en tabla personas");
			//comprobamos que tipo de evento es
			if(ev.getType()==TableModelEvent.UPDATE){
				ActualizarTablaPersona(ev);				
			}
			break;
		//evento en panel internos
		case 1:
			loggeador.info("Edición en tabla internos");
			if(ev.getType()==TableModelEvent.UPDATE){
				ActualizarTablaInternos(ev);				
			}
			break;
		//evento en el panel de empleados
		case 2:
			loggeador.info("Edición en tabla empleados");
			if(ev.getType()==TableModelEvent.UPDATE){
				ActualizarTablaEmpleados(ev);				
			}
			break;
		default:
			loggeador.debug("Error en la selección de panel");
		}			
	}

	private void ActualizarTablaPersona(TableModelEvent ev) {
		Persona p=new Persona();
		DefaultTableModel modelo=(DefaultTableModel) ev.getSource();
		@SuppressWarnings("unchecked")
		Vector<Object> v=(Vector<Object>) modelo.getDataVector().elementAt(ev.getFirstRow());
		String[]identificacion=v.elementAt(0).toString().split("-");
		p.setLetraCif(identificacion[0].charAt(0));
		p.setDni(identificacion[1]);
		p.setLetraNif(identificacion[2].charAt(0));
		String[]apeNom=v.elementAt(1).toString().split(",");	
		//en la primera posición del array tenemos los apellidos que puede ser desde una palabra (primer apellido)
		//hasta más de uno en el caso de dos apellidos o apellidos compuestos
		StringTokenizer st=new StringTokenizer(apeNom[0], " ");
		switch (st.countTokens()){
			case 1:
				//una sola palabra, tan sólo un apellido
				p.setPrimerApe(st.nextToken().trim().toUpperCase());
				break;
			case 2:
				//lo normal, dos apellidos
				p.setPrimerApe(st.nextToken().trim().toUpperCase());
				p.setSegundoApe(st.nextToken().trim().toUpperCase());
				break;
			default:
				//metemos las mitad de las palabras en el primer apellido y la otra mitad en el segundo
				String primero="";
				String segundo="";
				int i=0;
				while(i<=st.countTokens()/2){
					primero=primero+st.nextToken()+" ";
					i++;
				}
				primero=primero.trim().toUpperCase();
				while(st.hasMoreTokens())
					segundo=segundo+st.nextToken()+" ";					
				segundo=segundo.trim().toUpperCase();
				p.setPrimerApe(primero);
				p.setSegundoApe(segundo);			
		}
		p.setNombre(apeNom[1].trim().toUpperCase());
		p.setDireccion(v.elementAt(2).toString().trim().toUpperCase());
		p.setLocalidad(v.elementAt(3).toString().trim().toUpperCase());
		p.setMunicipio(v.elementAt(4).toString().trim().toUpperCase());
		p.setCp(v.elementAt(5).toString().trim().toUpperCase());
		p.setProvincia(v.elementAt(6).toString().trim().toUpperCase());
		String[]telefonos=v.elementAt(7).toString().split("/");
		p.setTelefono1(telefonos[0].trim().toUpperCase());
		p.setTelefono2(telefonos[1].trim().toUpperCase());
		p.setEmail(v.elementAt(8).toString().trim().toUpperCase());
		
		PersonaDao pd=new PersonaDao();
		pd.updatePersona(p);
	}

	private void ActualizarTablaEmpleados(TableModelEvent ev) {
		Empleado e=new Empleado();
		DefaultTableModel modelo=(DefaultTableModel) ev.getSource();
		@SuppressWarnings("unchecked")
		Vector<Object> v=(Vector<Object>) modelo.getDataVector().elementAt(ev.getFirstRow());
		String[]identificacion=v.elementAt(0).toString().split("-");
		e.setLetraCif(identificacion[0].charAt(0));
		e.setDni(identificacion[1]);
		e.setLetraNif(identificacion[2].charAt(0));
		String[]apeNom=v.elementAt(1).toString().split(",");	
		//en la primera posición del array tenemos los apellidos que puede ser desde una palabra (primer apellido)
		//hasta más de uno en el caso de dos apellidos o apellidos compuestos
		StringTokenizer st=new StringTokenizer(apeNom[0], " ");
		switch (st.countTokens()){
			case 1:
				//una sola palabra, tan sólo un apellido
				e.setPrimerApe(st.nextToken().trim().toUpperCase());
				break;
			case 2:
				//lo normal, dos apellidos
				e.setPrimerApe(st.nextToken().trim().toUpperCase());
				e.setSegundoApe(st.nextToken().trim().toUpperCase());
				break;
			default:
				//metemos las mitad de las palabras en el primer apellido y la otra mitad en el segundo
				String primero="";
				String segundo="";
				int i=0;
				while(i<=st.countTokens()/2){
					primero=primero+st.nextToken()+" ";
					i++;
				}
				primero=primero.trim().toUpperCase();
				while(st.hasMoreTokens())
					segundo=segundo+st.nextToken()+" ";					
				segundo=segundo.trim().toUpperCase();
				e.setPrimerApe(primero);
				e.setSegundoApe(segundo);					
		}
		e.setNombre(apeNom[1].trim().toUpperCase());
		e.setDireccion(v.elementAt(2).toString().trim().toUpperCase());
		e.setLocalidad(v.elementAt(3).toString().trim().toUpperCase());
		e.setMunicipio(v.elementAt(4).toString().trim().toUpperCase());
		e.setCp(v.elementAt(5).toString().trim().toUpperCase());
		e.setProvincia(v.elementAt(6).toString().trim().toUpperCase());
		String[]telefonos=v.elementAt(7).toString().split("/");
		e.setTelefono1(telefonos[0].trim().toUpperCase());
		e.setTelefono2(telefonos[1].trim().toUpperCase());
		e.setEmail(v.elementAt(8).toString().trim().toUpperCase());
		e.setCc(v.elementAt(9).toString().trim().toUpperCase());	
		e.setSs(v.elementAt(10).toString().trim().toUpperCase());
		
		EmpleadoDao ed=new EmpleadoDao();
		ed.update(e);		
	}

	private void ActualizarTablaInternos(TableModelEvent ev) {
		Interno i=new Interno();
		DefaultTableModel modelo=(DefaultTableModel) ev.getSource();
		@SuppressWarnings("unchecked")
		Vector<Object> v=(Vector<Object>) modelo.getDataVector().elementAt(ev.getFirstRow());
		String[]identificacion=v.elementAt(0).toString().split("-");
		i.setLetraCif(identificacion[0].charAt(0));
		i.setDni(identificacion[1]);
		i.setLetraNif(identificacion[2].charAt(0));
		String[]apeNom=v.elementAt(1).toString().split(",");	
		//en la primera posición del array tenemos los apellidos que puede ser desde una palabra (primer apellido)
		//hasta más de uno en el caso de dos apellidos o apellidos compuestos
		StringTokenizer st=new StringTokenizer(apeNom[0], " ");
		switch (st.countTokens()){
			case 1:
				//una sola palabra, tan sólo un apellido
				i.setPrimerApe(st.nextToken().trim().toUpperCase());
				break;
			case 2:
				//lo normal, dos apellidos
				i.setPrimerApe(st.nextToken().trim().toUpperCase());
				i.setSegundoApe(st.nextToken().trim().toUpperCase());
				break;
			default:
				//metemos las mitad de las palabras en el primer apellido y la otra mitad en el segundo
				String primero="";
				String segundo="";
				int it=0;
				while(it<=st.countTokens()/2){
					primero=primero+st.nextToken()+" ";
					it++;
				}
				primero=primero.trim().toUpperCase();
				while(st.hasMoreTokens())
					segundo=segundo+st.nextToken()+" ";					
				segundo=segundo.trim().toUpperCase();
				i.setPrimerApe(primero);
				i.setSegundoApe(segundo);					
		}
		i.setNombre(apeNom[1].trim().toUpperCase());
		i.setDireccion(v.elementAt(2).toString().trim().toUpperCase());
		i.setLocalidad(v.elementAt(3).toString().trim().toUpperCase());
		i.setMunicipio(v.elementAt(4).toString().trim().toUpperCase());
		i.setCp(v.elementAt(5).toString().trim().toUpperCase());
		i.setProvincia(v.elementAt(6).toString().trim().toUpperCase());
		String[]telefonos=v.elementAt(7).toString().split("/");
		i.setTelefono1(telefonos[0].trim().toUpperCase());
		i.setTelefono2(telefonos[1].trim().toUpperCase());
		i.setEmail(v.elementAt(8).toString().trim().toUpperCase());
		i.setCc(v.elementAt(9).toString().trim().toUpperCase());	
		i.setSs(v.elementAt(10).toString().trim().toUpperCase());
		String numero =v.elementAt(11).toString().trim().toUpperCase();
		int numeroNum=Integer.parseInt(numero);
		i.setHabitacion(numeroNum);
				
		InternoDao id=new InternoDao();
		id.update(i);		
	}
	
	private String ObtenerDniEvento(JTable tabla, int tablaOrigen) {
		JRootPane jrp=tabla.getRootPane();
		Container c=jrp.getContentPane();
		Component[] componentes=c.getComponents();
		JPanel jp=(JPanel) componentes[0];
		JTabbedPane jtp=(JTabbedPane) jp.getComponent(0);
		PanelFiltros pf=(PanelFiltros) jtp.getComponent(tablaOrigen);
		JTable jt=pf.getJt();
		DefaultTableModel dtm=(DefaultTableModel)jt.getModel();
		String[]identificacion=dtm.getValueAt(jt.getSelectedRow(), 0).toString().split("-");
		String dni=identificacion[1];
		return dni;
	}
		
	public JTable getJt() {
		return jt;
	}

	public void setJt(JTable jt) {
		this.jt = jt;
	}

	public int getTipoPanel() {
		return tipoPanel;
	}

	public void setTipoPanel(int tipoPanel) {
		this.tipoPanel = tipoPanel;
	}
	
	public JScrollPane getJsp() {
		return jsp;
	}

	public void setJsp(JScrollPane jsp) {
		this.jsp = jsp;
	}
	public JPanel getJpRb() {
		return jpRb;
	}

	public void setJpRb(JPanel jpRb) {
		this.jpRb = jpRb;
	}

	
}