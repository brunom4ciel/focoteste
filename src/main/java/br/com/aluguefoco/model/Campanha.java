package br.com.aluguefoco.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * DTO campanha
 *
 * @author Bruno Maciel <brunom4ciel at gmail.com>
 * @version $Revision: 1 $
 */
@Entity
@Table(name="campanha")
public class Campanha implements Serializable{//Data Transfer Object
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;

	@NotEmpty
	private String nome;
	

	private long quantidadeMaximaVoluntarios;

	
	private String organizacao;
	
	@Column(columnDefinition = "TEXT")
	private String detalhe;
	
	private String email;
	
	private String emailTitulo;
	
	@Column(columnDefinition = "TEXT")
	private String documento;
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "campanhas")
    private Set<Usuario> usuarios = new HashSet<>();
	

	/**
	 * construtor padrão da classe
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	public Campanha() {
		
	}
	
	/**
	 * construtor utilizado para definir valores para os atributos da classe
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	public Campanha(String nome, String organizacao, long quantidade) {
		this.nome = nome;
		this.organizacao = organizacao;
		this.quantidadeMaximaVoluntarios = quantidade;
	}
	
	
	
	public String getEmailTitulo() {
		return emailTitulo;
	}

	public void setEmailTitulo(String emailTitulo) {
		this.emailTitulo = emailTitulo;
	}

	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrganizacao() {
		return organizacao;
	}

	public void setOrganizacao(String organizacao) {
		this.organizacao = organizacao;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getQuantidadeMaximaVoluntarios() {
		return quantidadeMaximaVoluntarios;
	}

	public void setQuantidadeMaximaVoluntarios(long quantidadeMaximaVoluntarios) {
		this.quantidadeMaximaVoluntarios = quantidadeMaximaVoluntarios;
	}

	 	
}
