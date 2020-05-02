package com.food.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDto {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Sao Paulo")
	private String nome;
	
	private EstadoDto estado;
	
}
