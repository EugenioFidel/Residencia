package com.Eu.paneles;

import java.awt.Color;

import javax.swing.JPanel;

public class PanelEstancias extends JPanel { 	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 693008077445915091L;

	public PanelEstancias(){
		this.setSize(900,900);
		
		this.setBackground(Color.green);
	}
//	public JTabbedPane jtp=new JTabbedPane();
//	public JPanel jpObservaciones=new JPanel();
//	public JPanel jpEstancias=new JPanel();
//	public JTable jtObservaciones =new JTable();
//	//botonero
//	JPanel jpBotonero=new JPanel();
//	JButton jbAnhadir =new JButton("Añadir");
//	JButton jbBorrar=new JButton("Borrar");
//	//Un JScrollPane para alojar la tabla 
//	public JScrollPane jsp = new JScrollPane();	
//	GridBagLayout gbl=new GridBagLayout();
//	GridBagConstraints gbc=new GridBagConstraints();
//	
//	final static Logger loggeador = Logger.getLogger(PanelFiltros.class);
//	
//	
//	private static final long serialVersionUID = 1L;
//
//	public PanelObservaciones(int id){
//		
//		this.setLayout(gbl);
////		this.setBackground(Color.BLUE);
//		RellenarTablaObservaciones(id);
//		
//		jsp.setViewportView(jtObservaciones);
//		
//		jtp.add(jsp, "Observaciones");
//		jtp.add(jpEstancias,"Estancias");
//		gbc.gridx=0;
//		gbc.gridy=0;
//		gbc.weightx=1.0;
//		gbc.weighty=1.0;
//		gbc.fill=GridBagConstraints.BOTH;
//		this.add(jtp,gbc);
//		
//		jbAnhadir.addActionListener(this);
//		jbBorrar.addActionListener(this);
//		
//		jpBotonero.add(jbAnhadir);
//		jpBotonero.add(jbBorrar);
//		
//		gbc.gridx=0;
//		gbc.gridy=1;
//		gbc.weightx=0.25;
//		gbc.weighty=0.25;
//		gbc.anchor=GridBagConstraints.EAST;
//		this.add(jpBotonero,gbc);
//		
//	}
//
//	private void RellenarTablaObservaciones(int id) {
//		//renders
//				MiRender miRender=new MiRender();		
//						
//				//el array con las cabeceras de la tabla
//				String[]cabecerasTablaObservaciones=new String[8];
//				cabecerasTablaObservaciones[0]="Id.";
//				cabecerasTablaObservaciones[1]="Fecha observación";
//				cabecerasTablaObservaciones[2]="Alimentación";
//				cabecerasTablaObservaciones[3]="Movilidad";
//				cabecerasTablaObservaciones[4]="Vestido";
//				cabecerasTablaObservaciones[5]="Inodoro";
//				cabecerasTablaObservaciones[6]="Esfínteres";
//				cabecerasTablaObservaciones[7]="Gr.dependencia";	
//						
//				ObservacionDao od=new ObservacionDao();		
//				List<Observacion> observaciones = od.listaObservaciones(id);
//				jtObservaciones=FuncionesDiversas.cargaDatosEnTablaObservaciones(observaciones, cabecerasTablaObservaciones);
//				System.out.println("filas en tabla observaciones"+jtObservaciones.getRowCount());	
//						
//				TableColumnModel conjuntoColumnas=jtObservaciones.getColumnModel();
//				for (int i=0;i<conjuntoColumnas.getColumnCount();i++){
//					conjuntoColumnas.getColumn(i).setCellRenderer(miRender);
//				}
//						
//				TableColumn columna=jtObservaciones.getColumn("Id.");
//				columna.setPreferredWidth(30);
//				columna=jtObservaciones.getColumn("Fecha observación");
//				columna.setPreferredWidth(130);
//				columna=jtObservaciones.getColumn("Alimentación");
//				columna.setPreferredWidth(105);
//				columna=jtObservaciones.getColumn("Movilidad");			
//				columna.setPreferredWidth(105);
//				columna=jtObservaciones.getColumn("Vestido");
//				columna.setPreferredWidth(105);
//				columna=jtObservaciones.getColumn("Inodoro");
//				columna.setPreferredWidth(105);
//				columna=jtObservaciones.getColumn("Esfínteres");
//				columna.setPreferredWidth(105);
//				columna=jtObservaciones.getColumn("Gr.dependencia");
//				columna.setPreferredWidth(110);
//				
//				jtObservaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//				jtObservaciones.doLayout();
//				this.setJtObservaciones(jtObservaciones);
//						
//				TableModel dtm=jtObservaciones.getModel();
//				dtm.addTableModelListener(this);
//						
//				
//		
//	}
//
//	public JTable getJtObservaciones() {
//		return jtObservaciones;
//	}
//
//	public void setJtObservaciones(JTable jt) {
//		this.jtObservaciones = jt;
//	}
//
//	@SuppressWarnings("deprecation")
//	public void tableChanged(TableModelEvent e) {
//		// TODO Auto-generated method stub
//		if(e.getType()==TableModelEvent.UPDATE){
//			//Editando la tabla
//			loggeador.info("editando una observacion");
//			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//			Observacion o=new Observacion();
//			DefaultTableModel modelo=(DefaultTableModel) e.getSource();
//			@SuppressWarnings("unchecked")
//			Vector<Object> v=(Vector<Object>) modelo.getDataVector().elementAt(e.getFirstRow());
//			o.setIdObservacion((Integer) v.elementAt(0));
//			try {
//				o.setFechaObservacion(formatter.parse((String)v.elementAt(1)));
//			} catch (ParseException e2) {
//				// TODO Auto-generated catch block
//				loggeador.info(e2);
//			}			
//			o.setAlimentacion(v.elementAt(2).toString());
//			o.setMovilidad(v.elementAt(3).toString());
//			o.setVestido(v.elementAt(4).toString());
//			o.setInodoro( v.elementAt(5).toString());
//			o.setEsfinteres(v.elementAt(6).toString());
//			if(o.getAlimentacion().equals("INDEPENDIENTE") && o.getMovilidad().equals("INDEPENDIENTE") && o.getVestido().equals("INDEPENDIENTE") && o.getInodoro().equals("INDEPENDIENTE") && o.getEsfinteres().equals("INDEPENDIENTE")){
//				o.setGradoDependencia("AG2");
//			}else if(o.getAlimentacion().equals("INDEPENDIENTE") || o.getMovilidad().equals("INDEPENDIENTE") || o.getVestido().equals("INDEPENDIENTE") || o.getInodoro().equals("INDEPENDIENTE") || o.getEsfinteres().equals("INDEPENDIENTE")){
//				o.setGradoDependencia("AG1");
//			}else{
//				o.setGradoDependencia("VALIDO");	
//			}
//			
//			ObservacionDao od=new ObservacionDao();
//			
//			od.updateObservacion(o);
//			
//
//		}else if(e.getType()==TableModelEvent.DELETE){
//			//Borrando una observación
//			System.out.println("Borrando una observación");
//		}
//	}
//
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		DefaultTableModel dtm=(DefaultTableModel)this.getJtObservaciones().getModel();
//		if(e.getSource().equals(jbAnhadir)){
//			System.out.println("pulso añadir observación");
//			Object[]fila={"","","","","","",""};
//			dtm.addRow(fila);
//		}else{
//			System.out.println("pulso borrar observación");
//		}
//		
//		
//	}	
	
}

