package com.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.assembler.FormaPagamentoDtoInputDisassembler;
import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.dto.FormaPagamentoDto;
import com.food.api.dto.input.FormaPagamentoDtoInput;
import com.food.domain.model.FormaPagamento;
import com.food.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	private static final Class<FormaPagamentoDto> FORMA_PAGAMENTO_DTO_CLASS = FormaPagamentoDto.class;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private GenericDtoAssembler<FormaPagamento, FormaPagamentoDto> assembler;
	
	@Autowired
	private FormaPagamentoDtoInputDisassembler diassembler;
	

	@GetMapping
	public List<FormaPagamentoDto> listar(){
		return assembler
				.toCollectionRepresentationModel(cadastroFormaPagamentoService.listar(), FORMA_PAGAMENTO_DTO_CLASS);
	}
	
	
	@GetMapping("/{id}")
	public FormaPagamentoDto bucar(@PathVariable Long id){
		return assembler.toRepresentationModel(cadastroFormaPagamentoService.buscar(id), 
						FORMA_PAGAMENTO_DTO_CLASS);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto salvar(@Valid @RequestBody FormaPagamentoDtoInput formaPagamentoInput ){
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.salvar(
				diassembler.toDomainObject(formaPagamentoInput));
		
		return assembler.toRepresentationModel(formaPagamento,FORMA_PAGAMENTO_DTO_CLASS);
	}
	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto atualizar(@PathVariable Long id, @Valid @RequestBody FormaPagamentoDtoInput formaPagamentoInput){
		
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscar(id);
		
		diassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
		
		formaPagamentoAtual = cadastroFormaPagamentoService.salvar(formaPagamentoAtual);
		
		return assembler.toRepresentationModel(formaPagamentoAtual,FORMA_PAGAMENTO_DTO_CLASS);
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id){
		cadastroFormaPagamentoService.excluir(id);
	}
	
}
