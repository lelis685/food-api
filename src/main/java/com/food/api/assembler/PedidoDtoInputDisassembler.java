package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.input.PedidoDtoInput;
import com.food.domain.model.Pedido;

@Component
public class PedidoDtoInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Pedido toDomainObject(PedidoDtoInput pedidoInput) {
		return mapper.map(pedidoInput, Pedido.class);
	}

	public void copyToDomainObject(PedidoDtoInput pedidoInput, Pedido pedido) {
		mapper.map(pedidoInput, pedido);
	}

}
