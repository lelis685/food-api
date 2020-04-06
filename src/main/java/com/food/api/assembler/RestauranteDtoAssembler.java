package com.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.RestauranteDto;
import com.food.domain.model.Restaurante;

@Component
public class RestauranteDtoAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public RestauranteDto toRepresentationModel(Restaurante restaurante) {
		return mapper.map(restaurante, RestauranteDto.class);
	}
	
	public List<RestauranteDto> toCollectionRepresentationModel(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(rest -> toRepresentationModel(rest))
				.collect(Collectors.toList());
	}

}
