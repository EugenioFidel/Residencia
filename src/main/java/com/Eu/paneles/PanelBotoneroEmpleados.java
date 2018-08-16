package com.Eu.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.Eu.dao.ContratoDao;
import com.Eu.dao.Empleado_contratoDao;
import com.Eu.formularios.AltaContrato;
import com.Eu.formularios.FrmPrincipal;
import com.Eu.model.Contrato;
import com.Eu.model.Empleado_contrato;

public class PanelBotoneroEmpleados extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	final static Logger loggeador = Logger.getLogger(PanelBotoneroEmpleados.class);
	private JButton jbAnhadir, jbBorrar;
	ButtonGroup bg;
	JRadioButton jrbContratos;
	JRadioButton jrbJornadas;
	Border blackline;

	JLayeredPane jlp;

	PanelContratos pc;
	PanelJornadas pj;
	Font fuente = new Font("Ubuntu", Font.PLAIN, 16);
	Font fuenteN = new Font("Ubuntu", Font.BOLD, 16);

	String pss;

	public PanelBotoneroEmpleados(String password) {
		// TODO Auto-generated constructor stub
		this.setLayout(new GridLayout(1, 32, 10, 10));

		this.setPss(password);

		jbAnhadir = new JButton("Añadir");
		jbAnhadir.setFont(fuenteN);
		jbBorrar = new JButton("Borrar");
		jbBorrar.setFont(fuenteN);

		jbAnhadir.addActionListener(this);
		jbBorrar.addActionListener(this);

		bg = new ButtonGroup();
		jrbContratos = new JRadioButton("Cont.");
		jrbContratos.setFont(fuenteN);
		jrbJornadas = new JRadioButton("Jorn.");
		jrbJornadas.setFont(fuenteN);

		jrbContratos.setSelected(true);

		jrbContratos.addActionListener(this);
		jrbJornadas.addActionListener(this);

		bg.add(jrbContratos);
		bg.add(jrbJornadas);

		this.add(jbAnhadir);
		this.add(jbBorrar);
		this.add(jrbContratos);
		this.add(jrbJornadas);

		blackline = BorderFactory.createLineBorder(Color.black);

		Border b = BorderFactory.createTitledBorder(blackline, "Empleados", TitledBorder.LEFT, TitledBorder.TOP);
		this.setBorder(b);
	}

	public void actionPerformed(ActionEvent e) {
		// necesitamos el formulario principal
		JRootPane jrp = this.getRootPane();
		FrmPrincipal f = (FrmPrincipal) jrp.getParent();

		jlp = f.getJlpFuncionalidades();

		pc = f.getPc();
		pj = f.getPj();

		if (e.getSource().getClass().equals(JButton.class)) {
			if (e.getSource().equals(jbAnhadir)) {
				if (jrbContratos.isSelected()) {
					loggeador.debug("alta de Contrato");
					AnhadirContrato();
				} else {
					loggeador.debug("No se puede generar un nuevo anel de jornadas");

				}
			} else {
				System.out.println("pulso borrar");
				if (jrbContratos.isSelected()) {
					loggeador.debug("Borrar contrato");
					BorrarContrato();
				} else {
					loggeador.debug("borrar observacion");
				}
			}
		} else {
			if (e.getSource().equals(jrbContratos)) {
				jlp.moveToBack(pj);
				jlp.moveToFront(pc);
			} else {
				jlp.moveToBack(pc);
				jlp.moveToFront(pj);
			}
		}
	}

	private void BorrarContrato() {
		JTable jt = pc.getJtContratos();
		DefaultTableModel dtm = (DefaultTableModel) jt.getModel();

		ContratoDao cd = new ContratoDao();
		Empleado_contratoDao ecd = new Empleado_contratoDao();
		Contrato c = cd.getContratoById(Integer.parseInt(dtm.getValueAt(jt.getSelectedRow(), 0).toString()));
		Empleado_contrato ec = ecd.getEmpleado_contratoById(c.getIdContrato());
		ecd.deleteEmpleado_contrato(ec);
		cd.deleteContrato(c);
		dtm.removeRow(jt.getSelectedRow());
	}

	private void AnhadirContrato() {
		// TODO Auto-generated method stub
		DefaultTableModel dtm = (DefaultTableModel) pc.getJtContratos().getModel();
		AltaContrato ac = new AltaContrato(pc.getE(), dtm, pss);
		ac.setVisible(true);
	}

	public JButton getJbAnhadir() {
		return jbAnhadir;
	}

	public void setJbAnhadir(JButton jbAnhadir) {
		this.jbAnhadir = jbAnhadir;
	}

	public JButton getJbBorrar() {
		return jbBorrar;
	}

	public void setJbBorrar(JButton jbBorrar) {
		this.jbBorrar = jbBorrar;
	}

	public JRadioButton getjrbContratos() {
		return jrbContratos;
	}

	public void setjrbContratos(JRadioButton jrbContratos) {
		this.jrbContratos = jrbContratos;
	}

	public JRadioButton getjrbJornadas() {
		return jrbJornadas;
	}

	public void setjrbJornadas(JRadioButton jrbJornadas) {
		this.jrbJornadas = jrbJornadas;
	}

	public String getPss() {
		return pss;
	}

	public void setPss(String pss) {
		this.pss = pss;
	}
}