package com.food.api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import com.food.api.dto.view.RestauranteView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDto {

	@ApiModelProperty(example = "1")
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;
	
	@ApiModelProperty(example = "12.00")
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDto cozinha;
	
	private Boolean aberto;
	private Boolean ativo;
	private EnderecoDto endereco;
	
}
