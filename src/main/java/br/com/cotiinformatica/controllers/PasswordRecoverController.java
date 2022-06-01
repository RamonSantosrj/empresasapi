package br.com.cotiinformatica.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.repositories.IUsuarioRepository;
import br.com.cotiinformatica.requests.PasswordRecoverPostRequest;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class PasswordRecoverController {

	private static final String ENDPOINT = "/api/password-recover";

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@ApiOperation("Método para realizar a recuperação da senha do usuário.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody PasswordRecoverPostRequest request) {
		return null; // TODO
	}
}
