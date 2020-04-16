package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.food.api.dto.view.RestauranteView;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaDto {

	@JsonView(RestauranteView.Resumo.class)
	private Long id;

	@JsonView(RestauranteView.Resumo.class)
	private String nome;

}
