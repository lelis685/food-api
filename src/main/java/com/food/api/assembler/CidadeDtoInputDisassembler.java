package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.input.CidadeDtoInput;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;

@Component
public class CidadeDtoInputDisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	
	public Cidade toDomainObject(CidadeDtoInput cidadeInput) {
		return mapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeDtoInput cidadeInput, Cidade cidade) {
		cidade.setEstado(new Estado()); // para evitar exception caso tente mudar de estado
		mapper.map(cidadeInput, cidade);
	}
	
}
