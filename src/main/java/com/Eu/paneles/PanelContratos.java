package com.Eu.paneles;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.Eu.controladores.FuncionesDiversas;
import com.Eu.controladores.MiRender;
import com.Eu.dao.ContratoDao;
import com.Eu.dao.EmpleadoDao;
import com.Eu.model.Categoria;
import com.Eu.model.Contrato;
import com.Eu.model.Empleado;
import com.Eu.model.Tipo_contrato;


public class PanelContratos extends JPanel implements TableModelListener { 		

	public JTable jtContratos =new JTable();
	public DefaultTableModel dtm=new DefaultTableModel();
	
	//Un JScrollPane para alojar la tabla 
	public JScrollPane jsp = new JScrollPane();	
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	
	final static Logger loggeador = Logger.getLogger(PanelFiltros.class);
	
	Empleado e=new Empleado();	
	
	private static final long serialVersionUID = 1L;
	
	

	public PanelContratos(int id){
		EmpleadoDao ed=new EmpleadoDao();
		this.setE(ed.getEmpleadoById(id));		
		
		this.setSize(800,600);
		this.setLayout(gbl);
		this.RellenarTablaContratos(id);		
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.fill=GridBagConstraints.BOTH;
		this.add(jsp,gbc);
		
	}

	public void RellenarTablaContratos(int id) {	
		MiRender miRender=new MiRender();	
						
		//el array con las cabeceras de la tabla
		String[]cabecerasTablaEstancias=new String[7];
		cabecerasTablaEstancias[0]="Id";
		cabecerasTablaEstancias[1]="Fecha inicio";
		cabecerasTablaEstancias[2]="Fecha fin";
		cabecerasTablaEstancias[3]="H/s";
		cabecerasTablaEstancias[4]="Tipo contrato";
		cabecerasTablaEstancias[5]="Categoria profesional";
		cabecerasTablaEstancias[6]="Empleado sustituido";
		
						
		ContratoDao cd=new ContratoDao();		
		List<Object> contratos = cd.listaContratos(id);
		jtContratos=FuncionesDiversas.cargaDatosEnTablaContratos(contratos, cabecerasTablaEstancias);
		System.out.println("filas en tabla contratos"+jtContratos.getRowCount());	
						
		TableColumnModel conjuntoColumnas=jtContratos.getColumnModel();
		for (int i=0;i<conjuntoColumnas.getColumnCount();i++){
			conjuntoColumnas.getColumn(i).setCellRenderer(miRender);
		}
				
		TableColumn columna=jtContratos.getColumn("Id");
		columna.setPreferredWidth(25);
		columna=jtContratos.getColumn("Fecha inicio");
		columna.setPreferredWidth(80);
		columna=jtContratos.getColumn("Fecha fin");			
		columna.setPreferredWidth(80);
		columna=jtContratos.getColumn("H/s");			
		columna.setPreferredWidth(25);
		columna=jtContratos.getColumn("Tipo contrato");
		columna.setPreferredWidth(175);
		columna=jtContratos.getColumn("Categoria profesional");
		columna.setPreferredWidth(175);
		columna=jtContratos.getColumn("Empleado sustituido");
		columna.setPreferredWidth(237);
		
		jtContratos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtContratos.doLayout();
		this.setJtContratos(jtContratos);
					
		TableModel dtm=jtContratos.getModel();
		dtm.addTableModelListener(this);		
		
		jsp.setViewportView(jtContratos);
		
		this.setDtm((DefaultTableModel)jtContratos.getModel());
	}


	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
		@SuppressWarnings("unchecked")
		Vector<Object> v=(Vector<Object>) dtm.getDataVector().elementAt(e.getFirstRow());
		ContratoDao cd=new ContratoDao();			
		Contrato c=cd.getContratoById(Integer.parseInt(v.elementAt(0).toString()));		
		if(e.getType()==TableModelEvent.UPDATE){
			//Editando un contrato
			loggeador.debug("editando un contrato");				
			try {
				if(v.elementAt(1)!=null)
					c.setFechaInicio(dt.parse((String)v.elementAt(1)));
				if(v.elementAt(2)!=null)
					c.setFechaFin(dt.parse((String)v.elementAt(2)));				
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				loggeador.debug(e2);
			}
			Tipo_contrato tc=new Tipo_contrato();
			tc.setTipoContrato(v.elementAt(4).toString());
			c.setTipoContrato(tc);
			Categoria cat=new Categoria();
			cat.setCategoria(v.elementAt(5).toString());
			c.setCategoria(cat);
			c.setHoras(Integer.parseInt(v.elementAt(3).toString()));
			cd.addContrato(c);
		}else if(e.getType()==TableModelEvent.DELETE){
			//Borrando un contrato
			loggeador.debug("Borrando un contrato");				
			cd.deleteContrato(c);
		}
	
	}


	public JScrollPane getJsp() {
		return jsp;
	}

	public void setJsp(JScrollPane jsp) {
		this.jsp = jsp;
	}	
	
	public Empleado getE() {
		return e;
	}

	public void setE(Empleado e) {
		this.e = e;
	}
	
	public void redibujar(){
		jsp.setViewportView(jtContratos);		
	}
	

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}	

	public JTable getJtContratos() {
		return jtContratos;
	}

	public void setJtContratos(JTable jtContratos) {
		this.jtContratos = jtContratos;
	}
	
}
