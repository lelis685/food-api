package com.food.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import com.food.api.dto.CozinhaDto;
import com.food.api.dto.input.CozinhaDtoInput;
import com.food.api.exceptionhandler.ApiError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {
	
	
	@ApiOperation("Lista as cozinhas com paginação")
	public Page<CozinhaDto> listar(Pageable pageable);
	
	
	@ApiOperation("Busca a cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = ApiError.class)
	})
	public CozinhaDto buscar(@ApiParam(value = "ID da cozinha", example = "1") Long cozinhaId);

	
	@ApiOperation("Realiza o cadastro da cozinha")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha cadastrada")
	})
	public CozinhaDto adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha")CozinhaDtoInput cozinhaInput);

	
	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = ApiError.class),
		@ApiResponse(code = 200, message = "Cozinha atualizada"),
	})
	public CozinhaDto atualizar(
			@ApiParam(value = "ID da cozinha", example = "1") Long cozinhaId, 
			@ApiParam(name = "corpo", value = "Representação de uma cozinha com novos dados") CozinhaDtoInput cozinhaInput
			);

	
	@ApiOperation("Exclui cadastro da cozinha por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Cozinha excluída"),
			@ApiResponse(code = 400, message = "ID da cozinha inválido", response = ApiError.class),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = ApiError.class)
	})
	public void remover(@ApiParam(value = "ID da cozinha", example = "1") @PathVariable Long cozinhaId);

	
}
