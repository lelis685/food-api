package com.food.domain.exception;

public class FotoProtudoNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_FOTO_PRODUTO_NAO_ENCONTRADA = 
			"Não existe um cadastro de foto do produto com código %d para o restaurante de código %d";
	
	public FotoProtudoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	
	public FotoProtudoNaoEncontradaException(Long restauranteId, Long produtoId) {
		this(String.format(MSG_FOTO_PRODUTO_NAO_ENCONTRADA, produtoId, restauranteId));
	}
	
}
