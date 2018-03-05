package com.Eu.paneles;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
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
import com.Eu.dao.ContratoDao;
import com.Eu.dao.EmpleadoDao;
import com.Eu.model.Categoria;
import com.Eu.model.Contrato;
import com.Eu.model.Empleado;
import com.Eu.model.Tipo_contrato;

public class PanelContratos extends JPanel implements ActionListener,TableModelListener { 		

	public JTable jtContratos =new JTable();
	public DefaultTableModel dtm=new DefaultTableModel();
	
	//Un JScrollPane para alojar la tabla 
	public JScrollPane jsp = new JScrollPane();	
	GridLayout gbl=new GridLayout(1,1);
	
	final static Logger loggeador = Logger.getLogger(PanelFiltros.class);
	
	Empleado e=new Empleado();	
	
	private static final long serialVersionUID = 1L;
	
	 //Popup sobre titulares
	JPopupMenu jpuMenuContextual=new JPopupMenu();	
	JSeparator jsSeparador1=new JSeparator();
	JSeparator jsSeparador2=new JSeparator();
	JMenuItem jmiInfContrato=new JMenuItem("Informe nuevo contrato");	
	
	Font fuente=new Font("Ubuntu",0,16);
	Font fuenteN=new Font("Ubuntu",1,16);
	
	String ps;

	public PanelContratos (int id,String ps){
		this.setPs(ps);
		EmpleadoDao ed=new EmpleadoDao();
		this.setE(ed.getEmpleadoById(id));		
		
		this.setSize(1010,880);
		this.setLayout(gbl);
		this.RellenarTablaContratos(id);	
		
		jpuMenuContextual.add(jsSeparador1);
		jpuMenuContextual.add(jmiInfContrato);
		jpuMenuContextual.add(jsSeparador2);
		
		jmiInfContrato.addActionListener(this);
		
		MouseListener popupListener=new PopupListener();
		jtContratos.addMouseListener(popupListener);
		jtContratos.setFont(new java.awt.Font("Tahoma", 0, 16));

		this.add(jsp);		
	}

	public void RellenarTablaContratos(int id) {	
		MiRender miRender=new MiRender();	
						
		//el array con las cabeceras de la tabla
		String[]cabecerasTablaEstancias=new String[8];
		cabecerasTablaEstancias[0]="Id";
		cabecerasTablaEstancias[1]="Fecha inicio";
		cabecerasTablaEstancias[2]="Fecha fin";
		cabecerasTablaEstancias[3]="H/s";
		cabecerasTablaEstancias[4]="Tipo contrato";
		cabecerasTablaEstancias[5]="Categoria profesional";
		cabecerasTablaEstancias[6]="Fecha comunicación";
		cabecerasTablaEstancias[7]="Empleado sustituido";		
						
		ContratoDao cd=new ContratoDao();		
		List<Object> contratos = cd.listaContratos(id);
		jtContratos=FuncionesDiversas.cargaDatosEnTablaContratos(contratos, cabecerasTablaEstancias);
		jtContratos.setFont(fuente);	
		
		TableColumnModel conjuntoColumnas=jtContratos.getColumnModel();
		for (int i=0;i<conjuntoColumnas.getColumnCount();i++){
			conjuntoColumnas.getColumn(i).setCellRenderer(miRender);
		}
				
		TableColumn columna=jtContratos.getColumn("Id");
		columna.setPreferredWidth(30);
		columna=jtContratos.getColumn("Fecha inicio");
		columna.setPreferredWidth(100);
		columna=jtContratos.getColumn("Fecha fin");			
		columna.setPreferredWidth(100);
		columna=jtContratos.getColumn("H/s");			
		columna.setPreferredWidth(30);
		columna=jtContratos.getColumn("Tipo contrato");
		columna.setPreferredWidth(250);
		columna=jtContratos.getColumn("Categoria profesional");
		columna.setPreferredWidth(170);
		columna=jtContratos.getColumn("Fecha comunicación");
		columna.setPreferredWidth(100);
		columna=jtContratos.getColumn("Empleado sustituido");
		columna.setPreferredWidth(230);
		
		MouseListener popupListener=new PopupListener();
		jtContratos.addMouseListener(popupListener);
		
		jtContratos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtContratos.doLayout();
		jtContratos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
				if(v.elementAt(6)!=null)
					c.setFechaComunicacion(dt.parse((String)v.elementAt(6)));
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
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(jmiInfContrato))
			loggeador.debug("evento en el menu contextual");
			Map<String, Object> parametros=new HashMap<String, Object>();			
			parametros.put("idContrato",dtm.getValueAt(jtContratos.getSelectedRow(), 0));
			String fechaF="";
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			if(dtm.getValueAt(jtContratos.getSelectedRow(), 2)!=null){
				try {				
					fechaF=sdf.format(sdf.parse(dtm.getValueAt(jtContratos.getSelectedRow(), 2).toString()));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				fechaF = JOptionPane.showInputDialog(this,"<html><font size=+1>Fecha prevista de fin de contrato: </font size></html>", JOptionPane.QUESTION_MESSAGE); 
				try {				
					fechaF=sdf.format(sdf.parse(fechaF));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			parametros.put("fechaFinPrevista", fechaF);
			parametros.put("empSustituido", dtm.getValueAt(jtContratos.getSelectedRow(), 7));
			int resultado=FuncionesDiversas.GenerarInformeContratoSustitucion(parametros,this.getPs());	
			if (resultado==1){
				Calendar fechaActual=Calendar.getInstance();
				String idContrato=dtm.getValueAt(jtContratos.getSelectedRow(), 0).toString();
				int id=Integer.parseUnsignedInt(idContrato);
				ContratoDao cd =new ContratoDao();
				Contrato co=cd.getContratoById(id);
				co.setFechaComunicacion(fechaActual.getTime());
				cd.addContrato(co);				
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
	
	

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}



	class PopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
		showPopup(e);
			    }
			    public void mouseReleased(MouseEvent e) {
			        showPopup(e);
			      }	
			    private void showPopup(MouseEvent e) {
			        if (e.isPopupTrigger()) {
			        	
			        	if(e.getSource().equals(jtContratos)){
			        		jpuMenuContextual.show(e.getComponent(), e.getX(), e.getY());
			        	}		        	
			        }
			      }	  	     
	}	
}
