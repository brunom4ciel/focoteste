package br.com.aluguefoco.security;

import br.com.aluguefoco.model.Regra;
import br.com.aluguefoco.model.Usuario;
import  br.com.aluguefoco.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRes;
	
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Usuario usuario = ur.findByEmail(email);
//		
//		
//		System.out.println("email = "+email);
//		
//		if(usuario == null){
//			throw new UsernameNotFoundException("Usuario não encontrado!");
//		}
//		
//		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
//	}
	
	
	@Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRes.findByEmail(username);
        if (usuario == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Regra regra : usuario.getRegras()){
            grantedAuthorities.add(new SimpleGrantedAuthority(regra.getNome()));
        }

        return new User(usuario.getUsername(), usuario.getPassword(), grantedAuthorities);
    }
	
	

}
