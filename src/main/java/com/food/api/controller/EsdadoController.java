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

import com.food.api.assembler.EstadoDtoAssembler;
import com.food.api.assembler.EstadoDtoInputDisassembler;
import com.food.api.dto.EstadoDto;
import com.food.api.dto.input.EstadoDtoInput;
import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;
import com.food.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EsdadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoDtoAssembler estadoDtoAssembler;
	
	@Autowired
	private EstadoDtoInputDisassembler estadoDtoInputDisassembler;

	
	@GetMapping
	public List<EstadoDto> listar() {
		return estadoDtoAssembler.toCollectionRepresentationModel(estadoRepository.findAll());
	}

	
	@GetMapping("/{estadoId}")
	public EstadoDto buscar(@PathVariable Long estadoId) {
		return estadoDtoAssembler.toRepresentationModel(cadastroEstado.buscar(estadoId));
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDto adicionar(@Valid @RequestBody EstadoDtoInput estadoInput) {
		Estado estado = estadoDtoInputDisassembler.toDomainObject(estadoInput);
		return estadoDtoAssembler.toRepresentationModel(cadastroEstado.salvar(estado));
	}

	
	@PutMapping("/{estadoId}")
	public EstadoDto atualizar(@PathVariable Long estadoId, @Valid @RequestBody EstadoDtoInput estadoInput) {
	
		Estado estadoAtual = cadastroEstado.buscar(estadoId);
		
		estadoDtoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
		
		return estadoDtoAssembler.toRepresentationModel(cadastroEstado.salvar(estadoAtual));
	}

	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);
	}

}