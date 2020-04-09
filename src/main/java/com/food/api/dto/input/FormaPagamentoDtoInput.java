package com.food.api.dto.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoDtoInput {

	@NotBlank
	private String descricao;	
	
}
