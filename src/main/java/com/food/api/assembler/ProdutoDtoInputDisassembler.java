package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.input.ProdutoDtoInput;
import com.food.domain.model.Produto;

@Component
public class ProdutoDtoInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Produto toDomainObject(ProdutoDtoInput produtoDtoInput) {
		return mapper.map(produtoDtoInput, Produto.class);
	}

	public void copyToDomainObject(ProdutoDtoInput produtoDtoInput, Produto produto) {
		mapper.map(produtoDtoInput, produto);
	}

}
