package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.input.UsuarioDtoInput;
import com.food.domain.model.Usuario;

@Component
public class UsuarioDtoInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Usuario toDomainObject(UsuarioDtoInput usuarioInput) {
		return mapper.map(usuarioInput, Usuario.class);
	}

	public void copyToDomainObject(UsuarioDtoInput usuarioInput, Usuario usuario) {
		mapper.map(usuarioInput, usuario);
	}

}
