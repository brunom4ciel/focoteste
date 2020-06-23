package br.com.aluguefoco.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.aluguefoco.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String>{

	Usuario findByEmail(String email);
	
	Iterable<Usuario> findByCampanhasId(long campanhaId);
	
}
