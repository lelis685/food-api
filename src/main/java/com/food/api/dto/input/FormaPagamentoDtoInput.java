package com.food.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoDtoInput {

	@ApiModelProperty(example = "Cartao de debito", required = true)
	@NotBlank
	private String descricao;	
	
}
