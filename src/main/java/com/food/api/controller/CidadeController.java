package com.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.assembler.CidadeDtoAssembler;
import com.food.api.assembler.CidadeDtoInputDisassembler;
import com.food.api.dto.CidadeDto;
import com.food.api.dto.input.CidadeDtoInput;
import com.food.domain.exception.EstadoNaoEncontradoException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Cidade;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDtoAssembler cidadeDtoAssembler;
	
	@Autowired
	private CidadeDtoInputDisassembler cidadeDtoInputDisassembler;

	
	@GetMapping
	public List<CidadeDto> listar() {
		return cidadeDtoAssembler.toCollectionRepresentationModel(cidadeRepository.findAll());
	}
	

	@GetMapping("/{cidadeId}")
	public CidadeDto buscar(@PathVariable Long cidadeId) {
		return cidadeDtoAssembler.toRepresentationModel(cadastroCidade.buscar(cidadeId));
	}
	

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDto adicionar(@Valid @RequestBody CidadeDtoInput cidadeInput) {
		try {
			Cidade cidade = cidadeDtoInputDisassembler.toDomainObject(cidadeInput);
			return cidadeDtoAssembler.toRepresentationModel(cadastroCidade.salvar(cidade));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	
	@PutMapping("/{cidadeId}")
	public CidadeDto atualizar(@PathVariable Long cidadeId, @Valid @RequestBody CidadeDtoInput cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);
			
			cidadeDtoInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			
			return cidadeDtoAssembler.toRepresentationModel(cadastroCidade.salvar(cidadeAtual));
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