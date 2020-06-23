package br.com.aluguefoco.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.aluguefoco.model.Regra;

public interface RegraRepository extends CrudRepository<Regra, String>{

	Regra findByNome(String nome);
	
}
