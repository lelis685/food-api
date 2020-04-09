package com.food.api.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeIdDtoInput {
	
	@NotNull
	private Long id;
	
}
