package com.food.api.openapi.controller;

import java.util.List;

import com.food.api.dto.RestauranteDto;
import com.food.api.dto.input.RestauranteDtoInput;
import com.food.api.exceptionhandler.ApiError;
import com.food.api.openapi.model.RestauranteBasicoDtoOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoDtoOpenApi.class)
	@ApiImplicitParams(@ApiImplicitParam(value = "Nome da projeção de pedidos", 
		allowableValues = "apenas-nome", name = "projecao", paramType = "query", type = "string"))
	public List<RestauranteDto> listarResumido();

	
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	public List<RestauranteDto> listarApenasNome();

	
	@ApiOperation(value = "Busca um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do restaurante inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public RestauranteDto buscar(
			@ApiParam(value = "ID de um restaurante", example = "1" ,required = true)
			Long restauranteId);

	
	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Restaurante cadastrado")
	})
	public RestauranteDto adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
			RestauranteDtoInput restauranteInput);

	
	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante atualizado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public RestauranteDto atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1" ,required = true)
			Long restauranteId, 
			
			@ApiParam(name = "corpo", value = "Representação de um restaurante com novos dados", required = true)
			RestauranteDtoInput restauranteInput);

	
	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public void ativar(
			@ApiParam(value = "ID de um restaurante", example = "1" ,required = true)
			Long restauranteId);

	
	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	public void ativarMultiplos(
			  @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
			List<Long> restauranteIds);

	
	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public void inativar(
			@ApiParam(value = "ID de um restaurante", example = "1" ,required = true)
			Long restauranteId);

	
	@ApiOperation("Inativa múltiplos restaurantes")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes inativado")
	})
	public void inativarMultiplos(
			  @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
			List<Long> restauranteIds);

	
	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public void fechar(
			@ApiParam(value = "ID de um restaurante", example = "1" ,required = true)
			Long restauranteId);

	
	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
	})
	public void abrir(
			@ApiParam(value = "ID de um restaurante", example = "1" ,required = true)
			Long restauranteId);

}
