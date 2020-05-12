package com.food.api.openapi.controller;

import java.util.List;

import com.food.api.dto.EstadoDto;
import com.food.api.dto.input.EstadoDtoInput;
import com.food.api.exceptionhandler.ApiError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista os estados")
	public List<EstadoDto> listar();


	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do estado inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = ApiError.class)
	})
	public EstadoDto buscar(
			@ApiParam(value = "ID de um estado", example = "1", required = true)
			Long estadoId);


	@ApiOperation("Cadastra um estado")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Estado cadastrado"),
	})
	public EstadoDto adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
			EstadoDtoInput estadoInput);


	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Estado atualizado"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = ApiError.class)
	})
	public  EstadoDto atualizar(
			@ApiParam(value = "ID de um estado", example = "1", required = true)
			Long estadoId,

			@ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
			EstadoDtoInput estadoInput);


	@ApiOperation("Exclui um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Estado excluído"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = ApiError.class)
	})
	public void remover(
			@ApiParam(value = "ID de um estado", example = "1", required = true)
			Long estadoId);
}        