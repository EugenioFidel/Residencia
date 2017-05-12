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
import com.Eu.dao.EstanciaDao;
import com.Eu.dao.InternoDao;
import com.Eu.model.Estancia;
import com.Eu.model.Interno;

public class PanelEstancias extends JPanel implements TableModelListener { 		

	public JTable jtEstancias =new JTable();
	public DefaultTableModel dtm=new DefaultTableModel();
	
	//Un JScrollPane para alojar la tabla 
	public JScrollPane jsp = new JScrollPane();	
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	
	final static Logger loggeador = Logger.getLogger(PanelFiltros.class);
	
	Interno i=new Interno();	
	
	private static final long serialVersionUID = 1L;
	
	static final int RESIDENCIAL=1;
	static final int CENTRO_DIA=2;
	
	static final int EN_ALTA=0;
	static final int EXITUS=1;
	static final int A_DOMICILIO=2;
	static final int A_OTRA_RESIDENCIA=3;

	public PanelEstancias(int id){
		InternoDao i=new InternoDao();
		this.setI(i.getInternoById(id));		
		
		this.setSize(800,600);
		this.setLayout(gbl);
		this.RellenarTablaEstancias(id);		
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.fill=GridBagConstraints.BOTH;
		this.add(jsp,gbc);		
	}

	public void RellenarTablaEstancias(int id) {	
		MiRender miRender=new MiRender();	
						
		//el array con las cabeceras de la tabla
		String[]cabecerasTablaEstancias=new String[5];
		cabecerasTablaEstancias[0]="Id.Est.";
		cabecerasTablaEstancias[1]="Fecha de alta";
		cabecerasTablaEstancias[2]="Tipo de estancia";
		cabecerasTablaEstancias[3]="Fecha de baja";
		cabecerasTablaEstancias[4]="Motivo de la baja";
						
		EstanciaDao ed=new EstanciaDao();		
		List<Estancia> estancias = ed.listaEstancias(id);
		jtEstancias=FuncionesDiversas.cargaDatosEnTablaEstancias(estancias, cabecerasTablaEstancias);
		System.out.println("filas en tabla estancias"+jtEstancias.getRowCount());	
						
		TableColumnModel conjuntoColumnas=jtEstancias.getColumnModel();
		for (int i=0;i<conjuntoColumnas.getColumnCount();i++){
			conjuntoColumnas.getColumn(i).setCellRenderer(miRender);
		}
						
		TableColumn columna=jtEstancias.getColumn("Id.Est.");
		columna.setPreferredWidth(50);
		columna=jtEstancias.getColumn("Fecha de alta");
		columna.setPreferredWidth(150);
		columna=jtEstancias.getColumn("Tipo de estancia");			
		columna.setPreferredWidth(200);
		columna=jtEstancias.getColumn("Fecha de baja");			
		columna.setPreferredWidth(150);
		columna=jtEstancias.getColumn("Motivo de la baja");
		columna.setPreferredWidth(245);
				
		jtEstancias.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtEstancias.doLayout();
		this.setJtEstancias(jtEstancias);
					
		TableModel dtm=jtEstancias.getModel();
		dtm.addTableModelListener(this);		
		
		jsp.setViewportView(jtEstancias);
		
		this.setDtm((DefaultTableModel)jtEstancias.getModel());
	}

	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		if(e.getType()==TableModelEvent.UPDATE){
			//Editando la tabla
			loggeador.info("editando una estancia");
			SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
			Estancia es=new Estancia();
			@SuppressWarnings("unchecked")
			Vector<Object> v=(Vector<Object>) dtm.getDataVector().elementAt(e.getFirstRow());
			es.setIdEstancia((Integer) v.elementAt(0));
			try {
				es.setFechaAlta(dt.parse((String)v.elementAt(1)));
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				loggeador.info(e2);
			}
			
			String tipoEstancia=v.elementAt(2).toString();
			if(tipoEstancia.equals("Residencial")){
				es.setTipoEstancia(RESIDENCIAL);
			}else{
				es.setTipoEstancia(CENTRO_DIA);
			}
			if(v.elementAt(3)==null){
				es.setFechaBaja(null);
			}else{
				try {
					es.setFechaBaja(dt.parse(v.elementAt(3).toString()));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					loggeador.debug(e1);
				}
			}
			
			String motivoBaja=v.elementAt(4).toString();
			if(motivoBaja.equals("En alta")){
				es.setMotivoBaja(EN_ALTA);
			}else if(motivoBaja.equals("Exitus")){
				es.setMotivoBaja(EXITUS);
			}else if(motivoBaja.equals("Salida a su domicilio")){
				es.setMotivoBaja(A_DOMICILIO);
			}else{
				es.setMotivoBaja(A_OTRA_RESIDENCIA);
			}			
			
			EstanciaDao ed=new EstanciaDao();			
			ed.updateEstancia(es);			

		}else if(e.getType()==TableModelEvent.DELETE){
			//Borrando una observación
			System.out.println("Borrando una Estancia");
		}
	}

	public JTable getJtEstancias() {
		return jtEstancias;
	}

	public void setJtEstancias(JTable jtEstancias) {
		this.jtEstancias = jtEstancias;
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
		jsp.setViewportView(jtEstancias);		
	}
	

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}	
}


