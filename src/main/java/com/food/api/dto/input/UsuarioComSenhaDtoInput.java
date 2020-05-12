package com.food.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaDtoInput extends UsuarioDtoInput{
	
	@ApiModelProperty(example = "123", required = true)
	@NotBlank
	private String senha;

}
