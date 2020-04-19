package com.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.assembler.PedidoDtoInputDisassembler;
import com.food.api.dto.PedidoDto;
import com.food.api.dto.PedidoResumoDto;
import com.food.api.dto.input.PedidoDtoInput;
import com.food.core.data.PageableTranslator;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Pedido;
import com.food.domain.repository.PedidoRepository;
import com.food.domain.repository.filter.PedidoFilter;
import com.food.domain.service.EmissaoPedidoService;
import com.food.infrastructure.repository.specification.PedidosSpec;
import com.google.common.collect.ImmutableMap;



@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private static final Class<PedidoDto> PEDIDO_DTO_CLASS = PedidoDto.class;
	private static final Class<PedidoResumoDto> PEDIDO_RESUMO_DTO_CLASS = PedidoResumoDto.class;

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private GenericDtoAssembler<Pedido, PedidoDto> assemblerPedidoDto;

	@Autowired
	private GenericDtoAssembler<Pedido, PedidoResumoDto> assemblerPedidoResumoDto;

	@Autowired
	private PedidoDtoInputDisassembler disassembler;

	@GetMapping
	public Page<PedidoResumoDto> listar(PedidoFilter filtro, Pageable pageable) {

		pageable = traduzirPageable(pageable);

		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidosSpec.usandoFiltro(filtro), pageable);

		List<PedidoResumoDto> pedidosResumoDto = 
				assemblerPedidoResumoDto.toCollectionRepresentationModel(pedidosPage.getContent(), PEDIDO_RESUMO_DTO_CLASS);

		Page<PedidoResumoDto> pedidosResumoDtoPage = new PageImpl<>(pedidosResumoDto, pageable, pedidosPage.getTotalElements());

		return pedidosResumoDtoPage;

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


	private Pageable traduzirPageable(Pageable pageable) {
		ImmutableMap<String, String> mapeamento = ImmutableMap.of(
				"nomeCliente", "cliente.nome");
		return PageableTranslator.translate(pageable, mapeamento);
	}


}