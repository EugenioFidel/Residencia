package com.Eu.paneles;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.Eu.dao.EmpleadoDao;
import com.Eu.model.Empleado;
import com.toedter.calendar.JCalendar;

public class PanelJornadas extends JPanel { 	

	public JCalendar jcEmpleado =new JCalendar();	
	Empleado e;	
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	
	private static final long serialVersionUID = 1L;
	final static Logger loggeador = Logger.getLogger(PanelJornadas.class);

	public PanelJornadas(int id){
		jcEmpleado.setWeekOfYearVisible(false);
		EmpleadoDao e=new EmpleadoDao();
		this.setE(e.getEmpleadoById(id));		
		
		this.setSize(800,600);
		this.setLayout(gbl);
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.fill=GridBagConstraints.BOTH;
		this.add(jcEmpleado,gbc);
		
	}

	public JCalendar getJcEmpleado() {
		return jcEmpleado;
	}

	public void setJcEmpleado(JCalendar jcEmpleado) {
		this.jcEmpleado = jcEmpleado;
	}

	public Empleado getE() {
		return e;
	}

	public void setE(Empleado e) {
		this.e = e;
	}	
}

