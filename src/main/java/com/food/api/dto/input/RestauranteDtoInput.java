package com.food.api.dto.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDtoInput {
	
	@NotBlank
	private String nome;

	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;

	@Valid
	@NotNull
	private CozinhaIdDtoInput cozinha;
	
	@Valid
	@NotNull
	private EnderecoDtoInput endereco;
}
