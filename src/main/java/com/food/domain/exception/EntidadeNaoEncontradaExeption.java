package com.food.domain.exception;

public  class EntidadeNaoEncontradaExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	public EntidadeNaoEncontradaExeption(String msg) {
		super(msg);
	}
	
}
