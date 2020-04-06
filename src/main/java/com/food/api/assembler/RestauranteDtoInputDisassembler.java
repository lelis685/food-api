package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.input.RestauranteDtoInput;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;

@Component
public class RestauranteDtoInputDisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Restaurante toDomainObject(RestauranteDtoInput restauranteInput) {
		return mapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteDtoInput restauranteInput, Restaurante restaurante) {
		// para evitar org.hibernate.HibernateException: identifier of an instance of com.food.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		mapper.map(restauranteInput, restaurante);
	}
	

}
