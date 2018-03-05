package com.Eu.controladores;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;

import javax.swing.JTable;
import javax.swing.table. DefaultTableCellRenderer;

public class MiRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	Calendar fechaActual=Calendar.getInstance();
	Color color1=new Color(255,211,155);
	Color color2=new Color(235,235,235);

public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	
	Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	
	
	//si la fila esta seleccionada la pintamos de otro color
	if(isSelected){		
			this.setBackground(color1);		
	}else{
		if(row%2==1){		
			this.setBackground(color2);
		}else{
			this.setBackground(Color.white);
		}
	}
	
//	//modificamos los colores de las filas al marcar el JCheckBox
//	if(table.getColumnCount()==13){
//		Boolean loQueTiene= (Boolean) table.getValueAt(row,0);
//		
//		if(loQueTiene!=null){
//			if(loQueTiene.equals(true) ){
//				this.setBackground(color1);
//			}else{
//				if(row%2==1){		
//					this.setBackground(color2);
//				}else{
//					this.setBackground(Color.white);
//				}
//			}
//		}
//	}	
	
	return cell;
	}
} 