package com.Eu.controladores;


import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

class CELL_EDITOR extends DefaultCellEditor{

	private static final long serialVersionUID = 1L;

	public CELL_EDITOR(JCheckBox checkBox) {
		super(checkBox);
		checkBox.setHorizontalAlignment(JLabel.CENTER);
	}
}
