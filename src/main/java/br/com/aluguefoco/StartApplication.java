package br.com.aluguefoco;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.aluguefoco.model.Campanha;
import br.com.aluguefoco.model.Usuario;
import br.com.aluguefoco.model.Regra;
import br.com.aluguefoco.repository.CampanhaRepository;
import br.com.aluguefoco.repository.UsuarioRepository;
import br.com.aluguefoco.repository.RegraRepository;

/**
 * Inicialização da aplicação
 *
 * @author Bruno Maciel <brunom4ciel at gmail.com>
 * @version $Revision: 1 $
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"br.com.aluguefoco","controller"})
public class StartApplication {
	
	/**
	 * Método de inicialização da aplicação
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);

	}
	
	/**
	 * Carrega dados na base de dados da aplicação
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	@Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRes, 
								RegraRepository regraRes, 
								CampanhaRepository campanhaRes) {
		
		String senha = new BCryptPasswordEncoder().encode("123");
		
		Regra regra1 = new Regra("ADMIN");
		Regra regra2 = new Regra("USER");

		
		Usuario usuario = new Usuario("brunom4ciel@gmail.com",
				"bruno",  
				senha, 
				"9879798798",
				"12/12/2020",
				"9999999",
				true);
		
		Usuario usuario2 = new Usuario("brunom4ciel2@gmail.com",
				"bruno",  
				senha, 
				"9879798798",
				"12/12/2020",
				"9999999",
				true);
	
		Campanha campanha1 = new Campanha("São João 2020 a", 
							"Igreja Matriz",20);		
		
		campanha1.getUsuarios().add(usuario);
		
		usuario.getCampanhas().add(campanha1);
		
		usuario.getRegras().add(regra1);
		usuario.getRegras().add(regra2);
		
		usuario2.getRegras().add(regra2);
		
        return args -> {        	
        	usuarioRes.save(usuario);
        	usuarioRes.save(usuario2);
        };
    }
	
}
