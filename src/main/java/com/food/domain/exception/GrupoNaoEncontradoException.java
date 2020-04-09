package com.food.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe um cadastro de grupo com código %d";

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public GrupoNaoEncontradoException(Long id) {
		this(String.format(MSG_GRUPO_NAO_ENCONTRADO, id));
	}

}
