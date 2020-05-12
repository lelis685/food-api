package com.food.api.openapi.controller;

import java.util.List;

import com.food.api.dto.ProdutoDto;
import com.food.api.dto.input.ProdutoDtoInput;
import com.food.api.exceptionhandler.ApiError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Lista os produtos de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do restaurante inválido", response = ApiError.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
    })
    public  List<ProdutoDto> listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", 
                example = "false", defaultValue = "false")
            boolean incluirInativos);

    
    @ApiOperation("Busca um produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = ApiError.class),
        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiError.class)
    })
    public ProdutoDto buscar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID do produto", example = "1", required = true)
            Long produtoId);

    
    @ApiOperation("Cadastra um produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Produto cadastrado"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiError.class)
    })
    public ProdutoDto salvar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true)
            ProdutoDtoInput produtoInput);

    
    @ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Produto atualizado"),
        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiError.class)
    })
    public ProdutoDto atualizar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID do produto", example = "1", required = true)
            Long produtoId,
            
            @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados", required = true)
            ProdutoDtoInput produtoInput);
}