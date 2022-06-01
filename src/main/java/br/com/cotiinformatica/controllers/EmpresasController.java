package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.entities.Empresa;
import br.com.cotiinformatica.repositories.IEmpresaRepository;
import br.com.cotiinformatica.requests.EmpresasPostRequest;
import br.com.cotiinformatica.requests.EmpresasPutRequest;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class EmpresasController {

	private static final String ENDPOINT = "/api/empresas";

	@Autowired // A interface será inicializada automaticamente
	private IEmpresaRepository empresaRepository;

	@ApiOperation("Método para realizar o cadastro de uma empresa.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody EmpresasPostRequest request) {

		try {
			
			//verificar se o cnpj informado já pertence a alguma empresa cadastrada
			if(empresaRepository.findByCnpj(request.getCnpj()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("O cnpj '" + request.getCnpj() + "' já está cadastrado no sistema, tente outro.");
			}
			
			//verificar se a razaoSocial informada já pertence a alguma empresa cadastrada
			if(empresaRepository.findByRazaoSocial(request.getRazaoSocial()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("A razão social '" + request.getRazaoSocial() + "' já está cadastrada no sistema, tente outra.");
			}

			Empresa empresa = new Empresa();

			empresa.setNomeFantasia(request.getNomeFantasia());
			empresa.setRazaoSocial(request.getRazaoSocial());
			empresa.setCnpj(request.getCnpj());

			empresaRepository.save(empresa);

			return ResponseEntity.status(HttpStatus.CREATED) // HTTP 201
					.body("Empresa cadastrada com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("Método para realizar a atualização dos dados de uma empresa.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody EmpresasPutRequest request) {

		try {

			//pesquisar a empresa pelo id
			Optional<Empresa> consulta = empresaRepository.findById(request.getIdEmpresa());
			
			//verificar se a empresa não foi encontrada
			if(consulta.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Nenhuma empresa foi encontrada, por favor verifique o id informado.");
			}
			
			//capturar a empresa
			Empresa empresa = consulta.get();
			
			//modificando os dados da empresa
			empresa.setNomeFantasia(request.getNomeFantasia());
			empresaRepository.save(empresa);

			return ResponseEntity.status(HttpStatus.CREATED) // HTTP 201
					.body("Empresa atualizada com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("Método para realizar a exclusão de uma empresa.")
	@RequestMapping(value = ENDPOINT + "/{idEmpresa}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idEmpresa") Integer idEmpresa) {

		try {

			//pesquisar a empresa pelo id
			Optional<Empresa> consulta = empresaRepository.findById(idEmpresa);
			
			//verificar se a empresa não foi encontrada
			if(consulta.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Nenhuma empresa foi encontrada, por favor verifique o id informado.");
			}
			
			//capturar a empresa
			Empresa empresa = consulta.get();

			//excluindo a empresa
			empresaRepository.delete(empresa);

			return ResponseEntity.status(HttpStatus.CREATED) // HTTP 201
					.body("Empresa excluída com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("Método para consultar todas as empresas cadastradas.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	public ResponseEntity<List<Empresa>> getAll() {

		try {
						
			List<Empresa> empresas = (List<Empresa>) empresaRepository.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(empresas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation("Método para consultar 1 empresa baseado no ID (identificador da empresa).")
	@RequestMapping(value = ENDPOINT + "/{idEmpresa}", method = RequestMethod.GET)
	public ResponseEntity<Empresa> getById(@PathVariable("idEmpresa") Integer idEmpresa) {

		try {

			Optional<Empresa> consulta = empresaRepository.findById(idEmpresa);

			//verificando se uma empresa foi encontrada
			if(consulta.isPresent()) {
				Empresa empresa = consulta.get();
				return ResponseEntity.status(HttpStatus.OK).body(empresa);
			}
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
