package com.food.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food.api.dto.PedidoDto;
import com.food.api.dto.PedidoResumoDto;
import com.food.api.dto.input.PedidoDtoInput;
import com.food.api.exceptionhandler.ApiError;
import com.food.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoCntrollerOpenApi {


	@ApiOperation("Pesquisa os pedidos")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "campos",
				paramType = "query",
				type = "string",
				value = "Nomes das propriedades para filtrar na resposta, separados por vírgula")
	})
	public Page<PedidoResumoDto> listar(PedidoFilter filtro, Pageable pageable);


	@ApiOperation("Busca um pedido por código")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "campos",
				paramType = "query",
				type = "string",
				value = "Nomes das propriedades para filtrar na resposta, separados por vírgula")
	})
	@ApiResponses({
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = ApiError.class)
	})
	public PedidoDto buscar(
			@ApiParam(value = "Codigo de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
			String codigoPedido);



	@ApiOperation("Registra um pedido")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Pedido registrado"),
	})
	public PedidoDto emitir(
			@ApiParam(name = "corpo", value = "Representação de um novo pedido")
			PedidoDtoInput pedidoInput);


}
