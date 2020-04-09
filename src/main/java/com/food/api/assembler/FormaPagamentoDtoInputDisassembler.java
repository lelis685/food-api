package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.dto.input.FormaPagamentoDtoInput;
import com.food.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDtoInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public FormaPagamento toDomainObject(FormaPagamentoDtoInput formaPagamentoInput) {
		return mapper.map(formaPagamentoInput, FormaPagamento.class);
	}

	public void copyToDomainObject(FormaPagamentoDtoInput formaPagamentoInput, FormaPagamento formaPagamento) {
		mapper.map(formaPagamentoInput, formaPagamento);
	}

}
