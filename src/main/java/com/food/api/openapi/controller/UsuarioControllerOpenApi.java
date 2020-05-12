package com.food.api.openapi.controller;

import java.util.List;

import com.food.api.dto.UsuarioDto;
import com.food.api.dto.input.SenhaDtoInput;
import com.food.api.dto.input.UsuarioComSenhaDtoInput;
import com.food.api.dto.input.UsuarioDtoInput;
import com.food.api.exceptionhandler.ApiError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    public List<UsuarioDto> listar();
    
    
    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do usuário inválido", response = ApiError.class),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiError.class)
    })
    public  UsuarioDto buscar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId);

    
    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Usuário cadastrado"),
    })
    public UsuarioDto salvar(
            @ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
            UsuarioComSenhaDtoInput usuarioInput);
    
    
    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuário atualizado"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiError.class)
    })
    public UsuarioDto atualizar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            
            @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true)
            UsuarioDtoInput usuarioInput);

    
    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiError.class)
    })
    public void alterarSenha(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            
            @ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true)
            SenhaDtoInput senha);
}