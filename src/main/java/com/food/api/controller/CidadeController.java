package com.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.assembler.CidadeDtoInputDisassembler;
import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.controller.openapi.CidadeControllerOpenApi;
import com.food.api.dto.CidadeDto;
import com.food.api.dto.input.CidadeDtoInput;
import com.food.domain.exception.EstadoNaoEncontradoException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Cidade;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi{

	private static final Class<CidadeDto> CIDADE_DTO_CLASS = CidadeDto.class;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private GenericDtoAssembler<Cidade, CidadeDto> assembler;

	@Autowired
	private CidadeDtoInputDisassembler cidadeDtoInputDisassembler;

	
	@GetMapping
	public List<CidadeDto> listar() {
		return assembler.toCollectionRepresentationModel(cidadeRepository.findAll(),CIDADE_DTO_CLASS);
	}

	
	@GetMapping("/{cidadeId}")
	public CidadeDto buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscar(cidadeId);
		return assembler.toRepresentationModel(cidade, CIDADE_DTO_CLASS);
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDto adicionar(@Valid @RequestBody CidadeDtoInput cidadeInput) {
		try {
			Cidade cidade = cidadeDtoInputDisassembler.toDomainObject(cidadeInput);
			cidade = cadastroCidade.salvar(cidade);
			return assembler.toRepresentationModel(cidade, CIDADE_DTO_CLASS);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}


	@PutMapping("/{cidadeId}")
	public CidadeDto atualizar(@Valid @RequestBody CidadeDtoInput cidadeInput , @PathVariable Long cidadeId ) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);

			cidadeDtoInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cadastroCidade.salvar(cidadeAtual);

			return assembler.toRepresentationModel(cidadeAtual,CIDADE_DTO_CLASS);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}


	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}


}