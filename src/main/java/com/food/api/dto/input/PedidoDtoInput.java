package com.food.api.dto.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDtoInput {

	
	@Valid
	@NotNull
	private RestauranteIdDtoInput restaurante;
	
	@Valid
	@NotNull
	private FormaPagamentoIdDtoInput formaPagamento;
	
	@Valid
	@NotNull
	private EnderecoDtoInput enderecoEntrega;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private List<ItemPedidoDtoInput> itens;
	
}
