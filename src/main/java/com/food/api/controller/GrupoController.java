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

import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.assembler.GrupoDtoInputDisassembler;
import com.food.api.dto.GrupoDto;
import com.food.api.dto.input.GrupoDtoInput;
import com.food.domain.model.Grupo;
import com.food.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	private static final Class<GrupoDto> GRUPO_DTO_CLASS = GrupoDto.class;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GenericDtoAssembler<Grupo, GrupoDto> assembler;
	
	@Autowired
	private GrupoDtoInputDisassembler diassembler;
	
	
	@GetMapping
	public List<GrupoDto> listar() {
		return assembler.toCollectionRepresentationModel(cadastroGrupoService.listar(), GRUPO_DTO_CLASS);
	}
	
	
	@GetMapping("/{id}")
	public GrupoDto buscar(@PathVariable Long id) {
		return assembler.toRepresentationModel(cadastroGrupoService.buscar(id), GRUPO_DTO_CLASS);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDto salvar(@Valid @RequestBody GrupoDtoInput grupoInput) {
		
		Grupo grupo = diassembler.toDomainObject(grupoInput);
		
		return assembler.toRepresentationModel(cadastroGrupoService.salvar(grupo), GRUPO_DTO_CLASS);
	}
	
	
	@PutMapping("/{id}")
	public GrupoDto atuaizar(@PathVariable Long id, @RequestBody @Valid GrupoDtoInput grupoInput) {
		
		Grupo grupoAtual = cadastroGrupoService.buscar(id);
		
		diassembler.copyToDomainObject(grupoInput, grupoAtual);
		
		return assembler.toRepresentationModel(cadastroGrupoService.salvar(grupoAtual), GRUPO_DTO_CLASS);
	}
	

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void exluir(@PathVariable Long id) {
		cadastroGrupoService.excluir(id);
	}
	
	
}
