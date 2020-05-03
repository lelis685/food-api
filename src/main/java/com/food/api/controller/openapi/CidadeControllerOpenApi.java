package com.food.api.controller.openapi;

import java.util.List;

import com.food.api.dto.CidadeDto;
import com.food.api.dto.input.CidadeDtoInput;
import com.food.api.exceptionhandler.ApiError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	
	@ApiOperation(value = "Lista as cidades")
	public List<CidadeDto> listar();

	
	@ApiOperation(value = "Busca uma cidade por id")
	@ApiResponses({ 
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = ApiError.class) })
	public CidadeDto buscar(
			@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId);

	
	@ApiOperation(value = "Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade cadastrada"),
	})
	public CidadeDto adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade") CidadeDtoInput cidadeInput);

	
	@ApiOperation(value = "Atualiza uma cidade por id")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = ApiError.class) })
	public CidadeDto atualizar(
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") CidadeDtoInput cidadeInput,
			@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId);

	
	@ApiOperation(value = "Exclui uma cidade por id")
	@ApiResponses({ 
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = ApiError.class) })
	public void remover(
			@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId);

}
