package com.food.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.food.api.dto.FotoProdutoDto;
import com.food.api.dto.input.FotoProdutoDtoInput;
import com.food.api.exceptionhandler.ApiError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

	@ApiOperation("Atualiza a foto do produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Foto do produto atualizada"),
		@ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiError.class)
	})
	public FotoProdutoDto atualizarFoto(
			@ApiParam(value = "ID do restaurante", example = "1", required = true)
			Long restauranteId,

			@ApiParam(value = "ID do produto", example = "1", required = true)
			Long produtoId,

			FotoProdutoDtoInput fotoProdutoInput, 
			
			@ApiParam(value = "(value = Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)",required = true)
			MultipartFile multipartFile) throws IOException;

	
	@ApiOperation("Exclui a foto do produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Foto do produto excluída"),
		@ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Foto de produto não encontrada", response = ApiError.class)
	})
	public void excluir(
			@ApiParam(value = "ID do restaurante", example = "1", required = true)
			Long restauranteId,

			@ApiParam(value = "ID do produto", example = "1", required = true)
			Long produtoId);


	@ApiOperation(value = "Busca a foto do produto de um restaurante",
			produces = "application/json, image/jpeg, image/png")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = ApiError.class),
		@ApiResponse(code = 404, message = "Foto de produto não encontrada", response = ApiError.class)
	})
	public FotoProdutoDto buscar(
			@ApiParam(value = "ID do restaurante", example = "1", required = true)
			Long restauranteId,

			@ApiParam(value = "ID do produto", example = "1", required = true)
			Long produtoId);


	@ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
	public ResponseEntity<?> servir(Long restauranteId, Long produtoId, String acceptHeader) 
			throws HttpMediaTypeNotAcceptableException;
}