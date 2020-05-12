package com.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.dto.GrupoDto;
import com.food.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.food.domain.model.Grupo;
import com.food.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi{

	private static final Class<GrupoDto> GRUPO_DTO_CLASS = GrupoDto.class;

	@Autowired
	private GenericDtoAssembler<Grupo, GrupoDto> assembler;

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	
	@GetMapping
	public List<GrupoDto> listar(@PathVariable Long usuarioId){
		return assembler.toCollectionRepresentationModel(cadastroUsuarioService.listarGrupos(usuarioId), GRUPO_DTO_CLASS);
	}

	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
		cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
		cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
	}


}
