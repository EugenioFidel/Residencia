package com.Eu.paneles;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.Eu.dao.EmpleadoDao;
import com.Eu.dao.JornadaDao;
import com.Eu.model.Empleado;
import com.Eu.model.Jornada;

public class PanelDetalleJ extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
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
	
	Jornada j;
	Empleado e;
	Calendar c;
	JLabel jlDia;
	JLabel jlHoraInicio;
	JLabel jlHoraFin;
	JTextField jtfHoraInicio;
	JTextField jtfHoraFin;	
	JPanel jpRadioButtons=new JPanel();
//	JPanel jpCompanheros=new JPanel();
	DefaultListModel<String> dlm=new DefaultListModel<String>();
	JList<String> jlCompanheros=new JList<String>(dlm);
	ButtonGroup bg=new ButtonGroup();
	
	JRadioButton jrbManhana=new JRadioButton("Mañana");
	JRadioButton jrbTarde=new JRadioButton("Tarde");
	JRadioButton jrbNoche=new JRadioButton("Noche");
	JRadioButton jrbPartido=new JRadioButton("Partido");
	JRadioButton jrbVacaciones=new JRadioButton("Vacaciones");
	JRadioButton jrbIt=new JRadioButton("Incap. transitoria");
	JRadioButton jrbAsuntos=new JRadioButton("Asuntos prop.");
	JRadioButton jrbCompensacion=new JRadioButton("Compensación");
	JRadioButton jrbOtras=new JRadioButton("Otras licencias");
	JRadioButton jrbLibre=new JRadioButton("Libre");
	
	SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public PanelDetalleJ(){		
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		
		this.setLayout(gbl);		
		
		jlDia=new JLabel("EDICIÓN DE JORNADA.:");
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=4;
		gbc.insets=new Insets(10,10,10,10);
		this.add(jlDia, gbc);		
		
		bg.add(jrbManhana);
		bg.add(jrbTarde);
		bg.add(jrbNoche);
		bg.add(jrbPartido);
		bg.add(jrbVacaciones);
		bg.add(jrbAsuntos);
		bg.add(jrbCompensacion);
		bg.add(jrbIt);
		bg.add(jrbOtras);
		bg.add(jrbLibre);
		
		jpRadioButtons.setLayout(new GridLayout(4,3));
		jrbManhana.addActionListener(this);
		jpRadioButtons.add(jrbManhana);
		jrbTarde.addActionListener(this);
		jpRadioButtons.add(jrbTarde);
		jrbNoche.addActionListener(this);
		jpRadioButtons.add(jrbNoche);
		jrbPartido.addActionListener(this);
		jpRadioButtons.add(jrbPartido);
		jrbVacaciones.addActionListener(this);
		jpRadioButtons.add(jrbVacaciones);
		jrbIt.addActionListener(this);
		jpRadioButtons.add(jrbIt);
		jrbAsuntos.addActionListener(this);
		jpRadioButtons.add(jrbAsuntos);
		jrbCompensacion.addActionListener(this);
		jpRadioButtons.add(jrbCompensacion);
		jrbOtras.addActionListener(this);
		jpRadioButtons.add(jrbOtras);
		jrbLibre.addActionListener(this);
		jpRadioButtons.add(jrbLibre);
				
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=4;
		this.add(jpRadioButtons, gbc);
		
		jlHoraInicio=new JLabel("Hora inicio: ");
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=1;
		gbc.anchor=GridBagConstraints.EAST;
		this.add(jlHoraInicio,gbc);
		
		jtfHoraInicio=new JTextField(20);
		gbc.gridx=1;
		gbc.gridy=2;
		gbc.anchor=GridBagConstraints.WEST;
		this.add(jtfHoraInicio, gbc);
		
		jlHoraFin=new JLabel("Hora fin: ");
		gbc.gridx=2;
		gbc.gridy=2;
		gbc.anchor=GridBagConstraints.EAST;
		this.add(jlHoraFin, gbc);
		
		jtfHoraFin=new JTextField(20);
		gbc.gridx=3;
		gbc.gridy=2;
		gbc.anchor=GridBagConstraints.WEST;
		this.add(jtfHoraFin, gbc);
		
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.weightx=1;
		gbc.weighty=1;
		gbc.gridwidth=4;
		gbc.fill=GridBagConstraints.BOTH;
		this.add(jlCompanheros,gbc);
	}	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		jtfHoraInicio.setEditable(true);
		jtfHoraFin.setEditable(true);
		Jornada jo=this.getJ();
		if(e.getSource().equals(jrbManhana)){
			jo.setTipoJornada(MANHANA);
			EstablecerHora(8,0,0);			
			jtfHoraInicio.setText(sdf.format(c.getTime()));
			EstablecerHora(15,59,59);
			jtfHoraFin.setText(sdf.format(c.getTime()));
		}else if(e.getSource().equals(jrbTarde)){
				jo.setTipoJornada(TARDE);
				EstablecerHora(16,0,0);
				jtfHoraInicio.setText(sdf.format(c.getTime()));
				EstablecerHora(23,59,59);
				jtfHoraFin.setText(sdf.format(c.getTime()));
			}else if (e.getSource().equals(jrbNoche)){
					jo.setTipoJornada(NOCHE);
					EstablecerHora(0,0,0);
					jtfHoraInicio.setText(sdf.format(c.getTime()));
					EstablecerHora(7,59,59);
					jtfHoraFin.setText(sdf.format(c.getTime()));
				}else if(e.getSource().equals(jrbPartido)){
						jo.setTipoJornada(PARTIDO);
						EstablecerHora(10,0,0);
						jtfHoraInicio.setText(sdf.format(c.getTime()));
						EstablecerHora(17,59,59);
						jtfHoraFin.setText(sdf.format(c.getTime()));
					}else if(e.getSource().equals(jrbVacaciones)){
							jo.setTipoJornada(VACACION);
							EstablecerHora(0,0,0);
							jtfHoraInicio.setText(sdf.format(c.getTime()));
							jtfHoraFin.setText(sdf.format(c.getTime()));
						}else if(e.getSource().equals(jrbOtras)){
								jo.setTipoJornada(OTRAS);
								EstablecerHora(0,0,0);
								jtfHoraInicio.setText(sdf.format(c.getTime()));
								jtfHoraFin.setText(sdf.format(c.getTime()));
							}else if(e.getSource().equals(jrbIt)){
								jo.setTipoJornada(IT);
								EstablecerHora(0,0,0);
								jtfHoraInicio.setText(sdf.format(c.getTime()));
								jtfHoraFin.setText(sdf.format(c.getTime()));
							}else if(e.getSource().equals(jrbLibre)){
									jo.setTipoJornada(LIBRE);
									EstablecerHora(0,0,0);
									jtfHoraInicio.setText(sdf.format(c.getTime()));
									jtfHoraFin.setText(sdf.format(c.getTime()));
								}else if(e.getSource().equals(jrbCompensacion)){
										jo.setTipoJornada(COMPENSACION);
										EstablecerHora(0,0,0);
										jtfHoraInicio.setText(sdf.format(c.getTime()));
										jtfHoraFin.setText(sdf.format(c.getTime()));
									}else if(e.getSource().equals(jrbAsuntos)){
											jo.setTipoJornada(ASUNTOS);
											EstablecerHora(0,0,0);
											jtfHoraInicio.setText(sdf.format(c.getTime()));
											jtfHoraFin.setText(sdf.format(c.getTime()));
										}		
		
		try {
			jo.setInicioJornada(sdf.parse(jtfHoraInicio.getText()));
			jo.setFinJornada(sdf.parse(jtfHoraFin.getText()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JornadaDao jd=new JornadaDao();
		try {
			jd.addJornada(jo);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		jtfHoraInicio.setEditable(false);
		jtfHoraFin.setEditable(false);
	}

	private void EstablecerHora(int hora, int minuto, int segundo) {
		// TODO Auto-generated method stub
		c.set(Calendar.HOUR_OF_DAY, hora);
		c.set(Calendar.MINUTE, minuto);
		c.set(Calendar.SECOND,segundo);
	}
	
	public Jornada getJ() {
		return j;
	}

	public void setJ(Jornada j) {
		this.j = j;
	}

	public Empleado getE() {
		return e;
	}

	public void setE(Empleado e) {
		this.e = e;
	}

	public Calendar getC() {
		return c;
	}

	public void setC(Calendar c) {
		this.c = c;
	}
	
	public void RellenarListaJornadas(Calendar cal, Empleado empleado){
		//Vaciamos los campos de texto y el defaultListModel
	       dlm.clear();
	       jtfHoraInicio.setEditable(true);
	       jtfHoraFin.setEditable(true);
	       jtfHoraFin.setText("");
	       jtfHoraInicio.setText("");
	       int tipoJornada=1000;
	       SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
	       //Obtemenos el array list de jornadas del empleado en cuestión
	       Set<Jornada>listaJornadasEmpleado=new HashSet<Jornada>();
	       listaJornadasEmpleado=empleado.getJornadas();
	       for(Jornada je:listaJornadasEmpleado){
	    	   System.out.println(sdf2.format(je.getInicioJornada()));
	    	   System.out.println(sdf2.format(cal.getTime()));
	    	  //obtenemos la fecha de cada jornada y la comparamos con la pulsada extrayendo su tipo de jornada
	    	   if(sdf2.format(je.getInicioJornada()).equals(sdf2.format(cal.getTime()))){
	    		   tipoJornada=je.getTipoJornada();
	    		   break;
	    	   }		   
	    	   
	       } 	
	       
	       System.out.println("fecha buscada " +sdf2.format(cal.getTime()));
	       
	       //conseguimos las jornadas de ese día y las metemos en una lista
	       JornadaDao jd=new JornadaDao();
	       List<Jornada>lista=jd.getJornadasPorFecha(cal);
	       //la Jornada del tío seleccionado se grabará en cuadros de texto. Los otros a la lista
	       for(Jornada j:lista){
	    	   int idEmpleado=jd.getIdEmpleado(j);
	    	   EmpleadoDao ed=new EmpleadoDao();
	    	   Empleado emp=ed.getEmpleadoById(idEmpleado);
	    	   if(emp.getIdpersona()==empleado.getIdpersona()){
	    		   SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    		   setJ(j);
	    		   setC(cal);
	    		   jtfHoraInicio.setText(sdt.format(j.getInicioJornada()));		    		  
	    		   jtfHoraFin.setText(sdt.format(j.getFinJornada()));			    		   
	    		   MarcarJRadio(j.getTipoJornada());
	    	   }else{
	    		   int tipoJ=j.getTipoJornada();
	    		   if(tipoJ==tipoJornada){
	    			   if(tipoJ==MANHANA || tipoJ==TARDE||tipoJ==NOCHE||tipoJ==PARTIDO|tipoJ==OTRAS){
	    				   dlm.addElement(emp.getNombre()+" "+emp.getPrimerApe());
	    			   }
	    		   }
	    		   		    		  
	    	   }		    	   
	       }
	       jtfHoraInicio.setEditable(false);
	       jtfHoraFin.setEditable(false);
	}
	
	private void MarcarJRadio(int tipoJornada) {
		// TODO Auto-generated method stub
		switch (tipoJornada){
		case MANHANA:
			jrbManhana.setSelected(true);
			break;
		case TARDE:
			jrbTarde.setSelected(true);
			break;
		case NOCHE:
			jrbNoche.setSelected(true);
			break;
		case VACACION:
			jrbVacaciones.setSelected(true);
			break;
		case ASUNTOS:
			jrbAsuntos.setSelected(true);
			break;
		case COMPENSACION:
			jrbCompensacion.setSelected(true);
			break;
		case OTRAS:
			jrbOtras.setSelected(true);
			break;
		case IT:
			jrbIt.setSelected(true);
			break;
		case PARTIDO:
			jrbPartido.setSelected(true);
			break;
		default:
			jrbLibre.setSelected(true);
		}
	}
}
