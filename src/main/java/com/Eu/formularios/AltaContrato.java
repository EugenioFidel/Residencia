package com.Eu.formularios;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.Eu.controladores.FuncionesDiversas;
import com.Eu.dao.CategoriaDao;
import com.Eu.dao.ContratoDao;
import com.Eu.dao.EmpleadoDao;
import com.Eu.dao.Empleado_contratoDao;
import com.Eu.dao.Tipo_contratoDao;
import com.Eu.model.Categoria;
import com.Eu.model.Contrato;
import com.Eu.model.Empleado;
import com.Eu.model.Empleado_contrato;
import com.Eu.model.Tipo_contrato;
import com.toedter.calendar.JDateChooser;

public class AltaContrato extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -540476757440700222L;

	final static Logger loggeador = Logger.getLogger(AltaContrato.class);

	SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

	JDateChooser jdcFecha = new JDateChooser();

	JLabel jlNombreEdit = new JLabel();
	JLabel jlFechaInicio = new JLabel("Fecha de contratación: ");
	JDateChooser jdcFechaInicio = new JDateChooser();
	JLabel jlHoras = new JLabel("Horas/semana: ");
	JComboBox<Integer> jcbHoras = new JComboBox<Integer>();
	JLabel jlTipoContrato = new JLabel("Tipo de contrato: ");
	JTextField jtTipoContrato = new JTextField();
	JLabel jlCategoria = new JLabel("Categoría profesional: ");
	JTextField jtCategoria = new JTextField();
	JLabel jlEmpleadoSustituido = new JLabel("Empleado sustituido: ");
	JComboBox<String[]> jcbEmpleados = new JComboBox<String[]>();

	JButton jbCancelar = new JButton("Cancelar");
	JButton jbGrabar = new JButton("Grabar");

	Font fuente = new Font("Verdana", 0, 20);
	Font fuenteN = new Font("Verdana", 1, 20);

	Empleado en;
	DefaultTableModel dtm;

	public AltaContrato(Empleado e, DefaultTableModel dtm, String pss) {

		this.setEn(e);
		this.setDtm(dtm);

		// propiedades del formulario
		setModal(true);
		setBounds(300, 300, 700, 400);
		isDefaultLookAndFeelDecorated();
		setTitle("Residenciator - Alta de contrato");

		jdcFecha.setFont(fuenteN);

		// Declaramos el layout, GridBagLayout
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints col = new GridBagConstraints();
		setLayout(gbl);

		CargarCombos();

		// obtenemos el nombre y apellidos del fulano
		jlNombreEdit.setText(
				this.getEn().getNombre() + " " + this.getEn().getPrimerApe() + " " + this.getEn().getSegundoApe());
		jlNombreEdit.setFont(fuenteN);
		col.gridx = 0;
		col.gridy = 0;
		col.gridwidth = 2;
		col.weightx = 1;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(10, 10, 10, 10);
		this.getContentPane().add(jlNombreEdit, col);
		col.gridwidth = 1;

		col.gridx = 0;
		col.gridy = 1;
		col.anchor = GridBagConstraints.EAST;
		jlFechaInicio.setFont(fuenteN);
		this.getContentPane().add(jlFechaInicio, col);
		col.anchor = GridBagConstraints.CENTER;

		// colocamos los controles
		col.gridx = 1;
		col.gridy = 1;
		jdcFecha.setFont(fuenteN);
		jdcFecha.setToolTipText("Introducir fecha de alta");
		this.getContentPane().add(jdcFechaInicio, col);

		col.gridx = 0;
		col.gridy = 2;
		col.anchor = GridBagConstraints.EAST;
		jlHoras.setFont(fuenteN);
		this.getContentPane().add(jlHoras, col);
		col.anchor = GridBagConstraints.CENTER;

		col.gridx = 1;
		col.gridy = 2;
		jdcFecha.setFont(fuenteN);
		jdcFecha.setToolTipText("Introducir horas por semana");
		this.getContentPane().add(jcbHoras, col);

		col.gridx = 0;
		col.gridy = 3;
		col.anchor = GridBagConstraints.EAST;
		jlTipoContrato.setFont(fuenteN);
		this.getContentPane().add(jlTipoContrato, col);
		col.anchor = GridBagConstraints.CENTER;

		col.gridx = 1;
		col.gridy = 3;
		jtTipoContrato.setFont(fuenteN);
		this.getContentPane().add(jtTipoContrato, col);

		col.gridx = 0;
		col.gridy = 4;
		col.anchor = GridBagConstraints.EAST;
		jlCategoria.setFont(fuenteN);
		this.getContentPane().add(jlCategoria, col);
		col.anchor = GridBagConstraints.CENTER;

		col.gridx = 1;
		col.gridy = 4;
		jtCategoria.setFont(fuenteN);
		this.getContentPane().add(jtCategoria, col);

		col.gridx = 0;
		col.gridy = 5;
		col.anchor = GridBagConstraints.EAST;
		jlEmpleadoSustituido.setFont(fuenteN);
		this.getContentPane().add(jlEmpleadoSustituido, col);
		col.anchor = GridBagConstraints.CENTER;

		col.gridx = 1;
		col.gridy = 5;
		jcbEmpleados.setFont(fuenteN);
		this.getContentPane().add(jcbEmpleados, col);

		// jbCancelar, configuraci�n y colocaci�n
		jbCancelar.addActionListener(this);
		jbCancelar.setFont(fuenteN);
		col.gridx = 0;
		col.gridy = 6;
		this.getContentPane().add(jbCancelar, col);

		// jbAceptar, configuraci�n y colocaci�n
		jbGrabar.addActionListener(this);
		jbGrabar.setFont(fuenteN);
		col.gridx = 1;
		col.gridy = 6;
		this.getContentPane().add(jbGrabar, col);

		Component[] c = this.getContentPane().getComponents();
		for (Component comp : c) {
			if (comp instanceof JLabel || comp instanceof JTextField || comp instanceof JButton
					|| comp instanceof JComboBox) {
				comp.setFont(fuenteN);
			}
		}

	}

	private void CargarCombos() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= 40; i++) {
			jcbHoras.addItem(i);
		}
		jcbHoras.setAutoscrolls(true);

		EmpleadoDao ed = new EmpleadoDao();
		List<Empleado> lista = ed.listaEmpleados();

		@SuppressWarnings("rawtypes")
		Iterator it = lista.iterator();
		String columnas[] = new String[2];
		jcbEmpleados.addItem(columnas);
		while (it.hasNext()) {
			Empleado e = (Empleado) it.next();
			String colums[] = new String[2];
			colums[0] = Integer.toString(e.getIdpersona());
			colums[1] = e.getPrimerApe() + " " + e.getSegundoApe() + ", " + e.getNombre();
			jcbEmpleados.addItem(colums);
		}

		jcbEmpleados.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = -3693036053851167905L;

			public java.awt.Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList l, Object o,
					int i, boolean s, boolean f) {
				return new JLabel(((String[]) o)[1]);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(jbGrabar)) {
			String f = dt.format(jdcFechaInicio.getDate());
			// Comprobamos el formato de la fecha
			if (!FuncionesDiversas.ComprobarFormFecha(f)) {

			}
			loggeador.debug("Grabar contrato");
			int idContrato = GrabarContrato(en);
			String[] emp = (String[]) jcbEmpleados.getSelectedItem();

			Object[] fila = { idContrato, f, null, jcbHoras.getSelectedItem(), jtTipoContrato.getText(),
					jtCategoria.getText(), null, emp[1] };
			dtm.addRow(fila);
			this.dispose();
		} else {
			this.dispose();
		}
	}

	private int GrabarContrato(Empleado e) {
		Contrato con = new Contrato();
		con.setFechaInicio(jdcFechaInicio.getDate());
		con.setHoras((Integer) jcbHoras.getSelectedItem());
		Tipo_contrato tc = new Tipo_contrato();
		tc.setTipoContrato(jtTipoContrato.getText());
		Tipo_contratoDao tcd = new Tipo_contratoDao();
		tcd.addTipo_contrato(tc);
		con.setTipoContrato(tc);
		Categoria c = new Categoria();
		c.setCategoria(jtCategoria.getText());
		CategoriaDao cad = new CategoriaDao();
		cad.addCategoria(c);
		con.setCategoria(c);
		ContratoDao cd = new ContratoDao();
		cd.addContrato(con);
		Empleado_contrato ec = new Empleado_contrato();
		ec.setIdEmpleado(e.getIdpersona());
		ec.setIdContrato(con.getIdContrato());
		String[] sustituido = new String[2];
		sustituido = (String[]) jcbEmpleados.getSelectedItem();
		if (sustituido[0] == null) {
			ec.setIdSustituido(0);
		} else {
			ec.setIdSustituido(Integer.parseInt(sustituido[0]));
		}
		Empleado_contratoDao ecd = new Empleado_contratoDao();
		try {
			ecd.addEmpleado_contrato(ec);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			loggeador.error("Error en la introducción del empleado \n" + e1);
		}

		return con.getIdContrato();
	}

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}

	public Empleado getEn() {
		return en;
	}

	public void setEn(Empleado en) {
		this.en = en;
	}
}