package com.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.CidadeDto;
import com.food.domain.model.Cidade;

@Component
public class CidadeDtoAssembler {

	@Autowired
	private ModelMapper mapper;
	
	public CidadeDto toRepresentationModel(Cidade cidade) {
		return mapper.map(cidade, CidadeDto.class);
	}
	
	public List<CidadeDto> toCollectionRepresentationModel(List<Cidade> cidades){
		return cidades.stream()
				.map(cidade -> toRepresentationModel(cidade))
				.collect(Collectors.toList());
	}
	
}
