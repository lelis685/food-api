package com.food.api.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDtoInput {

	@NotNull
	private Long produtoId;

	@NotNull
	@PositiveOrZero
	private Long quantidade;
	
	private String observacao;
	
}
