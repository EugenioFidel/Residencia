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
	
	SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy"); 	
	
	JDateChooser jdcFechaInicio=new JDateChooser();
	JDateChooser jdcFechaFin=new JDateChooser();
	
	JLabel jlEmpleado=new JLabel("Empleado: ");
	JComboBox<String> jcbEmpleados=new JComboBox<String>();
	
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
		boolean bandera=false;
		switch(tipoJornada){
		//Mañana
		case 'M':
			j.setTipoJornada(MANHANA);			
			//hora de inicio las ocho de la mañana				
			fecha.add(Calendar.HOUR_OF_DAY, 8);
			break;
		//Tarde
		case 'T':
			j.setTipoJornada(TARDE);
			//hora de inicio las 16 horas
			fecha.add(Calendar.HOUR_OF_DAY, 16);
			break;
		//Noche
		case 'N':
			j.setTipoJornada(NOCHE);
			//hora de inicio las 0 horas
			break;
		//Partido
		case 'P':
			j.setTipoJornada(PARTIDO);
			//hora de inicio las 10horas
			fecha.add(Calendar.HOUR_OF_DAY, 10);
			break;
		//Vacaciones
		case 'V':
			j.setTipoJornada(VACACION);
			bandera=true;
			break;
		//IT
		case 'I':
			j.setTipoJornada(IT);
			bandera=true;
			break;
		//Asuntos propios
		case 'A':
			j.setTipoJornada(ASUNTOS);
			bandera=true;
			break;
		//Asuntos propios
		case 'C':
			j.setTipoJornada(COMPENSACION);
			bandera=true;
			break;
		//otras licencias
		case 'O':
			j.setTipoJornada(OTRAS);
			bandera=true;
			break;
		case 'L':
			j.setTipoJornada(LIBRE);
			bandera=true;
		}
		if(bandera){
			j.setInicioJornada(fecha.getTime());
			j.setFinJornada(fecha.getTime());
		}else{
			j.setInicioJornada(fecha.getTime());
			//Hora de fin las ocho más lo que dure
			fecha.add(Calendar.HOUR_OF_DAY, horas);
			fecha.add(Calendar.MINUTE, minutos);
			//ajustamos el final de la jornada restando un segundo
			fecha.add(Calendar.SECOND, -1);
			j.setFinJornada(fecha.getTime());
		}	
		
		return j;
	}

}

	