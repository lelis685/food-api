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
import com.food.api.dto.UsuarioDto;
import com.food.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.food.domain.model.Restaurante;
import com.food.domain.model.Usuario;
import com.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi{
	
	private static final Class<UsuarioDto> USUARIO_DTO_CLASS = UsuarioDto.class;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private GenericDtoAssembler<Usuario, UsuarioDto> assembler;

	
	@GetMapping
	public List<UsuarioDto> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
		return assembler.toCollectionRepresentationModel(restaurante.getResponsaveis(), USUARIO_DTO_CLASS);
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
		cadastroRestaurante.associarUsuario(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
		cadastroRestaurante.desassociarUsuario(restauranteId, usuarioId);
	}

}
