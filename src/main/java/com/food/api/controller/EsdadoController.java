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

import com.food.api.assembler.EstadoDtoInputDisassembler;
import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.dto.EstadoDto;
import com.food.api.dto.input.EstadoDtoInput;
import com.food.api.openapi.controller.EstadoControllerOpenApi;
import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;
import com.food.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EsdadoController implements EstadoControllerOpenApi {
	
	private static final Class<EstadoDto> ESTADO_DTO_CLASS = EstadoDto.class;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private GenericDtoAssembler<Estado, EstadoDto> assembler;
	
	@Autowired
	private EstadoDtoInputDisassembler estadoDtoInputDisassembler;

	
	@GetMapping
	public List<EstadoDto> listar() {
		return assembler.toCollectionRepresentationModel(estadoRepository.findAll(), ESTADO_DTO_CLASS);
	}

	
	@GetMapping("/{estadoId}")
	public EstadoDto buscar(@PathVariable Long estadoId) {
		return assembler.toRepresentationModel(cadastroEstado.buscar(estadoId), ESTADO_DTO_CLASS);
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDto adicionar(@Valid @RequestBody EstadoDtoInput estadoInput) {
		Estado estado = estadoDtoInputDisassembler.toDomainObject(estadoInput);
		estado = cadastroEstado.salvar(estado);
		return assembler.toRepresentationModel(estado, ESTADO_DTO_CLASS);
	}

	
	@PutMapping("/{estadoId}")
	public EstadoDto atualizar(@PathVariable Long estadoId, @Valid @RequestBody EstadoDtoInput estadoInput) {
	
		Estado estadoAtual = cadastroEstado.buscar(estadoId);
		
		estadoDtoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
		
		estadoAtual = cadastroEstado.salvar(estadoAtual);
		
		return assembler.toRepresentationModel(estadoAtual, ESTADO_DTO_CLASS);
	}

	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);
	}

}