package com.Eu.paneles;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
	
	//Un JScrollPane para alojar la tabla 
	public JScrollPane jsp = new JScrollPane();	
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	
	final static Logger loggeador = Logger.getLogger(PanelFiltros.class);
	
	Interno i=new Interno();	
	
	private static final long serialVersionUID = 1L;

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
		//renders
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
				columna.setPreferredWidth(30);
				columna=jtEstancias.getColumn("Fecha de alta");
				columna.setPreferredWidth(105);
				columna=jtEstancias.getColumn("Tipo de estancia");			
				columna.setPreferredWidth(105);
				columna=jtEstancias.getColumn("Fecha de baja");			
				columna.setPreferredWidth(105);
				columna=jtEstancias.getColumn("Motivo de la baja");
				columna.setPreferredWidth(105);
				
				jtEstancias.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				jtEstancias.doLayout();
				this.setJtEstancias(jtEstancias);
					
				TableModel dtm=jtEstancias.getModel();
				dtm.addTableModelListener(this);		
				
				jsp.setViewportView(jtEstancias);
	}

	public void tableChanged(TableModelEvent e) {
//		// TODO Auto-generated method stub
//		if(e.getType()==TableModelEvent.UPDATE){
//			//Editando la tabla
//			loggeador.info("editando una estancia");
//			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//			Estancia es=new Estancia();
//			DefaultTableModel modelo=(DefaultTableModel) e.getSource();
//			@SuppressWarnings("unchecked")
//			Vector<Object> v=(Vector<Object>) modelo.getDataVector().elementAt(e.getFirstRow());
//			es.setIdEstancia((Integer) v.elementAt(0));
//			try {
//				es.setFechaAlta(formatter.parse((String)v.elementAt(1)));
//			} catch (ParseException e2) {
//				// TODO Auto-generated catch block
//				loggeador.info(e2);
//			}
//			
//			es.setTipoEstancia(v.elementAt(2).toString());
//			es.setMovilidad(v.elementAt(3).toString());
//			es.setAseo(v.elementAt(4).toString());
//			es.setVestido(v.elementAt(5).toString());
//			es.setInodoro( v.elementAt(6).toString());
//			es.setEsfinteres(v.elementAt(7).toString());
//			
//			EstanciaDao ed=new EstanciaDao();			
//			ed.updateEstancia(es);
			

//		}else if(e.getType()==TableModelEvent.DELETE){
//			//Borrando una observación
//			System.out.println("Borrando una observación");
//		}
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
	
}


