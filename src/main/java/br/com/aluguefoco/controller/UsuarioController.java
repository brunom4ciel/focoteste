package br.com.aluguefoco.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.aluguefoco.model.Usuario;
//import br.com.aluguefoco.repository.CampanhaRepository;
//import br.com.aluguefoco.repository.UsuarioRepository;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {
//	
//	@Autowired
//	private CampanhaRepository rs;
//	
//	@Autowired
//	private UsuarioRepository rsUsuario;
			
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public ModelAndView listar(){
		
		ModelAndView model = new ModelAndView("/usuario/index");
//		Iterable<Campanha> campanhas = rs.findAll();
		
		Usuario usuario = new Usuario();
		
		model.addObject("usuario",usuario);
		
		return model;	
	}
	
}
