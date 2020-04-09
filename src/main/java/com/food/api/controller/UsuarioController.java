package com.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.assembler.UsuarioDtoInputDisassembler;
import com.food.api.dto.UsuarioDto;
import com.food.api.dto.input.UsuarioDtoInput;
import com.food.api.dto.input.UsuarioComSenhaDtoInput;
import com.food.api.dto.input.SenhaDtoInput;
import com.food.domain.model.Usuario;
import com.food.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private static final Class<UsuarioDto> USUARIO_DTO_CLASS = UsuarioDto.class;

	@Autowired
	private GenericDtoAssembler<Usuario, UsuarioDto> assembler;

	@Autowired
	private UsuarioDtoInputDisassembler diassembler;

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;


	@GetMapping
	public List<UsuarioDto> listar(){
		return assembler.toCollectionRepresentationModel(cadastroUsuarioService.listar(), USUARIO_DTO_CLASS);
	}


	@GetMapping("/{id}")
	public UsuarioDto buscar(@PathVariable Long id){
		return assembler.toRepresentationModel(cadastroUsuarioService.buscar(id), USUARIO_DTO_CLASS);
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto salvar(@RequestBody @Valid UsuarioComSenhaDtoInput usuarioInput) {
		Usuario usuario = diassembler.toDomainObject(usuarioInput);
		return assembler.toRepresentationModel(cadastroUsuarioService.salvar(usuario), USUARIO_DTO_CLASS);
	}


	@PutMapping("/{id}")
	public UsuarioDto buscar(@PathVariable Long id, @RequestBody @Valid UsuarioDtoInput usuarioInput){

		Usuario usuarioAtual = cadastroUsuarioService.buscar(id);

		diassembler.copyToDomainObject(usuarioInput, usuarioAtual);

		usuarioAtual = cadastroUsuarioService.salvar(usuarioAtual);

		return assembler.toRepresentationModel(usuarioAtual, USUARIO_DTO_CLASS);
	}


	@PutMapping("/{id}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaDtoInput usuarioInput){
		cadastroUsuarioService.alterarSenha(id, usuarioInput.getSenhaAtual(), usuarioInput.getNovaSenha());
	}


}
