package com.Eu.formularios;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.Eu.paneles.PanelFiltros;
import com.Eu.dao.InternoDao;
import com.Eu.dao.PersonaDao;
import com.Eu.model.Persona;

public class AltaInterno extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;	
	
	JLabel jlAyuda=new JLabel();
	//Jpanels para distribución
	JPanel jpAyuda=new JPanel();
	JPanel jpPersonas=new JPanel();
	JPanel jpDatos=new JPanel();	
	JPanel jpBotonero=new JPanel();
	
	JLabel jlCc=new JLabel("Cuenta corriente (IBAN)");
	JTextField jtfCc=new JTextField(30);
	JLabel jlSs=new JLabel("Número de afiliación a la S.Social");
	JTextField jtfSs=new JTextField(30);
	JLabel jlHabitacion=new JLabel("Habitación");
	JTextField jtfHabitación=new JTextField(10);
	JLabel jlDniResponsable=new JLabel("Persona responsable");
	JComboBox<String> jcbPersonaResponsable=new JComboBox<String>();
	
	//un boton para grabar y otro para cancelar
	JButton jbGrabar=new JButton("Grabar");
	JButton jbCancelar=new JButton("Cancelar");
	
		
	//un GridBagLayout para el jFrame ofiExplot
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints col=new GridBagConstraints();
	
	//un defaultTableModel para incluir el nuevo interesado en la tabla
	public DefaultTableModel dtmPersona=new DefaultTableModel();	
	
	//un JTable para manejar las personas
	JTable t=new JTable();
	
	
	public AltaInterno(){
		
		setSize(700,700);
		setLocation(200, 100);
		setTitle("Alta de Interno en el sistema");
		setLayout(gbl);
		setResizable(true);
		setModal(true);
		
		//Añadimos el ActionListener a los botones
		jbGrabar.addActionListener(this);
		jbCancelar.addActionListener(this);
		
		jlAyuda.setText("Seleccione en la tabla la persona que se integrará en el sistema como interno.");
		
		jpAyuda.add(jlAyuda);
		
		col.gridx=0;
		col.gridy=0;
		col.gridheight=1;
		col.gridwidth=1;
		col.insets=new Insets(5,5,5,5);
		add(jpAyuda,col);
		
		PanelFiltros e=new PanelFiltros(3);
		
		t=e.getJt();
		dtmPersona=(DefaultTableModel) t.getModel();
		
		jpPersonas.add(e);
		
		col.gridx=0;
		col.gridy=1;
		col.gridheight=1;
		col.gridwidth=1;
		col.weightx=1.0;
		col.weighty=1.0;
		col.fill=GridBagConstraints.BOTH;
		add(jpPersonas,col);
		col.weighty=0;
		
		jpDatos.setLayout(new GridLayout(4,2,10,10));
		jpDatos.add(jlCc);
		jpDatos.add(jtfCc);
		jpDatos.add(jlSs);
		jpDatos.add(jtfSs);
		jpDatos.add(jlHabitacion);
		jpDatos.add(jtfHabitación);
		jpDatos.add(jlDniResponsable);
		RellenarComboPersonas();
		jpDatos.add(jcbPersonaResponsable);
		
		col.gridx=0;
		col.gridy=2;
		col.fill=GridBagConstraints.HORIZONTAL;
		add(jpDatos,col);
		
		jpBotonero.add(jbGrabar);
		jpBotonero.add(jbCancelar);
		
		col.gridx=0;
		col.gridy=3;
		col.fill=GridBagConstraints.NONE;
		add(jpBotonero,col);
	}

	private void RellenarComboPersonas() {
		// TODO Auto-generated method stub
		PersonaDao pd=new PersonaDao();
		List<Object>personas=pd.listaPersonas();
		@SuppressWarnings("rawtypes")
		Iterator it=personas.iterator();
		while (it.hasNext()){
			Persona p=(Persona) it.next();
			jcbPersonaResponsable.addItem(p.getDni()+"//"+p.getPrimerApe()+", "+p.getNombre());			
		}		
		
	}

	public void actionPerformed(ActionEvent pulsacion) {
		if(pulsacion.getSource().equals(jbCancelar)){
			this.dispose();
		}else{
			switch(t.getSelectedRowCount()){
			case 0:
				JOptionPane.showMessageDialog(this,"No hay ninguna persona seleccionada","Error",JOptionPane.ERROR_MESSAGE);
				break;
			case 1:
				//damos de alta al nuevo interno
				
				String dni=(String) dtmPersona.getValueAt(t.getSelectedRow(),	 0);
				String[]palabrasEnDni=dni.split("-");
				
				PersonaDao pd=new PersonaDao();
				List<Object> lista=pd.listaPersonasPorDni(palabrasEnDni[1]);
				Persona p=(Persona) lista.get(0);
				String palabraCombo=(String)jcbPersonaResponsable.getSelectedItem();
				String[]palabrasEnCombo=palabraCombo.split("//");
				Object[]datos={
						p.getIdpersona(),
						jtfCc.getText(),
						jtfSs.getText(),
						jtfHabitación.getText(),
						palabrasEnCombo[0]						
				};
				InternoDao id=new InternoDao();
				id.addInternoConId(datos);
				
				break;
			default:
				JOptionPane.showMessageDialog(this,"De uno en uno mejor","Error",JOptionPane.ERROR_MESSAGE);
		}	
			
			
			this.dispose();
		}			
	}
	
}


