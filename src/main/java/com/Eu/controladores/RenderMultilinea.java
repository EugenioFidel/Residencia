package com.Eu.controladores;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table. DefaultTableCellRenderer;

public class RenderMultilinea extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	Color color1=new Color(255,211,155);
	Color color2=new Color(235,235,235);

public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	
	//Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	
	JTextArea jtaCeldaMulti=new JTextArea();
	
	
//	//obtenemos el alto de la celda
	int anchoCelda=table.getCellRect(row, column, false).width;
//	//obtenemos el texto de la celda
	String texto=table.getValueAt(row, column).toString();
	
	jtaCeldaMulti.setText(texto);
	jtaCeldaMulti.setLineWrap(true);
	
	//calculamos el alto del JTextArea que vamos a ecesitar teniendo en cuenta la fuente y el tama�o de la cadena de texto
	//de la tabla sacamos su graphics, de este su fontmetrics y de este el alto de la fuente
	Graphics g=table.getGraphics();
	FontMetrics fm = g.getFontMetrics();
	int h = fm.getHeight();
	// lineasPorFila nos indica cuantas lineas de texto caben en cada "altura predeterminada" de la tabla
	int lineasPorFila = (int) table.getRowHeight() / h;
	// este valor indicar� cuantas lineas son necesarias para el texto de esa celda
	int lineasNecesarias = countLines(texto, anchoCelda, fm);
	int neededRows = (int) Math.ceil(lineasNecesarias / lineasPorFila) + 1;
	// y solo quedar�a establecerle el valor de altura a la fila adecuada de la tabla
	table.setRowHeight(row, neededRows * table.getRowHeight());		
		
		//alternamos el color de las filas
		if(row%2==1){		
			jtaCeldaMulti.setBackground(color2);
		}	
		//si la fila esta seleccionada la pintamos de otro color
		if(isSelected){		
			jtaCeldaMulti.setBackground(color1);		
		}
		return jtaCeldaMulti;		
	}
private int countLines(String line, int width, FontMetrics fm) {

	int count = 1;
	StringTokenizer wordTokenizer = new StringTokenizer(line);
	String tmp = "";
	String token = "";

	while (wordTokenizer.hasMoreTokens()) {

		token = wordTokenizer.nextToken();
		tmp += token + " ";

		if (fm.stringWidth(tmp) > width) {
			tmp = token + " ";
			count += (fm.stringWidth(tmp) / width != 0 ? fm.stringWidth(tmp) / width : 1);
		}
	}
	count=count+5;
	return count;
	}
} 
