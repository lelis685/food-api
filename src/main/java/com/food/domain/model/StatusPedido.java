package com.food.domain.model;

import java.util.Arrays;
import java.util.List;


public enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);

	private String descricao;
	private List<StatusPedido> statusAnteriores;

	StatusPedido(String descricao, StatusPedido ...statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}

	public String getDescricao() {
		return descricao;
	}
	
	/**
	 * Metodo para nao permitir alterar STATUS que nao esteja no statusAnteriores
	 * @param novoStatusPedido
	 * @return
	 */
	public boolean naoPodeAlterarPara(StatusPedido novoStatusPedido) {
		return !novoStatusPedido.statusAnteriores.contains(this);
	}

}
