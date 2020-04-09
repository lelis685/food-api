package com.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.food.api.dto.EnderecoDto;
import com.food.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		TypeMap<Endereco, EnderecoDto> enderecoToEnderecoDtoTypeMap = 
				mapper.createTypeMap(Endereco.class, EnderecoDto.class);
	
		enderecoToEnderecoDtoTypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(),
				(dest, value) -> dest.getCidade().setEstado(value)
				);
		
		return mapper;
	}

}
