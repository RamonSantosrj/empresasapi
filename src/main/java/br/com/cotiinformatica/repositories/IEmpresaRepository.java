package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.Empresa;

public interface IEmpresaRepository extends CrudRepository<Empresa, Integer> {

	@Query("select e from Empresa e where e.cnpj = :param1")
	Empresa findByCnpj(@Param("param1") String cnpj) throws Exception;

	@Query("select e from Empresa e where e.razaoSocial = :param1")
	Empresa findByRazaoSocial(@Param("param1") String razaoSocial) throws Exception;
}