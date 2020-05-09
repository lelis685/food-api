package com.food.api.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDtoInput {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long produtoId;

	@ApiModelProperty(example = "2", required = true)
	@NotNull
	@PositiveOrZero
	private Long quantidade;
	
	@ApiModelProperty(example = "Menos picante, por favor")
	private String observacao;
	
}
