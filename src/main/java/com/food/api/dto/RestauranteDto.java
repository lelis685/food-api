package com.food.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDto {

	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDto cozinha;
	private Boolean aberto;
	private Boolean ativo;
	private EnderecoDto endereco;
}
