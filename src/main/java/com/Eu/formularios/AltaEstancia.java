package com.Eu.formularios;

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
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.Eu.dao.EstanciaDao;
import com.Eu.dao.Interno_estanciaDao;
import com.Eu.model.Estancia;
import com.Eu.model.Interno;
import com.Eu.model.Interno_estancia;
import com.toedter.calendar.JDateChooser;

public class AltaEstancia extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7951722283053397663L;

	final static Logger loggeador = Logger.getLogger(FrmPrincipal.class);
	
	SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy"); 	
	
	JDateChooser jdcFecha=new JDateChooser();
	
	JLabel jlNombreEdit=new JLabel();
	JLabel jlFechaAlta=new JLabel("Fecha de alta: ");
	JRadioButton jrbResidencial = new JRadioButton("Residencial");
	JRadioButton jrbCentroDia = new JRadioButton("Centro de día");
	ButtonGroup bgTipoEstancia = new ButtonGroup();
	
	JButton jbCancelar=new JButton("Cancelar");
	JButton jbGrabar=new JButton("Grabar");
	
	Interno in;
	DefaultTableModel dtm;
	
	public AltaEstancia(Interno i, DefaultTableModel dtm){
		
		this.setIn(i);
		this.setDtm(dtm);
		
		//propiedades del formulario
		setModal(true);
		setBounds(300, 300, 450,300);
		isDefaultLookAndFeelDecorated();
		setTitle("Residenciator - Alta de nueva estancia");
		
		//Declaramos el layout, GridBagLayout
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints col=new GridBagConstraints();
		setLayout(gbl);
		
		jrbResidencial.setSelected(true);
		bgTipoEstancia.add(jrbResidencial);
		bgTipoEstancia.add(jrbCentroDia);	
		
		
		//obtenemos el nombre y apellidos del fulano
		jlNombreEdit.setText(this.getIn().getNombre()+" "+
				this.getIn().getPrimerApe()+" "+
				this.getIn().getSegundoApe());
		col.gridx=0;
		col.gridy=0;
		col.gridwidth=2;
		col.weightx=1;
		col.fill=GridBagConstraints.BOTH;
		col.insets=new Insets(2,2,2,2);
		this.getContentPane().add(jlNombreEdit,col);
		col.gridwidth=1;
		
		col.gridx=0;
		col.gridy=1;
		col.insets=new Insets(2,2,2,2);
		this.getContentPane().add(jlFechaAlta,col);
		
		//colocamos los controles
		col.gridx=1;
		col.gridy=1;
		col.insets=new Insets(2,2,2,2);
		jdcFecha.setToolTipText("Introducir fecha de alta");
		this.getContentPane().add(jdcFecha,col);
		
		//colocamos los controles
		col.gridx=0;
		col.gridy=2;
		col.insets=new Insets(2,2,2,2);
		this.getContentPane().add(jrbResidencial,col);
		
		col.gridx=0;
		col.gridy=3;
		col.insets=new Insets(2,2,2,2);
		this.getContentPane().add(jrbCentroDia,col);		
		
		
		//jbCancelar, configuraci�n y colocaci�n
		jbCancelar.addActionListener(this);
		col.gridx=0;
		col.gridy=4;
		this.getContentPane().add(jbCancelar,col);
				
		//jbAceptar, configuraci�n y colocaci�n
		jbGrabar.addActionListener(this);
		col.gridx=1;
		col.gridy=4;
		this.getContentPane().add(jbGrabar,col);
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(jbGrabar)){
			loggeador.debug("Grabar estancia");
			int idEstancia=GrabarEstancia(in);
			String tipoEstancia="";
			if(jrbResidencial.isSelected()){
				tipoEstancia="Residencial";
			}else{
				tipoEstancia="Centro de día";
			}			
			Object[]fila={idEstancia,dt.format(jdcFecha.getDate()),tipoEstancia,"","En alta"};
			dtm.addRow(fila);
			this.dispose();
		}else{
			this.dispose();
		}
		
	}

	private int GrabarEstancia(Interno i) {
		Estancia e=new Estancia();
		e.setFechaAlta(jdcFecha.getDate());
		if(jrbResidencial.isSelected()){
			e.setTipoEstancia(1);
		}else{
			e.setTipoEstancia(2);
		}
		EstanciaDao ed=new EstanciaDao();
		ed.addEstancia(e);
		Interno_estancia ie=new Interno_estancia();
		ie.setIdEstancia(e.getIdEstancia());
		ie.setIdPersona(this.getIn().getIdpersona());
		Interno_estanciaDao ied=new Interno_estanciaDao();
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