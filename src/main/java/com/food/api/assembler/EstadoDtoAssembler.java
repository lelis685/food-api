package com.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.EstadoDto;
import com.food.domain.model.Estado;

@Component
public class EstadoDtoAssembler {

	@Autowired
	private ModelMapper mapper;
	
	public EstadoDto toRepresentationModel(Estado estado) {
		return mapper.map(estado, EstadoDto.class);
	}
	
	public List<EstadoDto> toCollectionRepresentationModel(List<Estado> estados){
		return estados.stream()
				.map(estado -> toRepresentationModel(estado))
				.collect(Collectors.toList());
	}
	
}
