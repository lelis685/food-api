package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.input.EstadoDtoInput;
import com.food.domain.model.Estado;

@Component
public class EstadoDtoInputDisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Estado toDomainObject(EstadoDtoInput EstadoInput) {
		return mapper.map(EstadoInput, Estado.class);
	}
	
	public void copyToDomainObject(EstadoDtoInput estadoInput, Estado estado) {
		mapper.map(estadoInput, estado);
	}
	

}
