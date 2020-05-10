package com.food.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoDtoInput {

	@ApiModelProperty(example = "Sao Paulo", required = true)
	@NotBlank
	private String nome;
	
}
