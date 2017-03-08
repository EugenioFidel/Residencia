package com.Eu.controladores;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import com.Eu.dao.PersonaDao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import com.Eu.model.Empleado;
import com.Eu.model.Interno;
import com.Eu.model.Observacion;
import com.Eu.model.Persona;

public class FuncionesDiversas {	
	//Un TableRowSorter para ordenar y filtrar la tabla
	static TableRowSorter <TableModel>elQueOrdena=null;
	final static Logger loggeador = Logger.getLogger(FuncionesDiversas.class);
			
	public static JTable cargaDatosEnTablaPersonas(List<Object> lista,String[] cabeceras){				
		
		DefaultTableModel dtm=new DefaultTableModel();	
			for (int i = 0; i < cabeceras.length; i++) 
				dtm.addColumn(cabeceras[i]);		
				
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lista.iterator(); iterator.hasNext();){
			Persona pe = (Persona) iterator.next(); 
			Object fila[]=new Object[cabeceras.length];
			fila[0]=pe.getLetraCif()+"-"+pe.getDni()+"-"+pe.getLetraNif();
			fila[1]=pe.getPrimerApe()+" "+pe.getSegundoApe()+", "+pe.getNombre();
			fila[2]=pe.getDireccion();
			fila[3]=pe.getLocalidad();
			fila[4]=pe.getMunicipio();
			fila[5]=pe.getCp();
			fila[6]=pe.getProvincia();
			fila[7]=pe.getTelefono1()+" / "+pe.getTelefono2();
			fila[8]=pe.getEmail();
			dtm.addRow(fila);
		}
	
		//Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena=new TableRowSorter<TableModel>(dtm);
//		dtm.addColumn("");
		JTable tb =new JTable(dtm);
//		positionColumn(tb, 0);
		
		tb.setRowSorter(elQueOrdena);
			
			return tb;
			
	}	
	public static JTable cargaDatosEnTablaEmpleados(List<Object> lista,String[] cabeceras){		
		
		
		DefaultTableModel dtm=new DefaultTableModel();	
			for (int i = 0; i < cabeceras.length; i++) 
				dtm.addColumn(cabeceras[i]);		
				
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lista.iterator(); iterator.hasNext();){
			Empleado e = (Empleado) iterator.next(); 
			Object fila[]=new Object[cabeceras.length];
			fila[0]=e.getLetraCif()+"-"+e.getDni()+"-"+e.getLetraNif();
			fila[1]=e.getPrimerApe()+" "+e.getSegundoApe()+", "+e.getNombre();
			fila[2]=e.getDireccion();
			fila[3]=e.getLocalidad();
			fila[4]=e.getMunicipio();
			fila[5]=e.getCp();
			fila[6]=e.getProvincia();
			fila[7]=e.getTelefono1()+" / "+e.getTelefono2();
			fila[8]=e.getEmail();
			fila[9]=e.getCc();
			fila[10]=e.getSs();
			
			dtm.addRow(fila);
		}
	
		//Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena=new TableRowSorter<TableModel>(dtm);
		JTable tb =new JTable(dtm);
		
		tb.setRowSorter(elQueOrdena);
			
			return tb;
			
	}	
	
public static JTable cargaDatosEnTablaInternos(List<Object> lista,String[] cabeceras){		
		
		
		DefaultTableModel dtm=new DefaultTableModel();	
			for (int i = 0; i < cabeceras.length; i++) 
				dtm.addColumn(cabeceras[i]);		
				
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lista.iterator(); iterator.hasNext();){
			Interno i = (Interno) iterator.next(); 
			Object fila[]=new Object[cabeceras.length];
			fila[0]=i.getLetraCif()+"-"+i.getDni()+"-"+i.getLetraNif();
			fila[1]=i.getPrimerApe()+" "+i.getSegundoApe()+", "+i.getNombre();
			fila[2]=i.getDireccion();
			fila[3]=i.getLocalidad();
			fila[4]=i.getMunicipio();
			fila[5]=i.getCp();
			fila[6]=i.getProvincia();
			fila[7]=i.getTelefono1()+" / "+i.getTelefono2();
			fila[8]=i.getEmail();
			fila[9]=i.getCc();
			fila[10]=i.getSs();
			
			PersonaDao pd =new PersonaDao();
			List<Object>lp=pd.listaPersonasPorDni(i.getDniResponsable());
			Persona p=(Persona)lp.get(0);
			fila[11]=p.getNombre()+" "+p.getPrimerApe()+" "+p.getSegundoApe();
			
			dtm.addRow(fila);
		}
	
		//Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena=new TableRowSorter<TableModel>(dtm);
		JTable tb =new JTable(dtm);
		
		tb.setRowSorter(elQueOrdena);
			
			return tb;
			
	}	
	
	public static JTable cargaDatosEnTablaObservaciones(List<Observacion> lista,String[] cabeceras){
		/*
		 * creamos un DefaultTableModel dft y con el una JTable jt.
		 */
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy"); 	
		
		DefaultTableModel dtm=new DefaultTableModel();	
			for (int i = 0; i < cabeceras.length; i++) 
				dtm.addColumn(cabeceras[i]);		
				
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lista.iterator(); iterator.hasNext();){
			Object[] fila = (Object[]) iterator.next();
			Object filaB[]=new Object[fila.length];
			filaB[0]=fila[0];
			for(int i=1;i<fila.length;i++){
				if(i==1){
					filaB[i]=dt.format(fila[i]);
				}else{
					filaB[i]=fila[i];	
				}
			}
			dtm.addRow(filaB);
		}
	
		//Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena=new TableRowSorter<TableModel>(dtm);
//		dtm.addColumn("");
		JTable tb =new JTable(dtm);
//		positionColumn(tb, 0);
		
		tb.setRowSorter(elQueOrdena);
			
		return tb;
	}
	
	public static void positionColumn(JTable table,int col_Index) {
		table.moveColumn(table.getColumnCount()-1, col_Index);
	}
	
	public static void generarReporte(JasperReport reporte,
			Map<String, Object> params,
			java.sql.Connection conec,
			String nomFichero) {
		JasperPrint print;
					
			//un Calendar para la fecha del sistema
			Calendar fechaActual=Calendar.getInstance();			
			try {
				print = JasperFillManager.fillReport(reporte,params,conec);
				nomFichero=nomFichero+
					Integer.toString(fechaActual.get(Calendar.DATE))
					+"_"+Integer.toString(fechaActual.get(Calendar.MONTH)+1)
					+"_"+Integer.toString(fechaActual.get(Calendar.YEAR))+
					" time "+Integer.toString(fechaActual.get(Calendar.HOUR))+
					"h"+Integer.toString(fechaActual.get(Calendar.MINUTE))+"''.pdf";
				JasperExportManager.exportReportToPdfFile(print,nomFichero);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MostrarEnVisorPDF(nomFichero);		
		}
	
	
		public static void MostrarEnVisorPDF(String nomFichero){
			nomFichero=nomFichero.replace("./","");
			try {
				File path = new File (nomFichero);
				Desktop.getDesktop().open(path);
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		public static void GenerarListadoPersonas() {
			try {
				//conexion para el reporte
				dbConexion con=new dbConexion();
				java.sql.Connection conec=con.getConexion();
				JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfPersonas.jrxml");
				generarReporte(reportListado,null,conec,"./informes/ListadoPersonas_");
				conec.close();
				con.desconectar();
			} catch (JRException e1) {
				System.out.println("Error en la generación del listado");
				e1.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public static void GenerarListadoTelefonos() {
			try {
				//conexion para el reporte
				dbConexion con=new dbConexion();
				java.sql.Connection conec=con.getConexion();
				JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfTelefonos.jrxml");
				generarReporte(reportListado,null,conec,"./informes/ListadoTelefonos_");
				conec.close();
				con.desconectar();
			} catch (JRException e1) {
				System.out.println("Error en la generación del listado");
				e1.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public static void GenerarListadoInternos() {
			try {
				//conexion para el reporte
				dbConexion con=new dbConexion();
				java.sql.Connection conec=con.getConexion();
				JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfResidentes.jrxml");
				generarReporte(reportListado,null,conec,"./informes/ListadoResidentes_");
				conec.close();
				con.desconectar();
			} catch (JRException e1) {
				System.out.println("Error en la generación del listado");
				e1.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public static void GenerarListadoEmpleados() {
			try {
				//conexion para el reporte
				dbConexion con=new dbConexion();
				java.sql.Connection conec=con.getConexion();
				JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfEmpleados.jrxml");
				generarReporte(reportListado,null,conec,"./informes/ListadoEmpleados_");
				conec.close();
				con.desconectar();
			} catch (JRException e1) {
				System.out.println("Error en la generación del listado");
				e1.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
		
		public static void LimpiarDtm(DefaultTableModel modelo)
	    {
	        int filas = modelo.getRowCount();
	        if (filas > 0) {
	            for (int i = 0; i < filas; i++) {
	                modelo.removeRow(0);
	            }
	        }
	    }		
		
		public static void evaluarGradoDependencia(Observacion o) {
			if(o.getAlimentacion().equals("INDEPENDIENTE") && 
					o.getMovilidad().equals("INDEPENDIENTE") && 
					o.getAseo().equals("INDEPENDIENTE") &&
					o.getVestido().equals("INDEPENDIENTE") && 
					o.getInodoro().equals("INDEPENDIENTE") && 
					o.getEsfinteres().equals("INDEPENDIENTE")){
				o.setGradoDependencia("VALIDO");
			}else if(o.getAlimentacion().equals("INDEPENDIENTE") || 
					o.getMovilidad().equals("INDEPENDIENTE")||
					o.getAseo().equals("INDEPENDIENTE") || 
					o.getVestido().equals("INDEPENDIENTE") || 
					o.getInodoro().equals("INDEPENDIENTE") || 
					o.getEsfinteres().equals("INDEPENDIENTE")){
				o.setGradoDependencia("AG1");
			}else{
				o.setGradoDependencia("AG2");	
			}			
		}	
		
}
	
	
	

	

