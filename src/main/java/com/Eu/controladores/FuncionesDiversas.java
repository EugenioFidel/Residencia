package com.Eu.controladores;

import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import com.Eu.dao.EmpleadoDao;
import com.Eu.dao.PersonaDao;
import com.Eu.model.Empleado;
import com.Eu.model.Estancia;
import com.Eu.model.Interno;
import com.Eu.model.Observacion;
import com.Eu.model.Persona;
import com.mysql.jdbc.Connection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;


public class FuncionesDiversas {
	// Un TableRowSorter para ordenar y filtrar la tabla
	static TableRowSorter<TableModel> elQueOrdena = null;
	final static Logger loggeador = Logger.getLogger(FuncionesDiversas.class);
	// un Map para pasarle parámetros a los informes
	public static Map<String, Object> parametros = new HashMap<String, Object>();
	String pss;
	static Font fuente = new Font("Ubuntu", 0, 20);
	static Font fuenteN = new Font("Ubuntu", 1, 20);

	public static JTable cargaDatosEnTablaPersonas(List<Object> lista, String[] cabeceras) {

		DefaultTableModel dtm = new DefaultTableModel();
		for (int i = 0; i < cabeceras.length; i++)
			dtm.addColumn(cabeceras[i]);

		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Persona pe = (Persona) iterator.next();
			Object fila[] = new Object[cabeceras.length];
			fila[0] = pe.getLetraCif() + "-" + pe.getDni() + "-" + pe.getLetraNif();
			fila[1] = pe.getPrimerApe() + " " + pe.getSegundoApe() + ", " + pe.getNombre();
			fila[2] = pe.getDireccion();
			fila[3] = pe.getLocalidad();
			fila[4] = pe.getMunicipio();
			fila[5] = pe.getCp();
			fila[6] = pe.getProvincia();
			fila[7] = pe.getTelefono1() + " / " + pe.getTelefono2();
			fila[8] = pe.getEmail();
			dtm.addRow(fila);
		}

		// Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena = new TableRowSorter<TableModel>(dtm);
		JTable tb = new JTable(dtm);

		JTableHeader tbh = tb.getTableHeader();
		tbh.setFont(fuenteN);
		tb.setRowSorter(elQueOrdena);
		return tb;
	}

	public static JTable cargaDatosEnTablaEmpleados(List<Empleado> lista, String[] cabeceras) {

		DefaultTableModel dtm = new DefaultTableModel();
		for (int i = 0; i < cabeceras.length; i++)
			dtm.addColumn(cabeceras[i]);

		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Empleado e = (Empleado) iterator.next();
			Object fila[] = new Object[cabeceras.length];
			fila[0] = e.getLetraCif() + "-" + e.getDni() + "-" + e.getLetraNif();
			fila[1] = e.getPrimerApe() + " " + e.getSegundoApe() + ", " + e.getNombre();
			fila[2] = e.getDireccion();
			fila[3] = e.getLocalidad();
			fila[4] = e.getMunicipio();
			fila[5] = e.getCp();
			fila[6] = e.getProvincia();
			fila[7] = e.getTelefono1() + " / " + e.getTelefono2();
			fila[8] = e.getEmail();
			fila[9] = e.getCc();
			fila[10] = e.getSs();

			dtm.addRow(fila);
		}

		// Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena = new TableRowSorter<TableModel>(dtm);
		JTable tb = new JTable(dtm);

		JTableHeader tbh = tb.getTableHeader();
		tbh.setFont(fuenteN);
		tb.setRowSorter(elQueOrdena);

		return tb;
	}

	public static JTable cargaDatosEnTablaInternos(List<Interno> lista, String[] cabeceras) {

		DefaultTableModel dtm = new DefaultTableModel();
		for (int i = 0; i < cabeceras.length; i++)
			dtm.addColumn(cabeceras[i]);

		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Interno i = (Interno) iterator.next();
			Object fila[] = new Object[cabeceras.length];
			fila[0] = i.getLetraCif() + "-" + i.getDni() + "-" + i.getLetraNif();
			fila[1] = i.getPrimerApe() + " " + i.getSegundoApe() + ", " + i.getNombre();
			fila[2] = i.getDireccion();
			fila[3] = i.getLocalidad();
			fila[4] = i.getMunicipio();
			fila[5] = i.getCp();
			fila[6] = i.getProvincia();
			fila[7] = i.getTelefono1() + " / " + i.getTelefono2();
			fila[8] = i.getEmail();
			fila[9] = i.getCc();
			fila[10] = i.getSs();
			fila[11] = i.getHabitacion();

			PersonaDao pd = new PersonaDao();
			List<Object> lp = pd.listaPersonasPorDni(i.getDniResponsable());
			Persona p = (Persona) lp.get(0);
			fila[12] = p.getNombre() + " " + p.getPrimerApe() + " " + p.getSegundoApe();

			dtm.addRow(fila);
		}

		// Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena = new TableRowSorter<TableModel>(dtm);
		JTable tb = new JTable(dtm);

		JTableHeader tbh = tb.getTableHeader();
		tbh.setFont(fuenteN);
		tb.setRowSorter(elQueOrdena);

		return tb;
	}

	public static JTable cargaDatosEnTablaObservaciones(List<Observacion> lista, String[] cabeceras) {
		/*
		 * creamos un DefaultTableModel dft y con el una JTable jt.
		 */
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

		DefaultTableModel dtm = new DefaultTableModel();
		for (int i = 0; i < cabeceras.length; i++)
			dtm.addColumn(cabeceras[i]);

		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Object[] fila = (Object[]) iterator.next();
			Object filaB[] = new Object[fila.length];
			filaB[0] = fila[0];
			for (int i = 1; i < fila.length; i++) {
				if (i == 1) {
					filaB[i] = dt.format(fila[i]);
				} else {
					filaB[i] = fila[i];
				}
			}
			dtm.addRow(filaB);
		}

		// Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena = new TableRowSorter<TableModel>(dtm);
		JTable tb = new JTable(dtm);

		JTableHeader tbh = tb.getTableHeader();
		tbh.setFont(fuenteN);
		tb.setRowSorter(elQueOrdena);

		return tb;
	}

	public static JTable cargaDatosEnTablaEstancias(List<Estancia> lista, String[] cabeceras) {
		/*
		 * creamos un DefaultTableModel dft y con el una JTable jt.
		 */
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

		DefaultTableModel dtm = new DefaultTableModel();
		for (int i = 0; i < cabeceras.length; i++)
			dtm.addColumn(cabeceras[i]);

		for (@SuppressWarnings("rawtypes")
		Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Object[] fila = (Object[]) iterator.next();
			Object filaB[] = new Object[fila.length];
			filaB[0] = fila[0];
			for (int i = 1; i < fila.length; i++) {
				if (i == 1 || i == 3) {
					if (!(fila[i] == null)) {
						filaB[i] = dt.format(fila[i]);
					} else {
						filaB[i] = null;
					}
				} else {
					filaB[i] = fila[i];
				}
			}
			dtm.addRow(filaB);
		}

		// Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena = new TableRowSorter<TableModel>(dtm);
		JTable tb = new JTable(dtm);

		JTableHeader tbh = tb.getTableHeader();
		tbh.setFont(fuenteN);
		tb.setRowSorter(elQueOrdena);

		return tb;
	}

	public static JTable cargaDatosEnTablaContratos(List<Object> contratos, String[] cabeceras) {
		/*
		 * creamos un DefaultTableModel dft y con el una JTable jt.
		 */
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

		DefaultTableModel dtm = new DefaultTableModel();
		for (int i = 0; i < cabeceras.length; i++)
			dtm.addColumn(cabeceras[i]);

		for (@SuppressWarnings("rawtypes")
		Iterator iterator = contratos.iterator(); iterator.hasNext();) {
			Object[] fila = (Object[]) iterator.next();
			Object filaB[] = new Object[fila.length];
			filaB[0] = fila[0];
			for (int i = 1; i < fila.length; i++) {
				if (i == 1 || i == 2 || i == 6) {
					if (!(fila[i] == null)) {
						filaB[i] = dt.format(fila[i]);
					} else {
						filaB[i] = null;
					}
				} else if (i == 7) {
					if (!(fila[i].equals(0))) {
						EmpleadoDao ed = new EmpleadoDao();
						Empleado e = ed.getEmpleadoById(Integer.parseInt(fila[i].toString()));
						filaB[i] = e.getPrimerApe() + " " + e.getSegundoApe() + ", " + e.getNombre();
					} else {
						filaB[i] = "";
					}

				} else {
					filaB[i] = fila[i];
				}
			}
			dtm.addRow(filaB);
		}

		// Un TableRowSorter para ordenar y filtrar la tabla
		elQueOrdena = new TableRowSorter<TableModel>(dtm);
		JTable tb = new JTable(dtm);

		JTableHeader tbh = tb.getTableHeader();
		tbh.setFont(fuenteN);
		tb.setRowSorter(elQueOrdena);

		return tb;
	}

	public static void positionColumn(JTable table, int col_Index) {
		table.moveColumn(table.getColumnCount() - 1, col_Index);
	}

	public static void generarReporte(JasperReport reporte, Map<String, Object> params, java.sql.Connection conec,
			String nomFichero) {
		JasperPrint print;

		try {
			print = JasperFillManager.fillReport(reporte, params, conec);
			nomFichero = DatarFichero(nomFichero);
			JasperExportManager.exportReportToPdfFile(print, nomFichero);
		} catch (JRException e) {
			System.out.println("Error en la generación del reporte. Consulta el fichero de logs para más información");
			e.printStackTrace();
			loggeador.error(e.getMessage());
		}
		MostrarEnVisorPDF(nomFichero);
	}

	public static void MostrarEnVisorPDF(String nomFichero) {
		nomFichero = nomFichero.replace("./", "");
		try {
			File path = new File(nomFichero);
			Desktop.getDesktop().open(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void GenerarListadoPersonas(String pss) {
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfPersonas.jrxml");
			generarReporte(reportListado, null, conec, "./src/main/resources/informes/ListadoPersonas_");
			conec.close();
			con.desconectar();
		} catch (JRException e1) {
			System.out.println("Error en la generación del listado, consulta los logs para más información");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {
			loggeador.error(e.getMessage());
		}
	}

	public void GenerarListadoTelefonos(String pss) {
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfTelefonos.jrxml");
			generarReporte(reportListado, null, conec, "./src/main/resources/informes/ListadoTelefonos_");
			conec.close();
			con.desconectar();
		} catch (JRException e1) {
			System.out.println("Error en la generación del listado, consulta los logs para más información");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {

			loggeador.error(e.getMessage());
		}
	}

	public void GenerarListadoInternos(String pss) {
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfResidentes.jrxml");
			generarReporte(reportListado, null, conec, "./src/main/resources/informes/ListadoResidentes_");
			conec.close();
			con.desconectar();
		} catch (JRException e1) {
			System.out.println("Error en la generación del listado, consulta los logs para más información");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {
			loggeador.error(e.getMessage());
		}
	}

	public void GenerarListadoEmpleados(String pss) {
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfEmpleados.jrxml");
			generarReporte(reportListado, null, conec, "./src/main/resources/informes/ListadoEmpleados_");
			conec.close();
			con.desconectar();
		} catch (JRException e1) {
			System.out.println("Error en la generación del listado, consulta los logs para más información");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {
			loggeador.error(e.getMessage());
		}
	}

	public void GenerarInformeCuotas(Date fecha, String pss) {
		String nomFichero = "./src/main/resources/informes/cuotas/InformeCuotas_";
		@SuppressWarnings("unused")
		int resultado = 0;

		nomFichero = DatarFichero(nomFichero);

		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd)");
		String dia = sdt.format(fecha);
		// actualizamos en la base la vista obs
		String cadenaCrearVistaCuotas = "create or replace view cuotas "
				+ "AS select distinct `persona`.`letraCif` AS `letraCif`,"
				+ "`persona`.`dni` AS `dni`,`persona`.`letraNif` AS `letraNif`,"
				+ "`persona`.`nombre` AS `nombre`,`persona`.`primerApe` AS `primerApe`,"
				+ "`persona`.`segundoApe` AS `segundoApe`,`interno`.`cc` AS `cc`,"
				+ "`observacion`.`gradoDependencia` AS `gradoDependencia`,"
				+ "`importes`.`importe` AS `importe`,`observacion`." + "`fechaObservacion` AS `fechaObservacion` from "
				+ "((((((`persona` join `interno`) join `observacion`) join "
				+ "`interno_observacion`) join `importes`) join `estancia`) join "
				+ "`interno_estancia`) where ((`persona`.`idPersona` = `interno`.`idPersona`)"
				+ " and (`interno`.`idPersona` = `interno_estancia`.`idPersona`)"
				+ " and (`interno_estancia`.`idEstancia` = `estancia`.`idEstancia`)"
				+ " and (`estancia`.`tipoEstancia`=1)" + " and (`estancia`.`motivoBaja` = 0)"
				+ " and (`interno`.`idPersona` = `interno_observacion`.`idPersona`)"
				+ " and (`interno_observacion`.`idObservacion` = `observacion`.`idObservacion`)"
				+ " and (`observacion`.`gradoDependencia` = `importes`.`gradoDependencia`)"
				+ " and (`observacion`.`fechaObservacion` <= \"" + dia + "\"))"
				+ " order by `persona`.`primerApe`,`persona`.`segundoApe`,`persona`.`nombre`";
		Updatear(cadenaCrearVistaCuotas, pss);

		// vaciamos los parametros
		parametros.clear();
		// metemos en parametros la fecha
		parametros.put("fechaInforme", fecha);
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfCuotas.jrxml");
			generarReporte(reportListado, parametros, conec, "./src/main/resources/informes/cuotas/InformeCuotas_");
			conec.close();
			con.desconectar();
			if (JOptionPane.showConfirmDialog(null,
					"<html><font face='Ubuntu' size='10' color='black'>¿Deseas enviar a gestión el informe?",
					"Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				resultado = FuncionesDiversas.EnviarMail(nomFichero, "Informe de cuotas", pss);
		} catch (JRException e1) {
			System.out.println("Error en la generación del informe, consulta logs para más información");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {

			loggeador.error(e.getMessage());
		}
	}

	public void GenerarInformeCuotasCd(Date fecha, String pss) {
		String nomFichero = "./src/main/resources/informes/cuotas/InformeCuotasCd_";
		@SuppressWarnings("unused")
		int resultado = 0;

		nomFichero = DatarFichero(nomFichero);

		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd)");
		String dia = sdt.format(fecha);
		// actualizamos en la base la vista obs
		String cadenaCrearVistaCuotasCd = "create or replace view cuotasCd\n" + 
				"AS \n" + 
				"select distinct \n" + 
				"	`persona`.`letraCif` AS `letraCif`,\n" + 
				"	`persona`.`dni` AS `dni`,\n" + 
				"	`persona`.`letraNif` AS `letraNif`,\n" + 
				"	`persona`.`nombre` AS `nombre`,\n" + 
				"	`persona`.`primerApe` AS `primerApe`,\n" + 
				"	`persona`.`segundoApe` AS `segundoApe`,\n" + 
				"	`interno`.`cc` AS `cc`,\n" +
				"	`tipoestancia`.`tipoEstancia` AS `tipoEstancia`,\n" + 
				"	`tipoestancia`.`precio` AS `precio`\n" + 
				"FROM\n" + 
				"	((((((`persona` join `interno`)\n" + 
				"	join `observacion`)\n" + 
				"	join `interno_observacion`)\n" + 
				"	join `importes`)\n" + 
				"	join `estancia`)\n" + 
				"	join `interno_estancia`\n" + 
				"	join `tipoestancia`) \n" + 
				"where\n" + 
				"	((`persona`.`idPersona` = `interno`.`idPersona`)\n" + 
				"	and (`interno`.`idPersona` = `interno_estancia`.`idPersona`)\n" + 
				"	and (`interno_estancia`.`idEstancia` = `estancia`.`idEstancia`)\n" + 
				"	and (`estancia`.`tipoEstancia`!=1)\n" + 
				"	and (`estancia`.`tipoEstancia`= `tipoestancia`.`idtipoestancia`)\n" + 
				"	and (`estancia`.`motivoBaja` = 0)	\n" + 
				"	and (`interno`.`idPersona` = `interno_observacion`.`idPersona`)\n" + 
				"	and (`interno_observacion`.`idObservacion` = `observacion`.`idObservacion`)\n" + 
				"	and (`observacion`.`gradoDependencia` = `importes`.`gradoDependencia`)\n" + 
				"	and (`observacion`.`fechaObservacion` <= \""+ dia + "\"))\n" + 
				"order by\n" + 
				"	`persona`.`primerApe`,\n" + 
				"	`persona`.`segundoApe`,\n" + 
				"	`persona`.`nombre`";
		Updatear(cadenaCrearVistaCuotasCd, pss);

		// vaciamos los parametros
		parametros.clear();
		// metemos en parametros la fecha
		parametros.put("fechaInforme", fecha);
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager.compileReport("./src/main/resources/InfCuotasCd.jrxml");
			generarReporte(reportListado, parametros, conec, "./src/main/resources/informes/cuotas/InformeCuotasCd_");
			conec.close();
			con.desconectar();
			if (JOptionPane.showConfirmDialog(null,
					"<html><font face='Ubuntu' size='10' color='black'>¿Deseas enviar a gestión el informe?",
					"Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				resultado = FuncionesDiversas.EnviarMail(nomFichero, "Informe de cuotas del Centro de día", pss);
		} catch (JRException e1) {
			System.out.println("Error en la generación del informe, consulta logs para más información");
			e1.printStackTrace();
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			loggeador.error(e.getMessage());
		}
	}


	public static void GenerarInformeNuevoContrato(int idContrato, String pss) {

		// vaciamos los parametros
		parametros.clear();
		// metemos en parametros la fecha
		parametros.put("idContrato", idContrato);
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager
					.compileReport("./src/main/resources/InfNewContrato.jrxml");
			generarReporte(reportListado, parametros, conec,
					"./src/main/resources/informes/contratos/InformeNuevoContrato_");
			conec.close();
			con.desconectar();
		} catch (JRException e1) {
			System.out.println("Error en la generación del listado");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {

			loggeador.error(e.getMessage());
		}
	}

	public void GenerarInformeDependencias(Date fecha, String pss) {
		String nomFichero = "./src/main/resources/informes/dependencias/InformeDependencias_";
		@SuppressWarnings("unused")
		int resultado = 0;

		nomFichero = DatarFichero(nomFichero);

		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		String dia = sdt.format(fecha);
		// actualizamos en la base la vista obs
		String cadenaCrearVistaDep = "create or replace VIEW `dependencias` "
				+ "AS select distinct `persona`.`letraCif` AS `letraCif`,`persona`.`dni` "
				+ "AS `dni`,`persona`.`letraNif` AS `letraNif`,`persona`.`nombre` AS `nombre`,"
				+ "`persona`.`primerApe` AS `primerApe`,`persona`.`segundoApe` AS `segundoApe`,"
				+ "`observacion`.`fechaObservacion` AS `fechaObservacion`,"
				+ "`observacion`.`alimentacion` AS `alimentacion`,`observacion`."
				+ "`movilidad` AS `movilidad`,`observacion`.`aseo` AS `aseo`,`observacion`."
				+ "`vestido` AS `vestido`,`observacion`.`inodoro` AS `inodoro`,"
				+ "`observacion`.`esfinteres` AS `esfinteres`,"
				+ "`observacion`.`gradoDependencia` AS `gradoDependencia` "
				+ "from (((`persona` join `interno`) join `observacion`) join `interno_observacion`) "
				+ "where ((`persona`.`idPersona` = `interno`.`idPersona`) and "
				+ "(`interno`.`idPersona` = `interno_observacion`.`idPersona`) and "
				+ "(`interno_observacion`.`idObservacion` = `observacion`.`idObservacion`) "
				+ "and (`observacion`.`fechaObservacion` <= \"" + dia + "\") and "
				+ "`interno`.`idPersona` in (select `interno`.`idPersona` "
				+ "from ((`interno` join `interno_estancia`) join `estancia`) "
				+ "where ((`interno`.`idPersona` = `interno_estancia`.`idPersona`) "
				+ "and (`interno_estancia`.`idEstancia` = `estancia`.`idEstancia`) "
				+ "and (`estancia`.`motivoBaja` = 0)))) order by `persona`.`primerApe`,"
				+ "`persona`.`segundoApe`,`persona`.`nombre`";
		Updatear(cadenaCrearVistaDep, pss);
		// vaciamos los parametros
		parametros.clear();
		// metemos en parametros la fecha
		parametros.put("fechaInforme", fecha);
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager
					.compileReport("./src/main/resources/InfDependencias.jrxml");
			generarReporte(reportListado, parametros, conec,
					"./src/main/resources/informes/dependencias/InformeDependencias_");
			conec.close();
			con.desconectar();
			if (JOptionPane.showConfirmDialog(null,
					"<html><font face='Ubuntu' size='14' color='black'>¿Deseas enviar a gestión el informe?",
					"Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				resultado = FuncionesDiversas.EnviarMail(nomFichero, "Informe de dependencias", pss);
		} catch (JRException e1) {
			System.out.println("Error en la generación de listado");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {

			loggeador.error(e.getMessage());
		}
	}

	public void GenerarListadoNumClientes(Date fecha, String pss) {
		String nomFichero = "./src/main/resources/informes/numClientes/InformeNumClientes_";
		@SuppressWarnings("unused")
		int resultado = 0;

		nomFichero = DatarFichero(nomFichero);

		// vaciamos los parametros
		parametros.clear();
		// metemos en parametros la fecha
		parametros.put("fechaInforme", fecha);
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager
					.compileReport("./src/main/resources/InfNumClientes.jrxml");
			generarReporte(reportListado, parametros, conec,
					"./src/main/resources/informes/numClientes/InformeNumClientes_");
			conec.close();
			con.desconectar();
			// generamos el mail
			if (JOptionPane.showConfirmDialog(null,
					"<html><font face='Ubuntu' size='10' color='black'>¿Deseas enviar a gestión el informe?",
					"Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				resultado = FuncionesDiversas.EnviarMail(nomFichero, "Informe de número de residentes", pss);
		} catch (JRException e1) {
			System.out.println("Error en la generación de listado");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {

			loggeador.error(e.getMessage());
		}
	}

	public static int GenerarInformeContratoSustitucion(Map<String, Object> parametros, String pss) {
		String nomFichero = "./src/main/resources/informes/contratos/InformeContrato_";
		nomFichero = DatarFichero(nomFichero);
		int resultado = 0;

		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager
					.compileReport("./src/main/resources/InfNewContrato.jrxml");
			generarReporte(reportListado, parametros, conec,
					"./src/main/resources/informes/contratos/InformeContrato_");
			conec.close();
			con.desconectar();
			// generamos el mail
			if (JOptionPane.showConfirmDialog(null,
					"<html><font face='Verdana' size='10' color='black'>¿Deseas enviar a gestión el contrato?",
					"Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				resultado = FuncionesDiversas.EnviarMail(nomFichero, "propuesta de contratación", pss);
		} catch (JRException e1) {
			System.out.println("Error en la generación de listado");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {

			System.out.println("Error en la generación de listado");
			loggeador.error(e.getMessage());
		}
		return resultado;
	}

	public static void GenerarAltaResidente(Map<String, Object> parametros, String pss) {
		String nomFichero = "./src/main/resources/informes/Informes_A_B/Altas/ComAltaResidente_";
		nomFichero = DatarFichero(nomFichero);

		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager
					.compileReport("./src/main/resources/ComunicacionAltaResidente.jrxml");
			generarReporte(reportListado, parametros, conec,
					"./src/main/resources/informes/Informes_A_B/Altas/ComAltaResidente_");
			conec.close();
			con.desconectar();
			if (JOptionPane.showConfirmDialog(null,
					"<html><font face='Ubuntu' size='10' color='black'>¿Deseas enviar a gestión el alta del residente?",
					"Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				FuncionesDiversas.EnviarMail(nomFichero, "alta de residente", pss);
		} catch (JRException e1) {
			System.out.println("Error en la generación del informe");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {

			loggeador.error(e.getMessage());
		}
	}

	public static void GenerarBajaResidente(Map<String, Object> parametros, String pss) {
		String nomFichero = "./src/main/resources/informes/Informes_A_B/Bajas/ComBajaResidente_";
		nomFichero = DatarFichero(nomFichero);
		try {
			// conexion para el reporte
			dbConexion con = new dbConexion(pss);
			java.sql.Connection conec = con.getConexion();
			JasperReport reportListado = JasperCompileManager
					.compileReport("./src/main/resources/ComunicacionBajaResidente.jrxml");
			generarReporte(reportListado, parametros, conec,
					"./src/main/resources/informes/Informes_A_B/Bajas/ComBajaResidente_");
			conec.close();
			con.desconectar();
			if (JOptionPane.showConfirmDialog(null,
					"<html><font face='Ubuntu' size='10' color='black'>¿Deseas enviar a gestión la baja del residente?",
					"Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				FuncionesDiversas.EnviarMail(nomFichero, "baja de residente", pss);
		} catch (JRException e1) {
			System.out.println("Error en la generación del informe");
			loggeador.error(e1.getMessage());
		} catch (SQLException e) {

			loggeador.error(e.getMessage());
		}
	}

	public static int EnviarMail(String adj, String documento, String pssw) {

		String direccion = FuncionesDiversas.obtenerStringBase("select mailGestPersonal from parametros", pssw);
		int o = JOptionPane.showConfirmDialog(null, "El mail se enviará a " + direccion + " ¿Es correcto?");
		if (o != 0) {
			direccion = JOptionPane.showInputDialog(null,
					"<html><font face='Ubuntu' size='10' color='black'>Introduce la direccón de envío: ", "Atención",
					JOptionPane.OK_CANCEL_OPTION);
		}
		String pss = IntroduccionPassword();
		String password = FuncionesDiversas.obtenerStringBase("select passMail from parametros", pssw);
		if (!password.equals(FuncionesDiversas.getCifrado(pss, "MD5"))) {
			JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Atención", JOptionPane.ERROR_MESSAGE);
			loggeador.error("error de contraseña");
		} else {

			String nomFichero = "InformeNewContrato";

			if (documento.equals("baja de residente")) {
				nomFichero = "bajaResidente";
			} else {
				if (documento.equals("alta de residente")) {
					nomFichero = "altaResidente";
				} else {
					if (documento.equals("Informe de número de residentes")) {
						nomFichero = "InformeNumClientes";
					} else {
						if (documento.equals("Informe de dependencias")) {
							nomFichero = "informeDependencias";
						} else {
							if (documento.equals("Informe de cuotas"))
								nomFichero = "informeCuotas";
						}
					}
				}
			}

			nomFichero = DatarFichero(nomFichero);

			String originMail = FuncionesDiversas.obtenerStringBase("select mailDir from parametros", pssw);

			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user", originMail);
			props.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);

			BodyPart texto = new MimeBodyPart();
			try {
				texto.setText("Adjunto a la presente te remito " + documento + "\n" + "cualquier cosa, ya sabes.\n"
						+ "\t\tUn saludo\n\t\t\tRaquel"
						+ "\n\n Ruego me hagas acuse de recibo de este mail para tener certeza de su recepción. Gracias.");
			} catch (MessagingException e) {
				loggeador.error(e.getMessage());
			}
			BodyPart adjunto = new MimeBodyPart();
			try {
				adjunto.setDataHandler(new DataHandler(new FileDataSource(adj)));
				adjunto.setFileName(nomFichero);
				MimeMultipart multiParte = new MimeMultipart();
				multiParte.addBodyPart(texto);
				multiParte.addBodyPart(adjunto);
				MimeMessage message = new MimeMessage(session);
				// Se rellena el From
				message.setFrom(new InternetAddress(originMail));
				// Se rellenan los destinatarios
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(direccion));
				// Se rellena el subject
				message.setSubject(documento + " Hoyo de Pinares");
				// Se mete el texto y la foto adjunta.
				message.setContent(multiParte);
				Transport t = session.getTransport("smtp");
				t.connect(originMail, pss);
				t.sendMessage(message, message.getAllRecipients());
				t.close();
				JOptionPane.showMessageDialog(null, "Mensaje enviado", "Atención", JOptionPane.OK_OPTION);
				return 1;
			} catch (MessagingException e1) {
				loggeador.error(e1.getMessage());
				return 0;
			}
		}

		return 0;
	}

	private static String IntroduccionPassword() {
		JPanel panel = new JPanel();
		panel.setFont(fuenteN);
		JLabel label = new JLabel("Introduce la contraseña:");
		label.setFont(fuenteN);
		JPasswordField pass = new JPasswordField(10);
		pass.setFont(fuente);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "OK", "Cancelar" };
		// int option = JOptionPane.showOptionDialog(null, panel, "Contraseña",
		// JOptionPane.NO_OPTION,
		// JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		int option = JOptionPane.showOptionDialog(null, panel, "Identificación", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if (option == 0) // pressing OK button
		{
			char[] password = pass.getPassword();
			return new String(password);
		}
		return null;
	}

	public static String getCifrado(String texto, String hashType) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
			byte[] array = md.digest(texto.getBytes());
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			loggeador.error("Error " + e.getMessage());
		}
		return "";
	}

	public static String obtenerStringBase(String consulta, String pss) {
		String valor = "";
		try {
			dbConexion con = new dbConexion(pss);
			Connection conec = (Connection) con.getConexion();
			java.sql.Statement st = conec.createStatement();

			java.sql.ResultSet rs = st.executeQuery(consulta);
			rs.next();
			valor = rs.getString(1);

			rs.close();
			st.close();
			conec.close();

		} catch (SQLException e) {
			loggeador.error(e.getMessage());
		}
		return valor;
	}

	public static void Updatear(String cadenaUpdate, String pss) {
		try {
			dbConexion con = new dbConexion(pss);
			Connection conec = (Connection) con.getConexion();
			java.sql.Statement st = conec.createStatement();

			st.executeUpdate(cadenaUpdate);
			st.close();
			conec.close();
			con.desconectar();
		} catch (SQLException e) {
			loggeador.error(e.getMessage());
		}
	}

	public static Calendar DateToCalendar(Date date) {
		Calendar cal = null;
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = (Date) formatter.parse(date.toString());
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {
			loggeador.error(e.getMessage());
		}
		return cal;
	}

	public static boolean ComprobarFormFecha(String fecha) {
		String regexp = "\\d{1,2}/\\d{1,2}/\\d{4}";
		boolean result = (Pattern.matches(regexp, fecha)) ? true : false;
		return result;
	}

	public static void LimpiarDtm(DefaultTableModel modelo) {
		int filas = modelo.getRowCount();
		if (filas > 0) {
			for (int i = 0; i < filas; i++) {
				modelo.removeRow(0);
			}
		}
	}

	public static void evaluarGradoDependencia(Observacion o) {
		if (o.getAlimentacion().equals("INDEPENDIENTE") && o.getMovilidad().equals("INDEPENDIENTE")
				&& o.getAseo().equals("INDEPENDIENTE") && o.getVestido().equals("INDEPENDIENTE")
				&& o.getInodoro().equals("INDEPENDIENTE") && o.getEsfinteres().equals("INDEPENDIENTE")) {
			o.setGradoDependencia("VALIDO");
		} else if (o.getAlimentacion().equals("INDEPENDIENTE") || o.getMovilidad().equals("INDEPENDIENTE")
				|| o.getAseo().equals("INDEPENDIENTE") || o.getVestido().equals("INDEPENDIENTE")
				|| o.getInodoro().equals("INDEPENDIENTE") || o.getEsfinteres().equals("INDEPENDIENTE")) {
			o.setGradoDependencia("AG1");
		} else {
			o.setGradoDependencia("AG2");
		}
	}

	public static String DatarFichero(String nomF) {
		String nomFichero = nomF;
		Calendar fechaActual = Calendar.getInstance();

		nomFichero = nomFichero + Integer.toString(fechaActual.get(Calendar.DATE)) + "_"
				+ Integer.toString(fechaActual.get(Calendar.MONTH) + 1) + "_"
				+ Integer.toString(fechaActual.get(Calendar.YEAR)) + " time "
				+ Integer.toString(fechaActual.get(Calendar.HOUR)) + "h"
				+ Integer.toString(fechaActual.get(Calendar.MINUTE)) + "''.pdf";
		return nomFichero;
	}

	public String getPss() {
		return pss;
	}

	public void setPss(String pss) {
		this.pss = pss;
	}

}