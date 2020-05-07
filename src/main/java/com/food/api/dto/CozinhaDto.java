package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.food.api.dto.view.RestauranteView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaDto {

	@ApiModelProperty(example = "1")
	@JsonView(RestauranteView.Resumo.class)
	private Long id;

	@ApiModelProperty(example = "Fogao Mineiro")
	@JsonView(RestauranteView.Resumo.class)
	private String nome;

}
