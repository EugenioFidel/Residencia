package com.Eu.formularios;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.Eu.dao.EmpleadoDao;
import com.Eu.model.Empleado;
import com.Eu.model.Interno;
import com.Eu.model.Jornada;
import com.toedter.calendar.JDateChooser;

public class AltaPatron extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7951722283053397663L;

	final static Logger loggeador = Logger.getLogger(FrmPrincipal.class);
	
	SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy"); 	
	
	JDateChooser jdcFechaInicio=new JDateChooser();
	JDateChooser jdcFechaFin=new JDateChooser();
	
	JLabel jlEmpleado=new JLabel("Empleado: ");
	JComboBox jcbEmpleados=new JComboBox();
	
	JLabel jlFechaInicio=new JLabel("Fecha inicio");
	JLabel jlFechaFin=new JLabel("Fecha final");
	
	JLabel jlPatron=new JLabel("PATRON DE JORNADAS");
	JTextField jtfPatron=new JTextField(20);
	
	JLabel jlCodigo=new JLabel("CODIGO");
	JLabel jlManhana=new JLabel("M - Mañana");
	JLabel jlTarde=new JLabel("T - Tarde");
	JLabel jlNoche=new JLabel("N - Noche");
	JLabel jlPartido=new JLabel("P - Partido");
	JLabel jlVacaciones=new JLabel("V - Vacaciones");
	JLabel jlIt=new JLabel("I - Incapacidad transitoria");
	JLabel jlAsuntos=new JLabel("A - Asuntos propios");
	JLabel jlCompensacion=new JLabel("C - Compensación jornada");
	JLabel jlOtrasLicencias=new JLabel("O - Otras licencias");
	JLabel jlLibre=new JLabel("L - Libre");
	
	JLabel jlDuracion=new JLabel("Duración jornada: ");
	JLabel jlHoras=new JLabel("Horas: ");
	JTextField jtfHoras=new JTextField(5);
	JLabel jlMinutos=new JLabel("Minutos: ");
	JTextField jtfMinutos=new JTextField(5);	
	
	JButton jbCancelar=new JButton("Cancelar");
	JButton jbGrabar=new JButton("Grabar");
	
	Interno in;
	DefaultTableModel dtm;
	
	public AltaPatron(){
		
		//propiedades del formulario
		setModal(true);
		setBounds(300, 150, 600, 450);
		isDefaultLookAndFeelDecorated();
		setTitle("Residenciator - Introducción de patrón de jornadas");
		jtfHoras.setText("8");
		jtfMinutos.setText("0");
		
		//Declaramos el layout, GridBagLayout
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints col=new GridBagConstraints();
		setLayout(gbl);
		
		RellenarComboEmpleados();
		
		col.gridx=0;
		col.gridy=0;
		col.insets=new Insets(5, 5, 5, 5);
//		col.fill=GridBagConstraints.BOTH;
		col.anchor=GridBagConstraints.EAST;
		this.add(jlEmpleado, col);
		col.anchor=GridBagConstraints.CENTER;
		
		col.gridx=1;
		col.gridy=0;
		col.gridwidth=4;
		this.add(jcbEmpleados,col);
		
		col.gridx=0;
		col.gridy=1;
		col.gridwidth=1;
		this.add(jlFechaInicio,col);
		
		col.gridx=1;
		col.gridy=1;
		this.add(jlFechaFin,col);
		
		col.gridx=2;
		col.gridy=1;
		col.gridwidth=3;
		this.add(jlPatron,col);		
		
		col.gridx=0;
		col.gridy=2;
		col.gridwidth=1;
		this.add(jdcFechaInicio,col);
		
		col.gridx=1;
		col.gridy=2;
		this.add(jdcFechaFin,col);		
		
		col.gridx=2;
		col.gridy=2;
		col.gridwidth=3;
		this.add(jtfPatron,col);
		
		col.gridx=0;
		col.gridy=3;
		col.gridwidth=1;
		this.add(jlDuracion,col);
		
		col.gridx=1;
		col.gridy=3;
		col.gridwidth=1;
		col.anchor=GridBagConstraints.EAST;
		this.add(jlHoras,col);
		col.anchor=GridBagConstraints.WEST;
		
		col.gridx=2;
		col.gridy=3;
		col.gridwidth=1;
		col.fill=GridBagConstraints.HORIZONTAL;
		this.add(jtfHoras,col);
		col.fill=GridBagConstraints.NONE;
		
		col.gridx=3;
		col.gridy=3;
		col.gridwidth=1;
		col.anchor=GridBagConstraints.EAST;
		this.add(jlMinutos,col);
		col.anchor=GridBagConstraints.WEST;
		
		col.gridx=4;
		col.gridy=3;
		col.gridwidth=1;
		col.fill=GridBagConstraints.HORIZONTAL;
		this.add(jtfMinutos,col);
		col.fill=GridBagConstraints.NONE;
		
		col.gridx=0;
		col.gridy=4;
		col.gridwidth=5;
		col.anchor=GridBagConstraints.CENTER;
		this.add(jlCodigo,col);
		
		col.gridx=0;
		col.gridy=5;
		col.gridwidth=5;
		this.add(jlManhana,col);
		
		col.gridx=0;
		col.gridy=6;
		col.gridwidth=5;
		this.add(jlTarde,col);
		
		col.gridx=0;
		col.gridy=7;
		col.gridwidth=5;
		this.add(jlNoche,col);
		
		col.gridx=0;
		col.gridy=8;
		col.gridwidth=5;
		this.add(jlPartido,col);
		
		col.gridx=0;
		col.gridy=9;
		col.gridwidth=5;
		this.add(jlVacaciones,col);
		
		col.gridx=0;
		col.gridy=10;
		col.gridwidth=5;
		this.add(jlIt,col);
		
		col.gridx=0;
		col.gridy=11;
		col.gridwidth=5;
		this.add(jlAsuntos,col);
		
		col.gridx=0;
		col.gridy=12;
		col.gridwidth=5;
		this.add(jlCompensacion,col);
		
		col.gridx=0;
		col.gridy=13;
		col.gridwidth=5;
		this.add(jlOtrasLicencias,col);
		
		col.gridx=0;
		col.gridy=14;
		col.gridwidth=5;
		this.add(jlLibre,col);
		
		col.gridx=1;
		col.gridy=15;
		col.gridwidth=2;
		col.weightx=1;
		col.fill=GridBagConstraints.HORIZONTAL;
		this.add(jbCancelar,col);
		
		col.gridx=3;
		col.gridy=15;
		col.gridwidth=2;
		this.add(jbGrabar,col);
		
		jbCancelar.addActionListener(this);
		jbGrabar.addActionListener(this);
	}
	
	@SuppressWarnings({"unchecked" })
	private void RellenarComboEmpleados() {
		// TODO Auto-generated method stub
		EmpleadoDao ed=new EmpleadoDao();
		List<Empleado>empleados=ed.listaEmpleados();
		Iterator<Empleado> it=empleados.iterator();
		while (it.hasNext()){
			Empleado e= it.next();
			jcbEmpleados.addItem(e.getDni()+"//"+e.getPrimerApe()+", "+e.getNombre());			
		}		
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(jbCancelar)){
			this.dispose();
		}else{
			loggeador.debug("pulso grabar patron");
			//primero obtenemos la identificación del empleado
			String empleadoSeleccionado=jcbEmpleados.getSelectedItem().toString();
			StringTokenizer st=new StringTokenizer(empleadoSeleccionado,"//");
			EmpleadoDao ed=new EmpleadoDao();
			List<Empleado>lista=ed.listaEmpleadoPorDni(st.nextToken());
			Empleado em=lista.get(0);
			//necesitamos el conjunto de jornadas del empleado
			Set<Jornada>l=em.getJornadas();
			//extraemos fecha inicio y fecha fin
			Date fI=jdcFechaInicio.getDate();
			Date fF=jdcFechaFin.getDate();
			Calendar cI=Calendar.getInstance();
			cI.setTime(fI);
			cI.set(Calendar.HOUR_OF_DAY, 0);
			cI.set(Calendar.MINUTE, 0);
			cI.set(Calendar.SECOND,0);
			Calendar cF=Calendar.getInstance();
			cF.setTime(fF);
			//obtenemos el patron
			String patron=jtfPatron.getText().toUpperCase();
			int longitud=patron.length();
			int i=0;
			//recorremos las fechas desde el inicio hasta el final
			while(cI.before(cF)){		
				System.out.println("inicio " + cI.getTime());
				System.out.println("final " + cF.getTime());
				
				//creamos la jornada
				Jornada j =new Jornada();
				//de qué es la jornada?
				char tipoJornada=patron.charAt(i);
				j=ObtenerJornada(tipoJornada,cI,Integer.parseInt(jtfHoras.getText()),Integer.parseInt(jtfMinutos.getText()));
				
				
				//incluimos la jornada en el empleado
				l.add(j);
				//incrementamos i
				i++;
				//Si hemos llegado al final del patron ponemos i a cero
				if(i==longitud){
					i=0;
				}
				
				cI.set(Calendar.HOUR_OF_DAY, 0);
				cI.set(Calendar.MINUTE, 0);
				cI.set(Calendar.SECOND,0);
				cI.add(Calendar.DAY_OF_YEAR, 1);
				System.out.println("inicio " + cI.getTime());
			}
			em.setJornadas(l);
			try {
				ed.addEmpleado(em);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.dispose();
			
		}
	}

	private Jornada ObtenerJornada(char tipoJornada,Calendar fecha,int horas, int minutos) {
		Jornada j=new Jornada();
		System.out.println("inicio " + fecha.getTime());
		switch(tipoJornada){
		//Mañana
		case 'M':
			//hora de inicio las ocho de la mañana
			fecha.add(Calendar.HOUR, 8);			
			break;
		//Tarde
		case 'T':
			//hora de inicio las 16 horas
			fecha.add(Calendar.HOUR, 16);
			break;
		//Noche
		case 'N':
			//hora de inicio las 0 horas
			fecha.add(Calendar.HOUR, 0);
			break;
		//Partido
		case 'P':
			//hora de inicio las 10horas
			fecha.add(Calendar.HOUR, 10);
			break;
		//Vacaciones
		case 'V':
			//hora de inicio las 0 horas, 7 minutos
			fecha.add(Calendar.MINUTE,7);
			break;
		//IT
		case 'I':
			//hora de inicio las 0 horas, 8 minutos
			fecha.add(Calendar.MINUTE,13);
			break;
		//Asuntos propios
		case 'A':
			//hora de inicio las 0 horas, 9 minutos
			fecha.add(Calendar.MINUTE,9);
			break;
		//Asuntos propios
		case 'C':
			//hora de inicio las 0 horas, 10 minutos
			fecha.add(Calendar.MINUTE,14);
			break;
		//otras licencias
		case 'O':
			//hora de inicio las 0 horas, 11 minutos
			fecha.add(Calendar.MINUTE,11);
			break;
		case 'L':
			//hora de inicio las 0 horas, 12 minutos
			fecha.add(Calendar.MINUTE,12);
			break;
		}
		j.setInicioJornada(fecha.getTime());
		//Hora de fin las ocho más lo que dure
		fecha.add(Calendar.HOUR, horas);
		fecha.add(Calendar.MINUTE, minutos);
		if(tipoJornada=='T'){
			fecha.add(Calendar.HOUR, -1);
			fecha.add(Calendar.MINUTE, -1);
		}
		j.setFinJornada(fecha.getTime());
		return j;
	}

}

	