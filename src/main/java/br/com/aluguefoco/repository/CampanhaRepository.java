package br.com.aluguefoco.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.aluguefoco.model.Campanha;

public interface CampanhaRepository extends CrudRepository<Campanha, String>{
	
	Campanha findById(long id);
}
