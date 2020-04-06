package com.food.api.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaIdDtoInput {

	@NotNull
	private Long id;
	
}
