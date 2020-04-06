package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.input.CozinhaDtoInput;
import com.food.domain.model.Cozinha;

@Component
public class CozinhaDtoInputDisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Cozinha toDomainObject(CozinhaDtoInput cozinhaInput) {
		return mapper.map(cozinhaInput, Cozinha.class);
	}
	
	public void copyToDomainObject(CozinhaDtoInput cozinhaInput, Cozinha cozinha) {
		mapper.map(cozinhaInput, cozinha);
	}
	

}
