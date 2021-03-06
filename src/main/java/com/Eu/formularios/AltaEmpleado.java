package com.Eu.formularios;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.Eu.paneles.PanelFiltros;
import com.Eu.dao.EmpleadoDao;
import com.Eu.dao.PersonaDao;
import com.Eu.model.Persona;

public class AltaEmpleado extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final int ALTOFILA=20;

	JLabel jlAyuda = new JLabel();
	// Jpanels para distribución
	JPanel jpAyuda = new JPanel();
	JPanel jpPersonas = new JPanel();
	JPanel jpDatos = new JPanel();
	JPanel jpBotonero = new JPanel();

	JLabel jlCc = new JLabel("Cuenta corriente (IBAN)");
	JTextField jtfCc = new JTextField(30);
	JLabel jlSs = new JLabel("Número de afiliación a la S.Social");
	JTextField jtfSs = new JTextField(30);

	// un boton para grabar y otro para cancelas
	JButton jbGrabar = new JButton("Grabar");
	JButton jbCancelar = new JButton("Cancelar");

	// un GridBagLayout para el jFrame ofiExplot
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints col = new GridBagConstraints();

	// un defaultTableModel para incluir el nuevo interesado en la tabla
	public DefaultTableModel dtmPersona = new DefaultTableModel();

	// un JTable para manejar las personas
	JTable t = new JTable();

	Font fuente = new Font("Verdana", 0, 20);
	Font fuenteN = new Font("Verdana", 1, 20);

	public AltaEmpleado() {

		setSize(700, 700);
		setLocation(200, 100);
		setTitle("Alta de Empleado en el sistema");
		setLayout(gbl);
		setResizable(true);
		setModal(true);

		// Añadimos el ActionListener a los botones
		jbGrabar.addActionListener(this);
		jbGrabar.setFont(fuenteN);
		jbCancelar.addActionListener(this);
		jbCancelar.setFont(fuenteN);

		jlAyuda.setText("Seleccione el empleado a integrar.");

		jpAyuda.add(jlAyuda);

		col.gridx = 0;
		col.gridy = 0;
		col.gridheight = 1;
		col.gridwidth = 1;
		col.insets = new Insets(5, 5, 5, 5);
		add(jpAyuda, col);

		PanelFiltros e = new PanelFiltros(3);

		t = e.getJt();
		t.setRowHeight(ALTOFILA);
		dtmPersona = (DefaultTableModel) t.getModel();

		jpPersonas.add(e);

		col.gridx = 0;
		col.gridy = 1;
		col.gridheight = 1;
		col.gridwidth = 1;
		col.weightx = 1.0;
		col.weighty = 1.0;
		col.fill = GridBagConstraints.BOTH;
		add(jpPersonas, col);
		col.weighty = 0;

		jpDatos.setLayout(new GridLayout(2, 2, 10, 10));
		jpDatos.add(jlCc);
		jpDatos.add(jtfCc);
		jpDatos.add(jlSs);
		jpDatos.add(jtfSs);

		col.gridx = 0;
		col.gridy = 2;
		col.fill = GridBagConstraints.HORIZONTAL;
		add(jpDatos, col);

		jpBotonero.add(jbGrabar);
		jpBotonero.add(jbCancelar);

		col.gridx = 0;
		col.gridy = 3;
		col.fill = GridBagConstraints.NONE;
		add(jpBotonero, col);

		Component[] c = this.getContentPane().getComponents();
		for (Component component : c) {
			Component[] co = ((Container) component).getComponents();
			for (Component comp : co) {
				if (comp instanceof JLabel || comp instanceof JTextField) {
					comp.setFont(fuenteN);
				}
			}

		}
	}

	public void actionPerformed(ActionEvent pulsacion) {
		if (pulsacion.getSource().equals(jbCancelar)) {
			this.dispose();
		} else {
			switch (t.getSelectedRowCount()) {
			case 0:
				JOptionPane.showMessageDialog(this, "No hay ninguna persona seleccionada", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			case 1:
				// damos de alta al nuevo empleado

				String dni = (String) dtmPersona.getValueAt(t.getSelectedRow(), 0);
				String[] palabrasEnDni = dni.split("-");

				PersonaDao pd = new PersonaDao();
				List<Object> lista = pd.listaPersonasPorDni(palabrasEnDni[1]);
				Persona p = (Persona) lista.get(0);
				Object[] datos = { p.getIdpersona(), jtfCc.getText(), jtfSs.getText() };
				EmpleadoDao ed = new EmpleadoDao();
				ed.addEmpleadoConId(datos);

				break;
			default:
				JOptionPane.showMessageDialog(this, "De uno en uno mejor", "Error", JOptionPane.ERROR_MESSAGE);
			}
			this.dispose();
		}
	}

}
