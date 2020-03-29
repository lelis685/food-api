package com.food.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {

	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	CORPO_REQUISICAO_INVALIDO("/json-invalido", "Requisição inválida"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");      
	
	private static final String API_DOMAIN = "https://food-api.com";
	
	private String title;
	private String uri;
	
	ApiErrorType(String path,String title){
		this.title = title;
		this.uri = API_DOMAIN + path;
	}

}
