package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.input.GrupoDtoInput;
import com.food.domain.model.Grupo;

@Component
public class GrupoDtoInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Grupo toDomainObject(GrupoDtoInput grupoInput) {
		return mapper.map(grupoInput, Grupo.class);
	}

	public void copyToDomainObject(GrupoDtoInput grupoInput, Grupo grupo) {
		mapper.map(grupoInput, grupo);
	}

}
