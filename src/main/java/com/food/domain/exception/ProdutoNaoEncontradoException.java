package com.food.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	private static final String MSG_PRODUTO_NAO_ENCONTRADO_EM_RESTAURANTE = "Não existe um cadastro de produto com código %d para o restaurante de código %d";

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
		this(String.format(MSG_PRODUTO_NAO_ENCONTRADO_EM_RESTAURANTE, produtoId, restauranteId));
	}

}