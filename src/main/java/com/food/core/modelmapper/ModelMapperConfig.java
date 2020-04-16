package com.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.food.api.dto.EnderecoDto;
import com.food.api.dto.input.ItemPedidoDtoInput;
import com.food.domain.model.Endereco;
import com.food.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		TypeMap<Endereco, EnderecoDto> enderecoToEnderecoDtoTypeMap = 
				modelMapper.createTypeMap(Endereco.class, EnderecoDto.class);
		
		modelMapper.createTypeMap(ItemPedidoDtoInput.class, ItemPedido.class)
		.addMappings(mapper -> mapper.skip(ItemPedido::setId));
	
		enderecoToEnderecoDtoTypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(),
				(dest, value) -> dest.getCidade().setEstado(value)
				);
		
		return modelMapper;
	}

}
