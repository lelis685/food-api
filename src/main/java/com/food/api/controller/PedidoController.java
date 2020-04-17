package com.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.assembler.PedidoDtoInputDisassembler;
import com.food.api.dto.PedidoDto;
import com.food.api.dto.PedidoResumoDto;
import com.food.api.dto.input.PedidoDtoInput;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Pedido;
import com.food.domain.service.EmissaoPedidoService;



@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private static final Class<PedidoDto> PEDIDO_DTO_CLASS = PedidoDto.class;
	private static final Class<PedidoResumoDto> PEDIDO_RESUMO_DTO_CLASS = PedidoResumoDto.class;

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;

	@Autowired
	private GenericDtoAssembler<Pedido, PedidoDto> assemblerPedidoDto;

	@Autowired
	private GenericDtoAssembler<Pedido, PedidoResumoDto> assemblerPedidoResumoDto;

	@Autowired
	private PedidoDtoInputDisassembler disassembler;


	@GetMapping
	public MappingJacksonValue listarBaseadoEmCampos(@RequestParam(required = false) String campos) {
		List<Pedido> todosPedidos = emissaoPedidoService.listar();
		List<PedidoResumoDto> todosPedidosDto = assemblerPedidoResumoDto.toCollectionRepresentationModel(todosPedidos, PEDIDO_RESUMO_DTO_CLASS);
		
		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(todosPedidosDto);
		
		SimpleFilterProvider filtersProvider = new SimpleFilterProvider();
		filtersProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
	
		if(StringUtils.isNotBlank(campos)) {
			filtersProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}
		
		pedidosWrapper.setFilters(filtersProvider);
		
		return pedidosWrapper;
	}


	@GetMapping("/{codigoPedido}")
	public PedidoDto buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscar(codigoPedido);
		return assemblerPedidoDto.toRepresentationModel(pedido, PEDIDO_DTO_CLASS);
	}   


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDto emitir(@RequestBody @Valid PedidoDtoInput pedidoInput) {
		try {
			Pedido pedido = disassembler.toDomainObject(pedidoInput);
			emissaoPedidoService.emitir(pedido);
			return assemblerPedidoDto.toRepresentationModel(pedido, PEDIDO_DTO_CLASS);
		} catch (EntidadeNaoEncontradaException  e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}   



}