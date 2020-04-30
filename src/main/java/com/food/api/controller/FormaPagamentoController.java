package com.food.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.food.api.assembler.FormaPagamentoDtoInputDisassembler;
import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.dto.FormaPagamentoDto;
import com.food.api.dto.input.FormaPagamentoDtoInput;
import com.food.domain.model.FormaPagamento;
import com.food.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	private static final Class<FormaPagamentoDto> FORMA_PAGAMENTO_DTO_CLASS = FormaPagamentoDto.class;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;

	@Autowired
	private GenericDtoAssembler<FormaPagamento, FormaPagamentoDto> assembler;

	@Autowired
	private FormaPagamentoDtoInputDisassembler diassembler;


	@GetMapping
	public ResponseEntity<List<FormaPagamentoDto>> listar(ServletWebRequest request){
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		
		OffsetDateTime ultimaDataAtualizacao = cadastroFormaPagamentoService.buscarUltimaDataAtualizacao();	
		if(ultimaDataAtualizacao != null) {
			eTag = String.valueOf(ultimaDataAtualizacao.toEpochSecond());
		}
		
		// compara If-None-Match vindo do HEADER caso nao modificado evita processamento desnecessario
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		List<FormaPagamentoDto> formaPagamentos = 
				assembler.toCollectionRepresentationModel(cadastroFormaPagamentoService.listar(), FORMA_PAGAMENTO_DTO_CLASS);

		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10,TimeUnit.SECONDS))
				.eTag(eTag)
				.cacheControl(CacheControl.noCache()) // forca estado stale no browse
				.body(formaPagamentos);
	}


	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoDto> bucar(@PathVariable Long id, ServletWebRequest request){
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		
		OffsetDateTime ultimaDataAtualizacao = cadastroFormaPagamentoService.buscarDataAtualizacaoById(id);	
		
		if(ultimaDataAtualizacao != null) {
			eTag = String.valueOf(ultimaDataAtualizacao.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamentoDto pagamentoDto = assembler.toRepresentationModel(
				cadastroFormaPagamentoService.buscar(id), FORMA_PAGAMENTO_DTO_CLASS);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10,TimeUnit.SECONDS))
				.eTag(eTag)
				.body(pagamentoDto);
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto salvar(@Valid @RequestBody FormaPagamentoDtoInput formaPagamentoInput ){

		FormaPagamento formaPagamento = cadastroFormaPagamentoService.salvar(
				diassembler.toDomainObject(formaPagamentoInput));

		return assembler.toRepresentationModel(formaPagamento,FORMA_PAGAMENTO_DTO_CLASS);
	}


	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto atualizar(@PathVariable Long id, @Valid @RequestBody FormaPagamentoDtoInput formaPagamentoInput){

		FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscar(id);

		diassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

		formaPagamentoAtual = cadastroFormaPagamentoService.salvar(formaPagamentoAtual);

		return assembler.toRepresentationModel(formaPagamentoAtual,FORMA_PAGAMENTO_DTO_CLASS);
	}


	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id){
		cadastroFormaPagamentoService.excluir(id);
	}

}
