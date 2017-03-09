package com.Eu.paneles;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
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
import com.Eu.dao.ObservacionDao;
import com.Eu.model.Estancia;
import com.Eu.model.Interno;
import com.Eu.model.Observacion;

public class PanelEstancias extends JPanel implements ActionListener,TableModelListener { 	
	

	public JTable jtEstancias =new JTable();
	//botonero
	JPanel jpBotonero=new JPanel();
	JButton jbAnhadir =new JButton("Añadir");
	JButton jbBorrar=new JButton("Borrar");
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
		
		jbAnhadir.addActionListener(this);
		jbBorrar.addActionListener(this);
		
		jpBotonero.add(jbAnhadir);
		jpBotonero.add(jbBorrar);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx=0.25;
		gbc.weighty=0.25;
		gbc.anchor=GridBagConstraints.EAST;
		this.add(jpBotonero,gbc);
		
		
	}

	public void RellenarTablaEstancias(int id) {
		//renders
				MiRender miRender=new MiRender();		
						
				//el array con las cabeceras de la tabla
				String[]cabecerasTablaEstancias=new String[6];
				cabecerasTablaEstancias[0]="Id.Est.";
				cabecerasTablaEstancias[1]="Id.Int.";
				cabecerasTablaEstancias[2]="Fecha de alta";
				cabecerasTablaEstancias[3]="Tipo de estancia";
				cabecerasTablaEstancias[4]="Fecha de baja";
				cabecerasTablaEstancias[5]="Motivo de la baja";
						
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
				columna=jtEstancias.getColumn("Id.Int.");
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

	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		DefaultTableModel dtm=(DefaultTableModel)this.getJtEstancias().getModel();
//		if(e.getSource().equals(jbAnhadir)){
//			System.out.println("pulso añadir Estancia");
//			AltaEstancia ao=new AltaEstancia(this.getI(),dtm);
//			ao.setVisible(true);
//		}else{
//			System.out.println("pulso borrar estancia");
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


