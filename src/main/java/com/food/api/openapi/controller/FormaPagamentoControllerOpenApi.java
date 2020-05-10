package com.food.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.food.api.dto.FormaPagamentoDto;
import com.food.api.dto.input.FormaPagamentoDtoInput;
import com.food.api.exceptionhandler.ApiError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

	
	@ApiOperation("Lista as formas de pagamento")
	public ResponseEntity<List<FormaPagamentoDto>> listar(ServletWebRequest request);

	
	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 404 , message = "Forma da pagamento não encontrada", response = ApiError.class),
		@ApiResponse(code = 400 , message = "ID da forma de pagamento inválido", response = ApiError.class)
	})
	public ResponseEntity<FormaPagamentoDto> bucar( 
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
			Long id, 
			ServletWebRequest request);


	@ApiOperation("Cadastra uma forma de pagamento")
	@ApiResponses({
		@ApiResponse(code = 201 , message = "Forma de pagamento cadastrada")
	})
	public FormaPagamentoDto salvar(
			@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true)
			FormaPagamentoDtoInput formaPagamentoInput );
	
	
	@ApiOperation("Atualiza uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 200 , message = "Forma de pagamento atualizada"),
		@ApiResponse(code = 404 , message = "Forma da pagamento não encontrada", response = ApiError.class)
	})
	public FormaPagamentoDto atualizar(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
			Long id, 
			
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com novos dados", required = true)
			FormaPagamentoDtoInput formaPagamentoInput);


	@ApiOperation("Exclui uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 204 , message = "Forma de pagamento excluída"),
		@ApiResponse(code = 404 , message = "Forma de pagamento não encontrada", response = ApiError.class),
		@ApiResponse(code = 400 , message = "ID da forma de pagamento inválido", response = ApiError.class)
	})
	public void excluir(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
			Long id);

	
}
