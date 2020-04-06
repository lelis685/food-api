package com.food.api.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdDtoInput {

	@NotNull
	private Long id;
	
}
