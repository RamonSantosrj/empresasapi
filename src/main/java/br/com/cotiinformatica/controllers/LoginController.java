package br.com.cotiinformatica.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.IUsuarioRepository;
import br.com.cotiinformatica.requests.LoginPostRequest;
import br.com.cotiinformatica.security.TokenSecurity;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class LoginController {

	private static final String ENDPOINT = "/api/login";

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@ApiOperation("Método para realizar a autenticação do usuário da API.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody LoginPostRequest request) {

		try {
			
			//buscar o usuário no banco de dados através do email e da senha
			Usuario usuario = usuarioRepository.findByEmailAndSenha(request.getEmail(), request.getSenha());
			
			//verificar se o usuário não foi encontrado
			if(usuario == null)
				//HTTP 401 (UNAUTHORIZED)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("Acesso negado. Email e senha inválidos.");
			
			//se o usuário for encontrado, iremos gerar o token de autenticação
			String token = TokenSecurity.generateToken(usuario.getEmail());
				
			return ResponseEntity.status(HttpStatus.OK).body(token);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
}
