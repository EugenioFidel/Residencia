package com.Eu.paneles;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.Eu.controladores.FuncionesDiversas;
import com.Eu.dao.EmpleadoDao;
import com.Eu.dao.JornadaDao;
import com.Eu.model.Empleado;
import com.Eu.model.Jornada;
import com.toedter.calendar.JCalendar;

public class PanelJornadas extends JPanel { 	
	
	static final int MANHANA=0;
	static final int TARDE=1;
	static final int NOCHE=2;
	static final int PARTIDO=3;
	static final int VACACION=4;
	static final int IT=5;
	static final int ASUNTOS=6;
	static final int COMPENSACION=7;
	static final int OTRAS=8;
	static final int LIBRE=9;
	
	public JCalendar jcEmpleado =new JCalendar();	
	Empleado em;	
	JPanel jpanel;
	PanelDetalleJ pdj;
	Component componentes[];
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	
	private static final long serialVersionUID = 1L;
	final static Logger loggeador = Logger.getLogger(PanelJornadas.class);
	

	public PanelJornadas(int id){		
		
		this.setSize(800,600);
		this.setLayout(gbl);	
		
		jcEmpleado.setSize(400, 400);
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=0.9;
		gbc.weighty=1;
		gbc.fill=GridBagConstraints.BOTH;
		this.add(jcEmpleado,gbc);
		gbc.weightx=1;
		
		pdj=new PanelDetalleJ();
		gbc.gridx=0;
		gbc.gridy=1;
		this.add(pdj,gbc);
	
		jcEmpleado.addPropertyChangeListener("calendar", new PropertyChangeListener() {

		    @Autowired
			public void propertyChange(PropertyChangeEvent e) {
		    	
		       //Vaciamos los campos de texto y el defaultListModel
		       pdj.dlm.clear();
		       pdj.jtfHoraInicio.setEditable(true);
		       pdj.jtfHoraFin.setEditable(true);
		       pdj.jtfHoraFin.setText("");
		       pdj.jtfHoraInicio.setText("");
		       //en qué dia hemos pulsado?
		       Calendar c=jcEmpleado.getCalendar();		       
		       
		       //conseguimos las jornadas de ese día y las metemos en una lista
		       JornadaDao jd=new JornadaDao();
		       List<Jornada>lista=jd.getJornadasPorFecha(c);
		       //la Jornada del tío seleccionado se grabará en cuadros de texto. Los otros a la lista
		       for(Jornada j:lista){
		    	   int idEmpleado=jd.getIdEmpleado(j);
		    	   EmpleadoDao ed=new EmpleadoDao();
		    	   Empleado emp=ed.getEmpleadoById(idEmpleado);
		    	   if(emp.getIdpersona()==em.getIdpersona()){
		    		   SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    		   pdj.setJ(j);
		    		   pdj.setC(c);
		    		   pdj.jtfHoraInicio.setText(sdt.format(j.getInicioJornada()));		    		  
		    		   pdj.jtfHoraFin.setText(sdt.format(j.getFinJornada()));			    		   
		    		   MarcarJRadio(j.getTipoJornada());
		    	   }else{
		    		   int tipoJ=j.getTipoJornada();
		    		   if(tipoJ==MANHANA || tipoJ==TARDE||tipoJ==NOCHE||tipoJ==PARTIDO|tipoJ==OTRAS){
		    			    pdj.dlm.addElement(emp.getNombre()+" "+emp.getPrimerApe());
		    		   }		    		  
		    	   }		    	   
		       }
		       pdj.jtfHoraInicio.setEditable(false);
		       pdj.jtfHoraFin.setEditable(false);
		    }			
		});			
	}

	public void rellenarJornadas() {
		List<Jornada>lista=new ArrayList<Jornada>();
		JornadaDao jd=new JornadaDao();
		lista=jd.listaJornadas(this.getE().getIdpersona());		
		jcEmpleado.setWeekOfYearVisible(false);		
		
		jpanel=jcEmpleado.getDayChooser().getDayPanel();
		componentes=jpanel.getComponents();
		
		//obtenemos la fecha del JCalendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(jcEmpleado.getDate());

		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		
		//recorremos el arraylist of jornadas
		for(int i = 0; i < lista.size(); i++){
			Jornada j=lista.get(i);
			int tipoJornada=j.getTipoJornada();
			//obtenemos la fecha de inicio de la jornada
			Date fechaInicio=j.getInicioJornada();
			
			Calendar c=FuncionesDiversas.DateToCalendar(fechaInicio);
			
			int mes=c.get(Calendar.MONTH);
			int anho=c.get(Calendar.YEAR);
						
			if(month==mes && year==anho){
				PintarDia(c,tipoJornada);				
			}		  
		}
	}
	

	public JCalendar getJcEmpleado() {
		return jcEmpleado;
	}

	public void setJcEmpleado(JCalendar jcEmpleado) {
		this.jcEmpleado = jcEmpleado;
	}

	public Empleado getE() {
		return em;
	}

	public void setE(Empleado e) {
		this.em = e;
	}	
	
	private void PintarDia(Calendar c, int tipoJornada) {
		//pintamos el día
		int dia=c.get(Calendar.DAY_OF_MONTH);
		//añadimos siete al día para saltarnos todas las cabeceras de los días
		dia=dia+5; 
		// Calculate the offset of the first day of the month
		c.set(Calendar.DAY_OF_MONTH,1);
		int offset = c.get(Calendar.DAY_OF_WEEK) - 1;
		//establecemos tipo de jornada
		switch (tipoJornada){
			case MANHANA:
				componentes[ dia + offset ].setForeground(Color.WHITE);
				componentes[ dia + offset ].setBackground(Color.blue);
				break;
			case TARDE:
				componentes[ dia + offset ].setBackground(Color.ORANGE);
				break;
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
				componentes[ dia + offset ].setBackground(Color.yellow);
				break;
			case PARTIDO:
				componentes[ dia + offset ].setBackground(Color.green);												
		}		
	}
	
	private void MarcarJRadio(int tipoJornada) {
		// TODO Auto-generated method stub
		switch (tipoJornada){
		case MANHANA:
			pdj.jrbManhana.setSelected(true);
			break;
		case TARDE:
			pdj.jrbTarde.setSelected(true);
			break;
		case NOCHE:
			pdj.jrbNoche.setSelected(true);
			break;
		case VACACION:
			pdj.jrbVacaciones.setSelected(true);
			break;
		case ASUNTOS:
			pdj.jrbAsuntos.setSelected(true);
			break;
		case COMPENSACION:
			pdj.jrbCompensacion.setSelected(true);
			break;
		case OTRAS:
			pdj.jrbOtras.setSelected(true);
			break;
		case IT:
			pdj.jrbIt.setSelected(true);
			break;
		case PARTIDO:
			pdj.jrbPartido.setSelected(true);
			break;
		default:
			pdj.jrbLibre.setSelected(true);
		}
	}
}

