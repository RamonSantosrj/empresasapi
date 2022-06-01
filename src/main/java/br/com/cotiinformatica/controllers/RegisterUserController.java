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
import br.com.cotiinformatica.requests.RegisterUserPostRequest;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class RegisterUserController {

	private static final String ENDPOINT = "/api/register-user";

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@ApiOperation("Método para realizar o cadastro do usuário da API.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody RegisterUserPostRequest request) {

		try {
			
			//verificar se o email informado já está cadastrado no banco de dados
			if(usuarioRepository.findByEmail(request.getEmail()) != null)
				//HTTP 400 -> BAD REQUEST
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("O email '"+ request.getEmail() +"' já está cadastrado no sistema, tente outro.");
			
			Usuario usuario = new Usuario();
			
			usuario.setNome(request.getNome());
			usuario.setEmail(request.getEmail());
			usuario.setSenha(request.getSenha());
			
			usuarioRepository.save(usuario);
			
			//HTTP 201 -> CREATED
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Parabéns '"+ usuario.getNome() +"', sua conta foi criada com sucesso!");
		}
		catch(Exception e) {
			//HTTP 500 -> INTERNAL SERVER ERROR
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
}
