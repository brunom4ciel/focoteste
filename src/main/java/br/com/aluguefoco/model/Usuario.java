package br.com.aluguefoco.model;

import java.io.Serializable;
//import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="usuario")
public class Usuario implements UserDetails, Serializable{
	
private static final long serialVersionUID = 1L;
	
	
	@Id
	@NotEmpty
	private String email;

    @NotNull
    @Size(min = 3, max = 255, message = "{nome.size}")
	private String nome;
	
	@NotEmpty
	private String senha;
	
	
	@NotEmpty
	private String cpf;
	
	@NotEmpty
	private String dataNascimento;
	
		
	private String telefone;	
	
	
	private boolean concorda;
	
	@ManyToMany
	@JoinTable( 
	        name = "usuarios_regras", 
	        joinColumns = @JoinColumn(
	          name = "usuario_email", referencedColumnName = "email"), 
	        inverseJoinColumns = @JoinColumn(
	          name = "regra_nome", referencedColumnName = "nome"))
	private Set<Regra> regras = new HashSet<>();
		
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
	@JoinTable( 
	        name = "usuarios_campanhas", 
	        joinColumns = @JoinColumn(
	          name = "usuario_email", referencedColumnName = "email"), 
	        inverseJoinColumns = @JoinColumn(
	          name = "campanha_id", referencedColumnName = "id"))
    private Set<Campanha> campanhas = new HashSet<>();		 
	
	/**
	 * construtor padrão da classe
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	public Usuario() {
		
	}
	
	/**
	 * construtor utilizado para definir valores para os atributos da classe
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	public Usuario(String email, String nome, String senha, String cpf, String DataNascimento, String telefone, boolean concorda) {
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		this.cpf = cpf;
		this.dataNascimento = DataNascimento;
		this.telefone = telefone;
		this.concorda = concorda;
	}
	
	
	public boolean isConcorda() {
		return concorda;
	}

	public void setConcorda(boolean concorda) {
		this.concorda = concorda;
	}

	public Set<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(Set<Campanha> campanhas) {
		this.campanhas = campanhas;
	}

	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}




	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

 

	public Set<Regra> getRegras() {
		return regras;
	}

	public void setRegras(Set<Regra> regras) {
		this.regras = regras;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return (Collection<? extends GrantedAuthority>) this.regras;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	

}
