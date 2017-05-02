package com.Eu.paneles;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.Eu.controladores.ComparadorJornada;
import com.Eu.controladores.FuncionesDiversas;
import com.Eu.dao.EmpleadoDao;
import com.Eu.dao.JornadaDao;
import com.Eu.model.Empleado;
import com.Eu.model.Jornada;
import com.toedter.calendar.JCalendar;

public class PanelJornadas extends JPanel { 	
	
	static final int MANHANA=8;
	static final int TARDE=16;
	static final int NOCHE=0;
	static final int PARTIDO=10;
	static final int VACACION=7;
	static final int IT=13;
	static final int ASUNTOS=9;
	static final int COMPENSACION=14;
	static final int OTRAS=11;
	static final int LIBRE=12;
	
	public JCalendar jcEmpleado =new JCalendar();	
	Empleado e;	
	JPanel jpanel;
	Component componentes[];
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	
	private static final long serialVersionUID = 1L;
	final static Logger loggeador = Logger.getLogger(PanelJornadas.class);
	

	public PanelJornadas(int id){		
		
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

	public void rellenarJornadas() {
		// TODO Auto-generated method stub
		List<Jornada>lista=new ArrayList<Jornada>();
		JornadaDao jd=new JornadaDao();
		lista=jd.listaJornadas(this.getE().getIdpersona());
//		Collections.sort(lista,new ComparadorJornada());
		
		jcEmpleado.setWeekOfYearVisible(false);
		
		
		jpanel=jcEmpleado.getDayChooser().getDayPanel();
		componentes=jpanel.getComponents();
		
		//obtenemos la fecha del JCalendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(jcEmpleado.getDate());
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		
		//recorremos el arraylist of jornadas
		for(int i = 0; i < lista.size(); i++){
			Jornada j=lista.get(i);
			//obtenemos la fecha de inicio de la jornada
			Date fechaInicio=j.getInicioJornada();
			Calendar c=FuncionesDiversas.DateToCalendar(fechaInicio);
			
			int mes=c.get(Calendar.MONTH);
			int anho=c.get(Calendar.YEAR);
			int hora=c.get(Calendar.HOUR_OF_DAY);
			int minuto=c.get(Calendar.MINUTE);
			System.out.println(mes);
			System.out.println(anho);
			System.out.println(hora);
			System.out.println(minuto);
			
			if(month==mes && year==anho){
				//pintamos el día
				int dia=c.get(Calendar.DAY_OF_MONTH);
				//añadimos siete al día para saltarnos todas las cabeceras de los días
				dia=dia+5; 
				// Calculate the offset of the first day of the month
				cal.set(Calendar.DAY_OF_MONTH,1);
				int offset = cal.get(Calendar.DAY_OF_WEEK) - 1;
				//establecemos tipo de jornada
				switch (hora){
					case MANHANA:
						componentes[ dia + offset ].setForeground(Color.WHITE);
						componentes[ dia + offset ].setBackground(Color.blue);
						break;
					case TARDE:
						componentes[ dia + offset ].setBackground(Color.ORANGE);
						break;
					case NOCHE:
						switch(minuto){
							case NOCHE:
								componentes[ dia + offset ].setForeground(Color.WHITE);
								componentes[ dia + offset ].setBackground(Color.BLACK);
								break;
							case VACACION:
								componentes[ dia + offset ].setBackground(Color.cyan);
								break;
							case ASUNTOS:
								componentes[ dia + offset ].setForeground(Color.WHITE);
								componentes[ dia + offset ].setBackground(Color.red);
								break;
							case COMPENSACION:
								componentes[ dia + offset ].setBackground(Color.pink);
								break;
							case OTRAS:
								componentes[ dia + offset ].setBackground(Color.magenta);
								break;
							case IT:
								//Jornada especial
								componentes[ dia + offset ].setBackground(Color.yellow);
						}
						break;
					case PARTIDO:
						componentes[ dia + offset ].setBackground(Color.green);
						break;
										
				}		
			}		  
		}
	}
		
}

