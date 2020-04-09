package com.food.api.dto.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaDtoInput extends UsuarioDtoInput{
	
	@NotBlank
    private String senha;

}
