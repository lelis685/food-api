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

import com.fasterxml.jackson.annotation.JsonView;
import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.assembler.RestauranteDtoInputDisassembler;
import com.food.api.dto.RestauranteDto;
import com.food.api.dto.input.RestauranteDtoInput;
import com.food.api.dto.view.RestauranteView;
import com.food.api.openapi.controller.RestauranteControllerOpenApi;
import com.food.domain.exception.CidadeNaoEncontradaException;
import com.food.domain.exception.CozinhaNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import com.food.domain.exception.RestauranteNaoEncontradoException;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;
import com.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi{

	private static final Class<RestauranteDto> RESTAURANTE_DTO_CLASS = RestauranteDto.class;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private GenericDtoAssembler<Restaurante, RestauranteDto> assembler;

	@Autowired
	private RestauranteDtoInputDisassembler restauranteDtoInputDisassembler;

	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteDto> listarResumido() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		return assembler.toCollectionRepresentationModel(restaurantes, RESTAURANTE_DTO_CLASS);
	}

	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteDto> listarApenasNome() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		return assembler.toCollectionRepresentationModel(restaurantes, RESTAURANTE_DTO_CLASS);
	}


	@GetMapping("/{restauranteId}")
	public RestauranteDto buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
		return assembler.toRepresentationModel(restaurante, RESTAURANTE_DTO_CLASS);
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDto adicionar(@Valid @RequestBody RestauranteDtoInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteDtoInputDisassembler.toDomainObject(restauranteInput);
			restaurante = cadastroRestaurante.salvar(restaurante);
			return assembler.toRepresentationModel(restaurante, RESTAURANTE_DTO_CLASS);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}


	@PutMapping("/{restauranteId}")
	public RestauranteDto atualizar(@PathVariable Long restauranteId, @Valid @RequestBody RestauranteDtoInput restauranteInput) {
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

			restauranteDtoInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

			restauranteAtual = cadastroRestaurante.salvar(restauranteAtual);

			return assembler.toRepresentationModel(restauranteAtual, RESTAURANTE_DTO_CLASS);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}


	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
	}


	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}


	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
	}


	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}


	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
	}


	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
	}



}
