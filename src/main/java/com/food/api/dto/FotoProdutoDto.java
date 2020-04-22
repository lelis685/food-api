package com.food.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoDto {
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
}
