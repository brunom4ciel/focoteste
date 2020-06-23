package br.com.aluguefoco.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.aluguefoco.model.Campanha;
import br.com.aluguefoco.model.Usuario;
import br.com.aluguefoco.repository.CampanhaRepository;
import br.com.aluguefoco.repository.UsuarioRepository;

/**
 * Implementa as funcionalidades do controlador campanha
 *
 * @author Bruno Maciel <brunom4ciel at gmail.com>
 * @version $Revision: 1 $
 */
@Controller
@RequestMapping(value = "/campanha")
public class CampanhaController {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private CampanhaRepository campanhaRes;
	
	@Autowired
	private UsuarioRepository usuarioRes;
	
	
	/**
	 * Faz o mapeamento da url para listar campanhas
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public ModelAndView campanhas(){
		
		ModelAndView model = new ModelAndView("/campanha/index");
		Iterable<Campanha> campanhas = campanhaRes.findAll();
		model.addObject("campanhas",campanhas);
		
		return model;	
	}
	
	/**
	 * Faz o mapeamento da url para ver uma campanha
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ModelAndView campanha(@PathVariable int id, Campanha campanha) {
		
        ModelAndView model = new ModelAndView("/campanha/form");
        model.addObject("campanha", campanhaRes.findById(id));  

        return model;
    }
	
	/**
	 * Faz o mapeamento da url para criar uma nova campanha
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
    @RequestMapping(value ="/novo" , method = RequestMethod.GET)
    public ModelAndView novo() {

    	ModelAndView model = new ModelAndView("/campanha/form");
        model.addObject("campanha", new Campanha());
        
        return model;
    }
	
    /**
	 * Faz o mapeamento da url para editar uma campanha
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
    @RequestMapping(value="/editar/{id}", method=RequestMethod.POST)
	public String editar(Campanha campanha, BindingResult result, RedirectAttributes attributes){
    	
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/campanha/form";
		}
	
		campanhaRes.save(campanha);
		
		attributes.addFlashAttribute("mensagem", "Campanha atualizada com sucesso!");
		return "redirect:/campanha/" + campanha.getId();
	}
    
    /**
	 * Faz o mapeamento da url para deletar uma campanha
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
    @RequestMapping(value="/deletar/{id}", method=RequestMethod.GET)
	public String deletar(Campanha campanha){
		
    	campanhaRes.delete(campanha);
		
		return "redirect:/campanha/index";
	}
    
    
    
    
    
    
    
    
    
    
    /**
	 * Faz o mapeamento da url para listar voluntarios pertencentes a uma campanha
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
    @RequestMapping(value="/{id}/voluntario", method = RequestMethod.GET)
    public ModelAndView voluntarios(@PathVariable int id, Campanha campanha) {
        ModelAndView model = new ModelAndView("/voluntario/index");

        model.addObject("campanha", campanhaRes.findById(id));  

        Iterable<Usuario> usuarios = usuarioRes.findByCampanhasId(campanha.getId());
        model.addObject("usuarios", usuarios);
    
        return model;
    }
	
	
    /**
	 * Faz o mapeamento da url para inscricao em campanha
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	@GetMapping(value="/{id}/voluntario/inscricao")
    public ModelAndView voluntario(@PathVariable int id, Campanha campanha) {

		campanha = campanhaRes.findById(id);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = "";
        
        if (principal instanceof UserDetails) {
        	email = ((UserDetails)principal).getUsername();
        } else {
        	email = principal.toString();
        }
        
        Usuario usuario = usuarioRes.findByEmail(email);
                
        String documento = campanha.getDocumento();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        
        documento = documento.replaceAll("@nome", usuario.getNome());
        documento = documento.replaceAll("@dataNascimento", usuario.getDataNascimento());
        documento = documento.replaceAll("@telefone", usuario.getTelefone());
        documento = documento.replaceAll("@email", usuario.getEmail());
        documento = documento.replaceAll("@cpf", usuario.getCpf());
        documento = documento.replaceAll("@dataEnvio", dateFormat.format(date));
        
        campanha.setDocumento(documento);
        
                
        ModelAndView model = new ModelAndView("/voluntario/form");

        model.addObject("campanha", campanha);
        
		return model;
    }
	
	/**
	 * Faz o mapeamento da url para confirmar inscricao em campanha
	 *
	 * @author Bruno Maciel <brunom4ciel at gmail.com>
	 * @version $Revision: 1 $
	 */
	@RequestMapping(value="/{id}/voluntario/inscricao", method = RequestMethod.POST)
    public String voluntarioAdicionar(@PathVariable int id, Campanha campanha) {

		campanha = campanhaRes.findById(id);
            
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = "";
        
        if (principal instanceof UserDetails) {
        	email = ((UserDetails)principal).getUsername();
        } else {
        	email = principal.toString();
        }
        
        Usuario usuario = usuarioRes.findByEmail(email);
                
        campanha.getUsuarios().add(usuario);		
		usuario.getCampanhas().add(campanha);
		
		usuarioRes.save(usuario);
		
		if(usuario.isConcorda() == true) {
			
			String documento = campanha.getDocumento();
	        
	        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        Date date = new Date();
	        
	        documento = documento.replaceAll("@nome", usuario.getNome());
	        documento = documento.replaceAll("@dataNascimento", usuario.getDataNascimento());
	        documento = documento.replaceAll("@telefone", usuario.getTelefone());
	        documento = documento.replaceAll("@email", usuario.getEmail());
	        documento = documento.replaceAll("@cpf", usuario.getCpf());
	        documento = documento.replaceAll("@dataEnvio", dateFormat.format(date));
	        		
	       
	        SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(usuario.getEmail());
	        msg.setTo(campanha.getEmail());

	        msg.setSubject((campanha.getEmailTitulo() == null || campanha.getEmailTitulo().trim().isEmpty() ? "Voluntário em campanha":campanha.getEmailTitulo()));
	        msg.setText(documento);

	        javaMailSender.send(msg);        

		}		
		
		return "redirect:/campanha/" + id + "/voluntario";
    }
	
   
}
