package br.com.cotiinformatica.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmpresasPostRequest {

	private String nomeFantasia;
	private String razaoSocial;
	private String cnpj;

}
