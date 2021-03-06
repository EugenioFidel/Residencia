package com.Eu.formularios;

import java.awt.Color;
import java.awt.Font;
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
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.Eu.controladores.FuncionesDiversas;
import com.Eu.controladores.dbConexion;
import com.Eu.paneles.PanelBotoneroEmpleados;
import com.Eu.paneles.PanelBotoneroInternos;
import com.Eu.paneles.PanelBotoneroPersonas;
import com.Eu.paneles.PanelContratos;
import com.Eu.paneles.PanelEstancias;
import com.Eu.paneles.PanelFiltros;
import com.Eu.paneles.PanelJornadas;
import com.Eu.paneles.PanelObservaciones;

import com.toedter.calendar.JDateChooser;

public class FrmPrincipal extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final static Logger loggeador = Logger.getLogger(FrmPrincipal.class);
	// Declaraciones para panel personas
	JTabbedPane tp = new JTabbedPane();
	JPanel jpTablas = new JPanel();
	JLayeredPane jlpFuncionalidades = new JLayeredPane();

	JPanel jpListados = new JPanel();
	PanelBotoneroPersonas jpb = new PanelBotoneroPersonas();

	enum eventos {
		Añadir, Editar, Borrar
	};

	public static int PERSONAS = 0;
	public static int EMPLEADOS = 1;
	public static int INTERNOS = 2;
	PanelFiltros pfTodos = null;
	PanelFiltros pfInternos = null;
	PanelFiltros pfEmpleados = null;
	// un icono para el formulario
	ImageIcon vaca = new ImageIcon("./src/main/resources/cowIcon.jpeg");

	// JMenuBar, barra de menus
	JMenuBar jmbMenu = new JMenuBar();
	JMenu menuArchivo = new JMenu("Archivo");
	JMenu menuEditar = new JMenu("Editar");
	JMenu menuListados = new JMenu("Listados");
	JMenu menuInformes = new JMenu("Informes");
	JMenu menuJornadas = new JMenu("Jornadas");
	JMenuItem jmiPlanillas = new JMenuItem("Abrir planillas");
	JMenuItem jmiListadoPersonas = new JMenuItem("Personas");
	JMenuItem jmiListadoInternos = new JMenuItem("Internos");
	JMenuItem jmiListadoEmpleados = new JMenuItem("Empleados");
	JSeparator jsSeparador1MenuListados = new JSeparator();
	JMenuItem jmiListadoTelefonos = new JMenuItem("Teléfonos");

	JMenuItem jmiInformeCuotas = new JMenuItem("Cuotas");
	JMenuItem jmiInformeDependencias = new JMenuItem("Dependencias");
	JMenuItem jmiNumClientes = new JMenuItem("Internos por día");
	
	JSeparator js=new JSeparator();
	
	JMenuItem jmiInformeCuotasCd=new JMenuItem("Cuotas centro día");
	

	JMenuItem jmiPatron = new JMenuItem("Grabar jornada/s");

	public PanelObservaciones po = null;
	public PanelEstancias pe = null;
	public PanelContratos pc = null;
	public PanelJornadas pj = null;
	public JPanel jpTelon = new JPanel();

	public PanelBotoneroInternos jpBotoneroInternos = new PanelBotoneroInternos();
	public PanelBotoneroEmpleados jpBotoneroEmpleados;

	Font fuente = new Font("Ubuntu", 0, 20);
	Font fuenteN = new Font("Ubuntu", 1, 20);

	
	FuncionesDiversas fd=new FuncionesDiversas();

	public FrmPrincipal() throws SQLException {

		// password para la BD
		String ps = System.getenv().get("PSS_RES");
		fd.setPss(ps);

		jpBotoneroEmpleados = new PanelBotoneroEmpleados(fd.getPss());

		// características del formulario principal

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FrmPrincipal.isDefaultLookAndFeelDecorated();
		this.setTitle("Gestión de residencias 1.0");

		// Declaramos el layout, GridBagLayout
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints col = new GridBagConstraints();
		this.setLayout(gbl);
		this.setIconImage(vaca.getImage());

		tp.setFont(fuente);
		pfTodos = new PanelFiltros(PERSONAS);
		pfInternos = new PanelFiltros(INTERNOS);
		pfEmpleados = new PanelFiltros(EMPLEADOS);
		tp.add("Todos", pfTodos);
		tp.add("Internos", pfInternos);
		tp.add("Empleados", pfEmpleados);

		jpTablas.setLayout(new GridLayout(1, 2));
		jpTablas.add(tp);

		col.gridx = 0;
		col.gridy = 0;
		col.weightx = 1.5;
		col.anchor = GridBagConstraints.NORTHWEST;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(5, 5, 5, 5);
		this.getContentPane().add(jpTablas, col);
		col.anchor = GridBagConstraints.CENTER;
		col.fill = GridBagConstraints.NONE;
		col.weightx = 0;

		col.gridx = 0;
		col.gridy = 1;
		col.anchor = GridBagConstraints.EAST;
		this.getContentPane().add(jpb, col);
		col.anchor = GridBagConstraints.CENTER;

		po = new PanelObservaciones(1000000);
		pe = new PanelEstancias(1000000, fd.getPss());
		pc = new PanelContratos(1000000,fd.getPss());
		pj = new PanelJornadas(1000000);

		jlpFuncionalidades.add(pe, JLayeredPane.DEFAULT_LAYER);
		jlpFuncionalidades.add(po, JLayeredPane.DEFAULT_LAYER);
		jlpFuncionalidades.add(pc, JLayeredPane.DEFAULT_LAYER);
		jlpFuncionalidades.add(pj, JLayeredPane.DEFAULT_LAYER);
		jpTelon.setBackground(Color.WHITE);
		jpTelon.setSize(950, 600);
		jlpFuncionalidades.add(jpTelon, JLayeredPane.DEFAULT_LAYER);

		jlpFuncionalidades.moveToBack(po);
		jlpFuncionalidades.moveToBack(pe);
		jlpFuncionalidades.moveToBack(pc);
		jlpFuncionalidades.moveToBack(pj);
		jlpFuncionalidades.moveToFront(jpTelon);

		col.gridx = 1;
		col.gridy = 0;
		col.gridwidth = 2;
		col.weightx = 1.0;
		col.weighty = 1.0;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(5, 5, 5, 5);
		this.getContentPane().add(jlpFuncionalidades, col);
		col.weightx = 0.0;
		col.weighty = 0.0;
		col.gridwidth = 1;

		col.gridx = 1;
		col.gridy = 1;
		col.insets = new Insets(5, 5, 5, 5);
		this.getContentPane().add(jpBotoneroInternos, col);

		col.gridx = 2;
		col.gridy = 1;
		this.getContentPane().add(jpBotoneroEmpleados, col);

		// =========================================================================================
		// BARRA DE MENU
		// ==========================================================================
		// =========================================================================================
		jmiPlanillas.setFont(fuenteN);
		menuArchivo.add(jmiPlanillas);
		menuArchivo.setFont(fuenteN);
		jmbMenu.add(menuArchivo);
		menuEditar.setFont(fuenteN);
		jmbMenu.add(menuEditar);
		jmiListadoPersonas.setFont(fuenteN);
		jmiListadoInternos.setFont(fuenteN);
		jmiListadoEmpleados.setFont(fuenteN);
		jmiListadoTelefonos.setFont(fuenteN);
		jmiInformeCuotas.setFont(fuenteN);
		jmiInformeDependencias.setFont(fuenteN);
		jmiNumClientes.setFont(fuenteN);
		jmiInformeCuotasCd.setFont(fuenteN);
		jmiPatron.setFont(fuenteN);
		menuListados.add(jmiListadoPersonas);
		menuListados.add(jmiListadoInternos);
		menuListados.add(jmiListadoEmpleados);
		menuListados.add(jsSeparador1MenuListados);
		menuListados.add(jmiListadoTelefonos);
		menuInformes.add(jmiInformeCuotas);
		menuInformes.add(jmiInformeDependencias);
		menuInformes.add(jmiNumClientes);
		menuInformes.add(js);
		menuInformes.add(jmiInformeCuotasCd);
		menuJornadas.add(jmiPatron);
		menuListados.setFont(fuenteN);
		menuInformes.setFont(fuenteN);
		menuJornadas.setFont(fuenteN);
		jmbMenu.add(menuListados);
		jmbMenu.add(menuInformes);
		jmbMenu.add(menuJornadas);

		jmiPlanillas.addActionListener(this);
		jmiListadoPersonas.addActionListener(this);
		jmiListadoInternos.addActionListener(this);
		jmiListadoEmpleados.addActionListener(this);
		jmiListadoTelefonos.addActionListener(this);
		jmiInformeCuotas.addActionListener(this);
		jmiInformeDependencias.addActionListener(this);
		jmiNumClientes.addActionListener(this);
		jmiInformeCuotasCd.addActionListener(this);
		jmiPatron.addActionListener(this);

		this.setJMenuBar(jmbMenu);

		this.pack();
	}

	public void actionPerformed(ActionEvent e) {
		JDateChooser jd = new JDateChooser();
		jd.setFont(fuente);
		Date fecha;
		Calendar c=Calendar.getInstance();
		// comprobamos el objeto que ha producido el evento
		if (e.getSource().equals(jmiPlanillas)) {
			System.out.println("planillas");
			AccPlanillas ap = new AccPlanillas(fd.getPss());
			ap.setVisible(true);
		} else if (e.getSource().equals(jmiInformeCuotas)) {
			String message = "Introduce la fecha del informe:\n";
			Object[] params = { message, jd };
			JOptionPane.showConfirmDialog(null, params, "Día de inicio", JOptionPane.PLAIN_MESSAGE);
			String s = "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			s = sdf.format(((JDateChooser) params[1]).getDate());// Casting params[1] makes me able to get its
																	// information

			try {				
				c.setTime(sdf.parse(s));
				c.add(Calendar.MONTH, 1);
				fecha=c.getTime();
				fd.GenerarInformeCuotas(fecha, fd.getPss());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource().equals(jmiInformeCuotasCd)) {
			String message = "Introduce la fecha del informe:\n";
			Object[] params = { message, jd };
			JOptionPane.showConfirmDialog(null, params, "Día de inicio", JOptionPane.PLAIN_MESSAGE);
			String s = "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			s = sdf.format(((JDateChooser) params[1]).getDate());// Casting params[1] makes me able to get its
																	// information

			try {
				c.setTime(sdf.parse(s));
				c.add(Calendar.MONTH, 1);
				fecha=c.getTime();
				fd.GenerarInformeCuotasCd(fecha, fd.getPss());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource().equals(jmiInformeDependencias)) {

			String message = "Introduce la fecha del informe:\n";
			Object[] params = { message, jd };
			JOptionPane.showConfirmDialog(null, params, "Día de inicio", JOptionPane.PLAIN_MESSAGE);
			String s = "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			s = sdf.format(((JDateChooser) params[1]).getDate());// Casting params[1] makes me able to get its
																	// information

			try {
				fecha = sdf.parse(s);
				fd.GenerarInformeDependencias(fecha, fd.getPss());
			} catch (ParseException e1) {				
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(jmiNumClientes)) {

			String message = "Introduce la fecha del informe:\n";
			Object[] params = { message, jd };
			JOptionPane.showConfirmDialog(null, params, "Día del informe", JOptionPane.PLAIN_MESSAGE);
			String s = "";
			String sMySQL = "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdfMysql = new SimpleDateFormat("yyyy-MM-dd");
			s = sdf.format(((JDateChooser) params[1]).getDate());// Casting params[1] makes me able to get its
																	// information
			sMySQL = sdfMysql.format(((JDateChooser) params[1]).getDate());

			try {
				fecha = sdf.parse(s);
				// llamamos al procedure que crea la tabla
				dbConexion con = new dbConexion(fd.getPss());
				java.sql.Connection conec = con.getConexion();
				CallableStatement cs = conec.prepareCall("call numClientes(\"" + sMySQL + "\");");
				cs.execute();
				fd.GenerarListadoNumClientes(fecha, fd.getPss());
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource().equals(jmiListadoInternos)) {
			fd.GenerarListadoInternos(fd.getPss());
		} else if (e.getSource().equals(jmiListadoEmpleados)) {
			fd.GenerarListadoEmpleados(fd.getPss());
		} else if (e.getSource().equals(jmiListadoTelefonos)) {
			fd.GenerarListadoTelefonos(fd.getPss());
		} else if (e.getSource().equals(jmiPatron)) {
			loggeador.debug("abrir formulario Patron");
			AltaPatron at = new AltaPatron();
			at.setVisible(true);
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

	public JPanel getJpTelon() {
		return jpTelon;
	}

	public void setJpTelon(JPanel jpTelon) {
		this.jpTelon = jpTelon;
	}

	public JTabbedPane getTp() {
		return tp;
	}

	public void setTp(JTabbedPane tp) {
		this.tp = tp;
	}

	public PanelBotoneroInternos getJpBotoneroInternos() {
		return jpBotoneroInternos;
	}

	public void setJpBotoneroInternos(PanelBotoneroInternos jpBotoneroInternos) {
		this.jpBotoneroInternos = jpBotoneroInternos;
	}

	public PanelBotoneroEmpleados getJpBotoneroEmpleados() {
		return jpBotoneroEmpleados;
	}

	public void setJpBotoneroEmpleados(PanelBotoneroEmpleados jpBotoneroEmpleados) {
		this.jpBotoneroEmpleados = jpBotoneroEmpleados;
	}
}