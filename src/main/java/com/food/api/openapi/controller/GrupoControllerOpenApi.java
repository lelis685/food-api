package com.food.api.openapi.controller;

import java.util.List;

import com.food.api.dto.GrupoDto;
import com.food.api.dto.input.GrupoDtoInput;
import com.food.api.exceptionhandler.ApiError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	
	@ApiOperation(value = "Lista os grupos")
	public List<GrupoDto> listar();
	
	
	@ApiOperation(value = "Busca grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do grupo inválido" , response = ApiError.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado" , response = ApiError.class)
	})
	public GrupoDto buscar(@ApiParam(value = "ID de um grupo", example = "1") Long id);
	
	
	@ApiOperation(value = "Realiza cadastro de um grupo")
	@ApiResponses(
			@ApiResponse(code = 201, message = "Grupo cadastrado")
			)
	public GrupoDto salvar(@ApiParam(name = "corpo", value = "Representação de um novo grupo") GrupoDtoInput grupoInput);
	
	
	@ApiOperation(value = "Atualiza cadastro de um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do grupo inválido" , response = ApiError.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado" , response = ApiError.class)
	})
	public GrupoDto atualizar(
			@ApiParam(value = "ID de um grupo", example = "1") Long id, 
			@ApiParam(name = "corpo", value = "Representação um grupo com novos") GrupoDtoInput grupoInput
			);
	
	
	@ApiOperation(value = "Exclui um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Grupo excluído"),
			@ApiResponse(code = 404, message = "Grupo não encontrado",  response = ApiError.class)
	})
	public void exluir(@ApiParam(value = "ID de um grupo", example = "1") Long id);
	
	
	
}
