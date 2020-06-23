package br.com.aluguefoco.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="regra")
public class Regra implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String nome;

	@ManyToMany(mappedBy = "regras")
    private List<Usuario> usuarios;
	
	/**
	 * construtor padrão da classe
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	public Regra() {
		
	}
	
	/**
	 * construtor utilizado para definir valor para um atributo de classe
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	public Regra(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nome;
	}
	
	
}
