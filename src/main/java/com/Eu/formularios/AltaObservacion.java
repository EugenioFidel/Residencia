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
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.Eu.controladores.FuncionesDiversas;
import com.Eu.dao.Interno_observacionDao;
import com.Eu.dao.ObservacionDao;
import com.Eu.model.Interno;
import com.Eu.model.Interno_observacion;
import com.Eu.model.Observacion;
import com.toedter.calendar.JDateChooser;

public class AltaObservacion extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7951722283053397663L;

	final static Logger loggeador = Logger.getLogger(FrmPrincipal.class);

	SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

	JDateChooser jdcFecha = new JDateChooser();
	JLabel jlNombre = new JLabel("Apellidos y Nombre: ");
	JLabel jlNombreEdit = new JLabel("Nombre de la persona");
	JLabel jlFechaObservacion = new JLabel("Fecha: ");
	JLabel jlAlimentacion = new JLabel("Alimentacion: ");
	JLabel jlMovilidad = new JLabel("Movilidad: ");
	JLabel jlAseo = new JLabel("Aseo: ");
	JLabel jlVestido = new JLabel("Vestido: ");
	JLabel jlInodoro = new JLabel("Inodoro: ");
	JLabel jlEsfinteres = new JLabel("Esfínteres: ");

	JRadioButton jrAlimentacionT = new JRadioButton("Independiente");
	JRadioButton jrAlimentacionF = new JRadioButton("Dependiente");
	ButtonGroup bgAlimentacion = new ButtonGroup();

	JRadioButton jrMovilidadT = new JRadioButton("Independiente");
	JRadioButton jrMovilidadF = new JRadioButton("Dependiente");
	ButtonGroup bgMovilidad = new ButtonGroup();

	JRadioButton jrAseoT = new JRadioButton("Independiente");
	JRadioButton jrAseoF = new JRadioButton("Dependiente");
	ButtonGroup bgAseo = new ButtonGroup();

	JRadioButton jrVestidoT = new JRadioButton("Independiente");
	JRadioButton jrVestidoF = new JRadioButton("Dependiente");
	ButtonGroup bgVestido = new ButtonGroup();

	JRadioButton jrInodoroT = new JRadioButton("Independiente");
	JRadioButton jrInodoroF = new JRadioButton("Dependiente");
	ButtonGroup bgInodoro = new ButtonGroup();

	JRadioButton jrEsfinteresT = new JRadioButton("Independiente");
	JRadioButton jrEsfinteresF = new JRadioButton("Dependiente");
	ButtonGroup bgEsfinteres = new ButtonGroup();

	JButton jbCancelar = new JButton("Cancelar");
	JButton jbGrabar = new JButton("Grabar");

	Interno in;
	DefaultTableModel dtm;

	Font fuente = new Font("Verdana", 0, 16);
	Font fuenteN = new Font("Verdana", 1, 16);

	public AltaObservacion(Interno i, DefaultTableModel dtm) {

		this.setIn(i);
		this.setDtm(dtm);

		// propiedades del formulario
		setModal(true);
		setBounds(300, 150, 700, 400);
		isDefaultLookAndFeelDecorated();
		setTitle("Residenciator - Alta de nueva observacion");

		jdcFecha.setFont(fuenteN);

		// Declaramos el layout, GridBagLayout
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints col = new GridBagConstraints();
		setLayout(gbl);

		jrAlimentacionT.setSelected(true);
		bgAlimentacion.add(jrAlimentacionT);
		bgAlimentacion.add(jrAlimentacionF);

		jrMovilidadT.setSelected(true);
		bgMovilidad.add(jrMovilidadT);
		bgMovilidad.add(jrMovilidadF);

		jrVestidoT.setSelected(true);
		bgVestido.add(jrVestidoT);
		bgVestido.add(jrVestidoF);

		jrAseoT.setSelected(true);
		bgAseo.add(jrAseoT);
		bgAseo.add(jrAseoF);

		jrInodoroT.setSelected(true);
		bgInodoro.add(jrInodoroT);
		bgInodoro.add(jrInodoroF);

		jrEsfinteresT.setSelected(true);
		bgEsfinteres.add(jrEsfinteresT);
		bgEsfinteres.add(jrEsfinteresF);

		// colocamos los controles
		col.gridx = 0;
		col.gridy = 0;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(10, 10, 10, 10);
		this.getContentPane().add(jlNombre, col);

		// obtenemos el nombre y apellidos del fulano
		jlNombreEdit.setText(
				this.getIn().getNombre() + " " + this.getIn().getPrimerApe() + " " + this.getIn().getSegundoApe());
		col.gridx = 1;
		col.gridy = 0;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jlNombreEdit, col);

		// jdcFecha, configuraci�n y colocaci�n
		jdcFecha.setBorder(BorderFactory.createTitledBorder("Fecha"));
		jdcFecha.setDate(Calendar.getInstance().getTime());
		col.gridx = 0;
		col.gridy = 1;
		col.fill = GridBagConstraints.HORIZONTAL;
		this.getContentPane().add(jdcFecha, col);

		col.gridx = 0;
		col.gridy = 2;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jlAlimentacion, col);

		col.gridx = 1;
		col.gridy = 2;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrAlimentacionT, col);

		col.gridx = 2;
		col.gridy = 2;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrAlimentacionF, col);

		col.gridx = 0;
		col.gridy = 3;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jlMovilidad, col);

		col.gridx = 1;
		col.gridy = 3;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrMovilidadT, col);

		col.gridx = 2;
		col.gridy = 3;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrMovilidadF, col);

		col.gridx = 0;
		col.gridy = 4;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jlAseo, col);

		col.gridx = 1;
		col.gridy = 4;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrAseoT, col);

		col.gridx = 2;
		col.gridy = 4;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrAseoF, col);

		col.gridx = 0;
		col.gridy = 5;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jlVestido, col);

		col.gridx = 1;
		col.gridy = 5;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrVestidoT, col);

		col.gridx = 2;
		col.gridy = 5;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrVestidoF, col);

		col.gridx = 0;
		col.gridy = 6;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jlInodoro, col);

		col.gridx = 1;
		col.gridy = 6;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrInodoroT, col);

		col.gridx = 2;
		col.gridy = 6;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrInodoroF, col);

		col.gridx = 0;
		col.gridy = 7;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jlEsfinteres, col);

		col.gridx = 1;
		col.gridy = 7;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrEsfinteresT, col);

		col.gridx = 2;
		col.gridy = 7;
		col.fill = GridBagConstraints.BOTH;
		col.insets = new Insets(2, 2, 2, 2);
		this.getContentPane().add(jrEsfinteresF, col);

		// jbCancelar, configuraci�n y colocaci�n
		jbCancelar.addActionListener(this);
		col.gridx = 1;
		col.gridy = 8;
		this.getContentPane().add(jbCancelar, col);

		// jbAceptar, configuraci�n y colocaci�n
		jbGrabar.addActionListener(this);
		col.gridx = 2;
		col.gridy = 8;
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
			loggeador.debug("Grabar observacion");
			GrabarObservacion(in);
		} else {
			this.dispose();
		}

	}

	private void GrabarObservacion(Interno i) {
		// TODO Auto-generated method stub
		Observacion o = new Observacion();
		if (jrAlimentacionF.isSelected()) {
			o.setAlimentacion("DEPENDIENTE");
		} else {
			o.setAlimentacion("INDEPENDIENTE");
		}
		if (jrVestidoF.isSelected()) {
			o.setVestido("DEPENDIENTE");
		} else {
			o.setVestido("INDEPENDIENTE");
		}
		if (jrAseoF.isSelected()) {
			o.setAseo("DEPENDIENTE");
		} else {
			o.setAseo("INDEPENDIENTE");
		}
		if (jrMovilidadF.isSelected()) {
			o.setMovilidad("DEPENDIENTE");
		} else {
			o.setMovilidad("INDEPENDIENTE");
		}
		if (jrInodoroF.isSelected()) {
			o.setInodoro("DEPENDIENTE");
		} else {
			o.setInodoro("INDEPENDIENTE");
		}
		if (jrEsfinteresF.isSelected()) {
			o.setEsfinteres("DEPENDIENTE");
		} else {
			o.setEsfinteres("INDEPENDIENTE");
		}
		o.setFechaObservacion(jdcFecha.getDate());
		// el campo grado de dependencia es un campo calculado
		FuncionesDiversas.evaluarGradoDependencia(o);
		loggeador.debug("observacion creada");

		// insertar la observacion
		ObservacionDao od = new ObservacionDao();
		od.addObservacion(o);
		int id = o.getIdObservacion();
		loggeador.debug(id);
		Interno_observacion io = new Interno_observacion();
		io.setIdPersona(this.getIn().getIdpersona());
		io.setIdObservacion(id);
		Interno_observacionDao iod = new Interno_observacionDao();
		try {
			iod.addInterno_observacion(io);
			Object[] fila = { o.getIdObservacion(), dt.format(o.getFechaObservacion()), o.getAlimentacion(),
					o.getVestido(), o.getAseo(), o.getMovilidad(), o.getInodoro(), o.getEsfinteres(),
					o.getGradoDependencia() };
			dtm.addRow(fila);
			this.dispose();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
