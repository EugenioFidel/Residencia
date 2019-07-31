package com.Eu.paneles;

import java.awt.Font;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.Eu.controladores.FuncionesDiversas;
import com.Eu.controladores.MiRender;

import com.Eu.dao.InternoDao;
import com.Eu.dao.ObservacionDao;

import com.Eu.model.Interno;
import com.Eu.model.Observacion;

public class PanelObservaciones extends JPanel implements TableModelListener { 
	
	private final int ALTOFILA=20;
	
	public JTable jtObservaciones =new JTable();
	public DefaultTableModel dtm=new DefaultTableModel();	

	//Un JScrollPane para alojar la tabla 
	public JScrollPane jsp = new JScrollPane();	
	GridLayout gbl=new GridLayout(1,1);
	
	final static Logger loggeador = Logger.getLogger(PanelFiltros.class);
	
	Interno i=new Interno();	
	
	private static final long serialVersionUID = 1L;
	
	Font fuente=new Font("Ubuntu",Font.PLAIN,16);
	Font fuenteN=new Font("Ubuntu",Font.BOLD,16);

	public PanelObservaciones(int id){
		InternoDao i=new InternoDao();
		this.setI(i.getInternoById(id));		
		
		this.setSize(1010,880);
		this.setLayout(gbl);
		this.RellenarTablaObservaciones(id);
		this.add(jsp);		
	}

	public void RellenarTablaObservaciones(int id) {
		//renders
		MiRender miRender=new MiRender();		
						
		//el array con las cabeceras de la tabla
		String[]cabecerasTablaObservaciones=new String[9];
		cabecerasTablaObservaciones[0]="Id.";
		cabecerasTablaObservaciones[1]="Fecha";
		cabecerasTablaObservaciones[2]="Alimentación";
		cabecerasTablaObservaciones[3]="Movilidad";
		cabecerasTablaObservaciones[4]="Aseo";
		cabecerasTablaObservaciones[5]="Vestido";
		cabecerasTablaObservaciones[6]="Inodoro";
		cabecerasTablaObservaciones[7]="Esfínteres";
		cabecerasTablaObservaciones[8]="Gr.dep.";	
						
		ObservacionDao od=new ObservacionDao();		
		List<Observacion> observaciones = od.listaObservaciones(id);
		jtObservaciones=FuncionesDiversas.cargaDatosEnTablaObservaciones(observaciones, cabecerasTablaObservaciones);
		jtObservaciones.setFont(fuente);
		jtObservaciones.setRowHeight(ALTOFILA);

		
		TableColumnModel conjuntoColumnas=jtObservaciones.getColumnModel();
		for (int i=0;i<conjuntoColumnas.getColumnCount();i++){
			conjuntoColumnas.getColumn(i).setCellRenderer(miRender);
		}
						
		TableColumn columna=jtObservaciones.getColumn("Id.");
		columna.setPreferredWidth(50);
		columna=jtObservaciones.getColumn("Fecha");
		columna.setPreferredWidth(160);
		columna=jtObservaciones.getColumn("Alimentación");
		columna.setPreferredWidth(120);
		columna=jtObservaciones.getColumn("Movilidad");			
		columna.setPreferredWidth(120);
		columna=jtObservaciones.getColumn("Aseo");			
		columna.setPreferredWidth(120);
		columna=jtObservaciones.getColumn("Vestido");
		columna.setPreferredWidth(120);
		columna=jtObservaciones.getColumn("Inodoro");
		columna.setPreferredWidth(120);
		columna=jtObservaciones.getColumn("Esfínteres");
		columna.setPreferredWidth(120);
		columna=jtObservaciones.getColumn("Gr.dep.");
		columna.setPreferredWidth(80);
			
		jtObservaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtObservaciones.doLayout();
		jtObservaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setJtObservaciones(jtObservaciones);
					
		TableModel dtm=jtObservaciones.getModel();
		dtm.addTableModelListener(this);		
				
		jsp.setViewportView(jtObservaciones);
				
		this.setDtm((DefaultTableModel)jtObservaciones.getModel());
	}

	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		if(e.getType()==TableModelEvent.UPDATE){
			//Editando la tabla
			loggeador.info("editando una observacion");
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Observacion o=new Observacion();
			DefaultTableModel modelo=(DefaultTableModel) e.getSource();
			@SuppressWarnings("unchecked")
			Vector<Object> v=(Vector<Object>) modelo.getDataVector().elementAt(e.getFirstRow());
			o.setIdObservacion((Integer) v.elementAt(0));
			try {
				o.setFechaObservacion(formatter.parse((String)v.elementAt(1)));
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				loggeador.info(e2);
			}
			
			o.setAlimentacion(v.elementAt(2).toString());
			o.setMovilidad(v.elementAt(3).toString());
			o.setAseo(v.elementAt(4).toString());
			o.setVestido(v.elementAt(5).toString());
			o.setInodoro( v.elementAt(6).toString());
			o.setEsfinteres(v.elementAt(7).toString());
			FuncionesDiversas.evaluarGradoDependencia(o);
			
			ObservacionDao od=new ObservacionDao();			
			od.updateObservacion(o);		

		}else if(e.getType()==TableModelEvent.DELETE){
			//Borrando una observación
			System.out.println("Borrando una observación");
		}
	}

	public JTable getJtObservaciones() {
		return jtObservaciones;
	}

	public void setJtObservaciones(JTable jtObservaciones) {
		this.jtObservaciones = jtObservaciones;
	}

	public JScrollPane getJsp() {
		return jsp;
	}

	public void setJsp(JScrollPane jsp) {
		this.jsp = jsp;
	}	
	
	public Interno getI() {
		return i;
	}

	public void setI(Interno i) {
		this.i = i;
	}
	
	public void redibujar(){
		jsp.setViewportView(jtObservaciones);		
	}	

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}
}

