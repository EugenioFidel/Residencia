package com.Eu.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


	

	@Entity
	@Table(name="categoria")
	public class Categoria {
		@Id
		@GeneratedValue
		@Column(name="idCategoria")
		private int idCategoria;	

		@Column(name="categoria")
		private String categoria;
		
		@OneToMany(mappedBy="categoria")
		private List<Categoria> categorias=new ArrayList<Categoria>(0);
		
		public Categoria(){}

		public Categoria(int idCategoria, String categoria, List<Categoria> categorias) {
			super();
			this.idCategoria = idCategoria;
			this.categoria = categoria;
			this.categorias = categorias;
		}

		public int getIdCategoria() {
			return idCategoria;
		}

		public void setIdCategoria(int idCategoria) {
			this.idCategoria = idCategoria;
		}

		public String getCategoria() {
			return categoria;
		}

		public void setCategoria(String categoria) {
			this.categoria = categoria;
		}

		public List<Categoria> getCategorias() {
			return categorias;
		}

		public void setCategorias(List<Categoria> categorias) {
			this.categorias = categorias;
		}
		
		
	}

