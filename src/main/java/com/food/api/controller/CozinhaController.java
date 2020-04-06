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

import com.food.api.assembler.CozinhaDtoAssembler;
import com.food.api.assembler.CozinhaDtoInputDisassembler;
import com.food.api.dto.CozinhaDto;
import com.food.api.dto.input.CozinhaDtoInput;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDtoAssembler cozinhaDtoAssembler;
	
	@Autowired
	private CozinhaDtoInputDisassembler cozinhaDtoInputDisassembler;
	

	@GetMapping(params = "nome")
	public List<CozinhaDto> buscarPorNome(String nome) {
		return cozinhaDtoAssembler.toCollectionRepresentationModel(cozinhaRepository.findByNomeContaining(nome));
	}

	@GetMapping
	public List<CozinhaDto> listar() {
		return cozinhaDtoAssembler.toCollectionRepresentationModel(cozinhaRepository.findAll());
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDto buscar(@PathVariable Long cozinhaId) {
		return cozinhaDtoAssembler.toRepresentationModel(cadastroCozinha.buscar(cozinhaId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDto adicionar(@Valid @RequestBody CozinhaDtoInput cozinhaInput) {
		Cozinha cozinha = cozinhaDtoInputDisassembler.toDomainObject(cozinhaInput);
		return cozinhaDtoAssembler.toRepresentationModel(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDto atualizar(@PathVariable Long cozinhaId, @Valid @RequestBody CozinhaDtoInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinha.buscar(cozinhaId);
		
		cozinhaDtoInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		return  cozinhaDtoAssembler.toRepresentationModel(cadastroCozinha.salvar(cozinhaAtual));
	}


	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}

}