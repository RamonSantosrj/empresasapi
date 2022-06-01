package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Integer> {

	@Query("select u from Usuario u where u.email = :param1")
	Usuario findByEmail(@Param("param1") String email) throws Exception;

	@Query("select u from Usuario u where u.email = :param1 and u.senha = :param2")
	Usuario findByEmailAndSenha(@Param("param1") String email, @Param("param2") String senha) throws Exception;
}
