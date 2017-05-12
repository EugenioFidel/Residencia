package com.Eu.paneles;

import java.awt.Color;
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

import com.Eu.dao.EstanciaDao;
import com.Eu.dao.ObservacionDao;
import com.Eu.formularios.AltaEstancia;
import com.Eu.formularios.AltaObservacion;
import com.Eu.formularios.FrmPrincipal;
import com.Eu.model.Estancia;
import com.Eu.model.Observacion;

public class PanelBotoneroInternos extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	final static Logger loggeador = Logger.getLogger(PanelBotoneroInternos.class);
	private JButton jbAnhadir,jbBorrar;
	ButtonGroup bg;
	JRadioButton jrbEstancias;
	JRadioButton jrbObservaciones;
	Border blackline;
	
	JLayeredPane jlp;
	
	PanelEstancias pe;
	PanelObservaciones po;
	
	public final int INTERNOS=1;
	
	public PanelBotoneroInternos() {
		// TODO Auto-generated constructor stub
		this.setLayout(new GridLayout(1,32,10,10));
		
		jbAnhadir=new JButton("Añadir");
		jbBorrar=new JButton("Borrar");
		
		jbAnhadir.addActionListener(this);
		jbBorrar.addActionListener(this);
		
		bg=new ButtonGroup();
		jrbEstancias=new JRadioButton("Est.");
		jrbObservaciones=new JRadioButton("Obs.");
		
		jrbEstancias.addActionListener(this);
		jrbObservaciones.addActionListener(this);
		
		bg.add(jrbEstancias);
		bg.add(jrbObservaciones);
		
		jrbEstancias.setSelected(true);
					
		this.add(jbAnhadir);
		this.add(jbBorrar);	
		this.add(jrbEstancias);
		this.add(jrbObservaciones);
		
		blackline = BorderFactory.createLineBorder(Color.black);
		
		Border b=BorderFactory.createTitledBorder(blackline,"Internos", TitledBorder.LEFT,TitledBorder.TOP);
		this.setBorder(b);		
	}
	
	public void actionPerformed(ActionEvent e) {
		//necesitamos el formulario principal
		JRootPane jrp=this.getRootPane();
		FrmPrincipal f=(FrmPrincipal) jrp.getParent();
		
		jlp=f.getJlpFuncionalidades();
		
		pe=f.getPe();
		po=f.getPo();	
		
		if(e.getSource().getClass().equals(JButton.class)){
			if(e.getSource().equals(jbAnhadir)){
				if(jrbEstancias.isSelected()){
					loggeador.debug("alta de estancia");
					AnhadirEstancia();
				}else{
					loggeador.debug("alta de observacion");
					AnhadirObservacion();
				}
			}else{
				System.out.println("pulso borrar");
				if(jrbEstancias.isSelected()){
					loggeador.debug("Borrar estancia");
					BorrarEstancia();
				}else{
					loggeador.debug("borrar observacion");
					BorrarObservacion();
				}
			}	
		}else{
			if(e.getSource().equals(jrbEstancias)){
				jlp.moveToBack(po);
				jlp.moveToFront(pe);
			}else{
				jlp.moveToBack(pe);
				jlp.moveToFront(po);
			}
		}
		
			
	}
	
	private void AnhadirEstancia() {
		// TODO Auto-generated method stub
		DefaultTableModel dtm=(DefaultTableModel)pe.getJtEstancias().getModel();
		AltaEstancia ae=new AltaEstancia(pe.getI(),dtm);
		ae.setVisible(true);	
	}
	
	private void AnhadirObservacion() {
		DefaultTableModel dtm=(DefaultTableModel)po.getJtObservaciones().getModel();
		AltaObservacion ao=new AltaObservacion(po.getI(),dtm);
		ao.setVisible(true);		
	}
	
	private void BorrarObservacion() {
		// TODO Auto-generated method stub
		JTable jt=po.getJtObservaciones();
		DefaultTableModel dtm=(DefaultTableModel)jt.getModel();
		
		ObservacionDao od=new ObservacionDao();
		Observacion o=od.getObservacionById(Integer.parseInt(dtm.getValueAt(jt.getSelectedRow(), 0).toString()));
		od.deleteObservacion(o);
		dtm.removeRow(jt.getSelectedRow());
	}
	
	private void BorrarEstancia() {
		// TODO Auto-generated method stub
		JTable jt=pe.getJtEstancias();
		DefaultTableModel dtm=(DefaultTableModel)jt.getModel();
		
		EstanciaDao ed=new EstanciaDao();
		Estancia e=ed.getEstanciaById(Integer.parseInt(dtm.getValueAt(jt.getSelectedRow(), 0).toString()));
		ed.deleteEstancia(e);
		dtm.removeRow(jt.getSelectedRow());
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

	public JRadioButton getjrbEstancias() {
		return jrbEstancias;
	}

	public void setjrbEstancias(JRadioButton jrbEstancias) {
		this.jrbEstancias = jrbEstancias;
	}

	public JRadioButton getjrbObservaciones() {
		return jrbObservaciones;
	}

	public void setjrbObservaciones(JRadioButton jrbObservaciones) {
		this.jrbObservaciones = jrbObservaciones;
	}		
}
