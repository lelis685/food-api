package com.food.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Marco Antonio")
	private String nome;
	
	@ApiModelProperty(example = "marco@gmail.com")
	private String email;
	
}
