package com.Eu.formularios;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.Eu.controladores.FuncionesDiversas;

public class AccPlanillas extends JDialog implements ActionListener{
	private static final long serialVersionUID = -540476757440700221L;

	final static Logger loggeador = Logger.getLogger(AccPlanillas.class);
	
	JLabel jlMes=new JLabel("Mes");
	JLabel jlAnho=new JLabel("Año");
	String[] meses=new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
	JComboBox<String> jcbMeses=new JComboBox<String>(meses);
	String[] anhos=new String[]{"2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"};
	JComboBox<String> jcbAnho=new JComboBox<String>(anhos);
	JPanel jpBotonero=new JPanel();
	JButton jbCancelar=new JButton("Cancelar");
	JButton jbAbrir=new JButton("Abrir");
	
	
	public AccPlanillas(){
		//propiedades del formulario
		setModal(true);
		setBounds(300, 300, 400,150);
		isDefaultLookAndFeelDecorated();
		setTitle("Residenciator - Abrir planillas de trabajo");
		
		//Declaramos el layout, GridBagLayout
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints col=new GridBagConstraints();
		setLayout(gbl);
		
		col.gridx=0;
		col.gridy=0;
		col.insets=new Insets(5, 5, 5, 5);
		this.getContentPane().add(jlMes, col);
		
		col.gridx=0;
		col.gridy=1;
		this.getContentPane().add(jlAnho, col);
		
		col.gridx=1;
		col.gridy=0;
		col.gridwidth=2;
		col.anchor=GridBagConstraints.WEST;
		col.fill=GridBagConstraints.HORIZONTAL;
		this.getContentPane().add(jcbMeses, col);
		
		col.gridx=1;
		col.gridy=1;
		col.gridwidth=2;
		this.getContentPane().add(jcbAnho, col);
		
		col.gridx=1;
		col.gridy=2;
		col.gridwidth=1;
		this.getContentPane().add(jbCancelar, col);
		
		col.gridx=2;
		col.gridy=2;
		col.gridwidth=1;
		this.getContentPane().add(jbAbrir, col);
		
		jbCancelar.addActionListener(this);
		jbAbrir.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(jbAbrir)){
			String rutaPlanilla=FuncionesDiversas.obtenerStringBase("Select rutaPlanillas from parametros");
			//ruta del archivo en el pc
			  String file = new String(rutaPlanilla+jcbMeses.getSelectedItem()+jcbAnho.getSelectedItem()+".xlsm"); 
						 
			 try{			
			    Desktop.getDesktop().open(new File(file));
			 }
			 catch (Exception e1)
			 {
			    System.out.println("Error al abrir el archivo " + file + "\n" + e1.getMessage());
			 }
		}
		
		this.dispose();
	}

}

