package com.Eu.controladores;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

class CELL_RENDERER extends JCheckBox implements TableCellRenderer{
	
	private static final long serialVersionUID = 1L;
	Color color1=new Color(184,207,229);

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setSelected((value != null && ((Boolean) value).booleanValue()));
		if (isSelected()) {
			setBackground(color1);
		}else{
			setBackground(table.getBackground());
		}		
		setHorizontalAlignment(JLabel.CENTER);			
	return this;
	}
}