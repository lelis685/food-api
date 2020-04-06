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

import com.food.api.assembler.RestauranteDtoAssembler;
import com.food.api.assembler.RestauranteDtoInputDisassembler;
import com.food.api.dto.RestauranteDto;
import com.food.api.dto.input.RestauranteDtoInput;
import com.food.domain.exception.CozinhaNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;
import com.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteDtoAssembler restauranteDtoAssembler;
	
	@Autowired
	private RestauranteDtoInputDisassembler restauranteDtoInputDisassembler;

	
	@GetMapping
	public List<RestauranteDto> listar() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		return restauranteDtoAssembler.toCollectionRepresentationModel(restaurantes);
	}

	
	@GetMapping("/{restauranteId}")
	public RestauranteDto buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
		return restauranteDtoAssembler.toRepresentationModel(restaurante);
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDto adicionar(@Valid @RequestBody RestauranteDtoInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteDtoInputDisassembler.toDomainObject(restauranteInput);
			return restauranteDtoAssembler.toRepresentationModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	
	@PutMapping("/{restauranteId}")
	public RestauranteDto atualizar(@PathVariable Long restauranteId, @Valid @RequestBody RestauranteDtoInput restauranteInput) {
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);
			
			restauranteDtoInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			return restauranteDtoAssembler.toRepresentationModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	


}
