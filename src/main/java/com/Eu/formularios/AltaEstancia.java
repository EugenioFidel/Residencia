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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.Eu.dao.EstanciaDao;
import com.Eu.dao.Interno_estanciaDao;
import com.Eu.model.Estancia;
import com.Eu.model.Interno;
import com.Eu.model.Interno_estancia;
import com.toedter.calendar.JDateChooser;

public class AltaEstancia extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7951722283053397663L;

	final static Logger loggeador = Logger.getLogger(FrmPrincipal.class);

	SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

	JDateChooser jdcFecha = new JDateChooser();

	JLabel jlNombreEdit = new JLabel();
	JLabel jlFechaAlta = new JLabel("Fecha de alta: ");
	JRadioButton jrbResidencial = new JRadioButton("Residencial");
	JRadioButton jrbCentroDia = new JRadioButton("Centro de día");
	JRadioButton jrbCentroDiaFs = new JRadioButton("Centro de día + FS");
	JRadioButton jrbCentroDiaLav = new JRadioButton("Centro de día + Lav.");
	JRadioButton jrbCentroDiaFsLav = new JRadioButton("Centro de día + FS + Lav.");
	ButtonGroup bgTipoEstancia = new ButtonGroup();

	JButton jbCancelar = new JButton("Cancelar");
	JButton jbGrabar = new JButton("Grabar");

	Interno in;
	DefaultTableModel dtm;

	Font fuente = new Font("Ubuntu", 0, 20);
	Font fuenteN = new Font("Ubuntu", 1, 20);

	public AltaEstancia(Interno i, DefaultTableModel dtm) {

		this.setIn(i);
		this.setDtm(dtm);

		// propiedades del formulario
		setModal(true);
		setBounds(300, 300, 500, 400);
		isDefaultLookAndFeelDecorated();
		setTitle("Residenciator - Alta de nueva estancia");
		
		jlNombreEdit.setFont(fuenteN);
		jlFechaAlta.setFont(fuenteN);
		jrbCentroDia.setFont(fuenteN);
		jrbResidencial.setFont(fuenteN);
		jrbCentroDiaFs.setFont(fuenteN);
		jrbCentroDiaLav.setFont(fuenteN);
		jrbCentroDiaFsLav.setFont(fuenteN);

		jdcFecha.setFont(fuenteN);

		// Declaramos el layout, GridBagLayout
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints col = new GridBagConstraints();
		setLayout(gbl);

		jrbResidencial.setSelected(true);
		bgTipoEstancia.add(jrbResidencial);
		bgTipoEstancia.add(jrbCentroDia);
		bgTipoEstancia.add(jrbCentroDiaFs);
		bgTipoEstancia.add(jrbCentroDiaLav);
		bgTipoEstancia.add(jrbCentroDiaFsLav);

		// obtenemos el nombre y apellidos del fulano
		jlNombreEdit.setText(
				this.getIn().getNombre() + " " + this.getIn().getPrimerApe() + " " + this.getIn().getSegundoApe());
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
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jlFechaAlta, col);

		// colocamos los controles
		col.gridx = 1;
		col.gridy = 1;
		col.insets = new Insets(2, 2, 2, 2);
		jdcFecha.setToolTipText("Introducir fecha de alta");
		this.getContentPane().add(jdcFecha, col);

		// colocamos los controles
		col.gridx = 0;
		col.gridy = 2;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrbResidencial, col);

		col.gridx = 0;
		col.gridy = 3;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrbCentroDia, col);

		col.gridx = 0;
		col.gridy = 4;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrbCentroDiaFs, col);
		
		col.gridx = 0;
		col.gridy = 5;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrbCentroDiaLav, col);
		
		col.gridx = 0;
		col.gridy = 6;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrbCentroDiaFsLav, col);
		
		// jbCancelar, configuraci�n y colocaci�n
		jbCancelar.addActionListener(this);
		col.gridx = 0;
		col.gridy = 7;
		this.getContentPane().add(jbCancelar, col);

		// jbAceptar, configuraci�n y colocaci�n
		jbGrabar.addActionListener(this);
		col.gridx = 1;
		col.gridy = 4;
		this.getContentPane().add(jbGrabar, col);

		Component[] c = this.getContentPane().getComponents();
		for (Component comp : c) {
			if (comp instanceof JLabel || comp instanceof JTextField || comp instanceof JButton
					|| comp instanceof JRadioButton) {
				comp.setFont(fuenteN);
			}
		}

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(jbGrabar)) {
			loggeador.debug("Grabar estancia");
			int idEstancia = GrabarEstancia(in);
			String tipoEstancia = "";
			if (jrbResidencial.isSelected()) {
				tipoEstancia = "Residencial";
			} else if(jrbCentroDia.isSelected()){
				tipoEstancia = "Centro de día";
			} else if(jrbCentroDiaFs.isSelected()) {
				tipoEstancia="Centro de día + FS";
			} else if(jrbCentroDiaLav.isSelected()) {
				tipoEstancia="Centro de día + Lav.";
			}else {
				tipoEstancia="Centro de día + FS + Lav.";
			}
			Object[] fila = { idEstancia, dt.format(jdcFecha.getDate()), tipoEstancia, "", "En alta" };
			dtm.addRow(fila);
			this.dispose();
		} else {
			this.dispose();
		}

	}

	private int GrabarEstancia(Interno i) {
		Estancia e = new Estancia();
		e.setFechaAlta(jdcFecha.getDate());
		if (jrbResidencial.isSelected()) {
			e.setTipoEstancia(1);
		} else if(jrbCentroDia.isSelected()){
			e.setTipoEstancia(2);;
		} else if(jrbCentroDiaFs.isSelected()) {
			e.setTipoEstancia(3);;
		} else if(jrbCentroDiaLav.isSelected()) {
			e.setTipoEstancia(4);;
		}else {
			e.setTipoEstancia(5);;
		}

		EstanciaDao ed = new EstanciaDao();
		ed.addEstancia(e);
		Interno_estancia ie = new Interno_estancia();
		ie.setIdEstancia(e.getIdEstancia());
		ie.setIdPersona(this.getIn().getIdpersona());
		Interno_estanciaDao ied = new Interno_estanciaDao();
		try {
			ied.addInterno_estancia(ie);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			loggeador.debug("error en la insercción de la estancia");
		}
		return e.getIdEstancia();
	}

	public Interno getIn() {
		return in;
	}

	public void setIn(Interno in) {
		this.in = in;
	}

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}
}