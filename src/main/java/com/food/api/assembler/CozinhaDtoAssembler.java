package com.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.CozinhaDto;
import com.food.domain.model.Cozinha;

@Component
public class CozinhaDtoAssembler {

	@Autowired
	private ModelMapper mapper;
	
	public CozinhaDto toRepresentationModel(Cozinha cozinha) {
		return mapper.map(cozinha, CozinhaDto.class);
	}
	
	public List<CozinhaDto> toCollectionRepresentationModel(List<Cozinha> cozinhas){
		return cozinhas.stream()
				.map(cozinha -> toRepresentationModel(cozinha))
				.collect(Collectors.toList());
	}
	
}
