package com.food.api.dto.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDtoInput {
	
	@ApiModelProperty(example = "Uberlandia", required = true)
	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoIdDtoInput estado;
	
}
