package com.food.api.openapi.model;

import java.math.BigDecimal;

import com.food.api.dto.CozinhaDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteBasicoDto")
@Getter
@Setter
public class RestauranteBasicoDtoOpenApi {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;
	
	@ApiModelProperty(example = "12.00")
	private BigDecimal taxaFrete;
	
	private CozinhaDto cozinha;
	
}
