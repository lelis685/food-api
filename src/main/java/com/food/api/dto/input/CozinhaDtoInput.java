package com.food.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDtoInput {
	
	@ApiModelProperty(example = "Fogao Mineiro", required = true)
	@NotBlank
	private String nome;
	
}
