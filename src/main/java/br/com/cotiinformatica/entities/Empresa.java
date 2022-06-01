package br.com.cotiinformatica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "EMPRESA")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDEMPRESA")
	private Integer idEmpresa;

	@Column(name = "NOMEFANTASIA", length = 150, nullable = false)
	private String nomeFantasia;

	@Column(name = "RAZAOSOCIAL", length = 150, nullable = false, unique = true)
	private String razaoSocial;

	@Column(name = "CNPJ", length = 20, nullable = false, unique = true)
	private String cnpj;

}
