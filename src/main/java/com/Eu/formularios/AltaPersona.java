package com.Eu.formularios;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.Eu.dao.PersonaDao;
import com.Eu.model.Persona;

public class AltaPersona extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	//Jlabels y JTextFields
	JLabel jlDni=new JLabel("D.N.I./N.I.E.");
	JTextField jtfLetraCif=new JTextField(2);
	JTextField jtfDni=new JTextField(6);
	JTextField jtfLetraNif=new JTextField(2);
	JLabel jlNombre=new JLabel("Nombre");
	JTextField jtfNombre=new JTextField(20);	
	JLabel jlPrimerApellido=new JLabel("1er apellido");
	JTextField jtfApellidos=new JTextField(20);
	JLabel jlSegundoApellido=new JLabel("2o apellido");
	JTextField jtfSegundoApellido=new JTextField(20);
	JLabel jlFechaNacimiento=new JLabel("Fecha nacimiento");
	JTextField jtfFechaNacimiento=new JTextField(10);
	JLabel jlSexo=new JLabel("Sexo");
	JTextField jtfSexo=new JTextField(2);
	JLabel jlProvincia=new JLabel("Provincia");
	JTextField jtfProvincia=new JTextField(20);
	JLabel jlLocalidad=new JLabel("Localidad");
	JTextField jtfLocalidad=new JTextField(40);
	JLabel jlMunicipio=new JLabel("Municipio");
	JTextField jtfMunicipio=new JTextField(40);
	JLabel jlDomicilio=new JLabel("Domicilio");
	JTextField jtfDomicilio=new JTextField(40);
	JLabel jlCp=new JLabel("Código Postal");
	JTextField jtfCp=new JTextField(5);
	JLabel jlTelefono=new JLabel("Teléfono");
	JTextField jtfTelefono=new JTextField(9);
	JLabel jlTelefonoMovil=new JLabel("Teléfono Movil");
	JTextField jtfTelefonoMovil=new JTextField(9);
	JLabel jlEmail=new JLabel("e-mail");
	JTextField jtfEmail=new JTextField(10);
	
	//Jpanels para distribución
	JPanel jpCif=new JPanel();
	JPanel jpApellidos=new JPanel();
	JPanel jpDomicilio=new JPanel();
	JPanel jpLocalidad=new JPanel();
	JPanel jpMunicipio=new JPanel();
	JPanel jpProvincia=new JPanel();
	JPanel jpTelefono=new JPanel();
	JPanel jpBotonero=new JPanel();
	
	//un boton para grabar y otro para cancelas
	JButton jbGrabar=new JButton("Grabar");
	JButton jbCancelar=new JButton("Cancelar");
	
		
	//un GridBagLayout para el jFrame ofiExplot
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints col=new GridBagConstraints();
	
	//un defaultTableModel para incluir el nuevo interesado en la tabla
	public DefaultTableModel dtmPersona=new DefaultTableModel();	
	
	public AltaPersona(JTable t){
		dtmPersona=(DefaultTableModel) t.getModel();
		setSize(650,450);
		setLocation(200, 200);
		setTitle("Alta de persona en el sistema");
		setLayout(gbl);
		setResizable(true);
		setModal(true);
		
		//Añadimos el ActionListener a los botones
		jbGrabar.addActionListener(this);
		jbCancelar.addActionListener(this);
		
		jpCif.add(jlDni);
		jpCif.add(jtfLetraCif);
		jpCif.add(jtfDni);
		jpCif.add(jtfLetraNif);
		jpCif.add(jlNombre);
		jpCif.add(jtfNombre);
		
		col.gridx=0;
		col.gridy=0;
		col.gridheight=1;
		col.gridwidth=1;
		col.insets=new Insets(5,5,5,5);
		col.anchor=GridBagConstraints.WEST;
		add(jpCif,col);
		
		jpApellidos.setLayout(new GridLayout(4, 2,15,15));
		jpApellidos.add(jlPrimerApellido);
		jpApellidos.add(jtfApellidos);
		jpApellidos.add(jlSegundoApellido);
		jpApellidos.add(jtfSegundoApellido);
		jpApellidos.add(jlFechaNacimiento);
		jpApellidos.add(jtfFechaNacimiento);
		
		col.gridx=0;
		col.gridy=1;
		col.gridheight=1;
		col.gridwidth=1;
		col.anchor=GridBagConstraints.WEST;
		add(jpApellidos,col);
		
		jpDomicilio.add(jlDomicilio);
		jpDomicilio.add(jtfDomicilio);
		
		col.gridx=0;
		col.gridy=2;
		col.gridheight=1;
		col.gridwidth=1;
		col.anchor=GridBagConstraints.WEST;
		add(jpDomicilio,col);
		
		jpLocalidad.add(jlLocalidad);
		jpLocalidad.add(jtfLocalidad);
			
		col.gridx=0;
		col.gridy=3;
		col.gridheight=1;
		col.gridwidth=1;
		col.anchor=GridBagConstraints.WEST;
		add(jpLocalidad,col);
		
		jpMunicipio.add(jlMunicipio);
		jpMunicipio.add(jtfMunicipio);
		col.gridx=0;
		col.gridy=4;
		col.gridheight=1;
		col.gridwidth=1;
		col.anchor=GridBagConstraints.WEST;
		add(jpMunicipio,col);
		
		jpProvincia.add(jlCp);
		jpProvincia.add(jtfCp);
		jpProvincia.add(jlProvincia);
		jpProvincia.add(jtfProvincia);
		
		col.gridx=0;
		col.gridy=5;
		col.gridheight=1;
		col.gridwidth=1;
		col.anchor=GridBagConstraints.WEST;
		add(jpProvincia,col);
		
		jpTelefono.add(jlTelefono);
		jpTelefono.add(jtfTelefono);
		jpTelefono.add(jlTelefonoMovil);
		jpTelefono.add(jtfTelefonoMovil);
		jpTelefono.add(jlEmail);
		jpTelefono.add(jtfEmail);
		
		col.gridx=0;
		col.gridy=6;
		col.gridheight=1;
		col.gridwidth=1;
		col.anchor=GridBagConstraints.WEST;
		add(jpTelefono,col);
		
		jpBotonero.add(jbGrabar);
		jpBotonero.add(jbCancelar);
		
		col.gridx=0;
		col.gridy=7;
		col.gridheight=1;
		col.gridwidth=1;
		col.anchor=GridBagConstraints.CENTER;
		add(jpBotonero,col);		
	
	}

	public void actionPerformed(ActionEvent pulsacion) {
		if(pulsacion.getSource().equals(jbCancelar)){
			this.dispose();
		}else{			
			try {
				//creamos una nueva persona
				Persona p=new Persona(jtfLetraCif.getText().toUpperCase().charAt(0),
				jtfDni.getText().toUpperCase(),
				jtfLetraNif.getText().toUpperCase().charAt(0),
				jtfNombre.getText().toUpperCase(),
				jtfApellidos.getText().toUpperCase(),
				jtfSegundoApellido.getText().toUpperCase(),
				formatter.parse(jtfFechaNacimiento.getText()),
				jtfDomicilio.getText().toUpperCase(),
				jtfLocalidad.getText().toUpperCase(),
				jtfMunicipio.getText().toUpperCase(),
				jtfCp.getText().toUpperCase(),
				jtfProvincia.getText().toUpperCase(),
				jtfTelefono.getText().toUpperCase(),
				jtfTelefonoMovil.getText().toUpperCase(),
				jtfEmail.getText().toUpperCase());
				
				PersonaDao pd=new PersonaDao();
				pd.addPersona(p);
				
				dtmPersona.addRow(Persona.PersonaAArray(p));	
			} catch (ParseException e) {
				// TODO Auto-generated catch block				
				e.printStackTrace();
			}				
			this.dispose();
		}			
	}
	
}


