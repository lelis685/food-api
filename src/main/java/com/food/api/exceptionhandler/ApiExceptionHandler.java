package com.food.api.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = 
				"Ocorreu um erro interno inesperado no sistema. "
	        + 	"Tente novamente e se o problema persistir, entre em contato "
	        + 	"com o administrador do sistema.";

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ex.printStackTrace();
		ApiError bodyError = createApiErrorBuilder(status,ApiErrorType.ERRO_DE_SISTEMA, MSG_ERRO_GENERICA_USUARIO_FINAL)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, bodyError, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String detail  = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		BindingResult bindingResult = ex.getBindingResult();
		
		List<Field> apiErrorFields = bindingResult.getFieldErrors().stream()
				.map(fieldError -> Field.builder()
						.name(fieldError.getField())
						.userMessage(fieldError.getDefaultMessage())
						.build())
				.collect(Collectors.toList());
		
		ApiError bodyError = createApiErrorBuilder(status,ApiErrorType.DADOS_INVALIDOS, detail )
				.userMessage(detail )
				.fields(apiErrorFields)
				.build();
		return handleExceptionInternal(ex, bodyError, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ApiError bodyError = createApiErrorBuilder(status,ApiErrorType.RECURSO_NAO_ENCONTRADO, ex.getMessage())
				.userMessage(ex.getMessage())	
				.build();
		return handleExceptionInternal(ex, bodyError, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ApiError bodyError = createApiErrorBuilder(status, ApiErrorType.ERRO_NEGOCIO, ex.getMessage())
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, bodyError, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ApiError bodyError = createApiErrorBuilder(status, ApiErrorType.ENTIDADE_EM_USO, ex.getMessage())
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, bodyError, new HttpHeaders(), status, request);
	}


	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		ApiError errorBody = createApiErrorBuilder(status, ApiErrorType.PARAMETRO_INVALIDO, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, errorBody, headers, status, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.", ex.getRequestURL());
		ApiError bodyError = createApiErrorBuilder(status, ApiErrorType.RECURSO_NAO_ENCONTRADO, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, bodyError, headers, status, request);
	}


	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object bodyError, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if(bodyError == null) {
			bodyError = ApiError.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.build();
		}else if(bodyError instanceof String) {
			bodyError = ApiError.builder()
					.title(bodyError.toString())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.status(status.value())
					.build();
		}

		return super.handleExceptionInternal(ex, bodyError, headers, status, request);
	}


	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}else if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}

		String detail = "O corpo da requisição está inválido. Verifique sintaxe.";
		ApiError bodyError = createApiErrorBuilder(status, ApiErrorType.CORPO_REQUISICAO_INVALIDO, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, bodyError, new HttpHeaders(), status, request);
	}



	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request){

		String fields = joinPath(ex.getPath());

		String detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente.", fields);
		ApiError bodyError = createApiErrorBuilder(status, ApiErrorType.CORPO_REQUISICAO_INVALIDO, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build(); 

		return handleExceptionInternal(ex, bodyError, headers, status, request);
	}


	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String fields = joinPath(ex.getPath());

		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				fields, ex.getValue(), ex.getTargetType().getSimpleName() );

		ApiError bodyError = createApiErrorBuilder(status, ApiErrorType.CORPO_REQUISICAO_INVALIDO, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, bodyError, new HttpHeaders(), status, request);
	}


	private String joinPath(List<Reference> refs) {
		String fields  = refs.stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		return fields;
	}


	private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType type, String detail) {
		return ApiError.builder()
				.status(status.value())
				.type(type.getUri())
				.title(type.getTitle())
				.detail(detail);
	}

}
