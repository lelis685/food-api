package com.food.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.domain.exception.EntidadeCozinhaNaoEncontradaException;
import com.food.domain.exception.EntidadeNaoEncontradaExeption;
import com.food.domain.exception.EntidadeRestauranteNaoEncontrada;
import com.food.domain.model.Restaurante;
import com.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		return ResponseEntity.ok(cadastroRestauranteService.listar());
	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id) {
		try {
			return ResponseEntity.ok(cadastroRestauranteService.buscar(id));
		} catch (EntidadeNaoEncontradaExeption e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaExeption e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante, @PathVariable("restauranteId") Long id) {
		try {
			restaurante = cadastroRestauranteService.atualizar(restaurante, id);
			return ResponseEntity.ok(restaurante);
		} catch (EntidadeRestauranteNaoEncontrada e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeCozinhaNaoEncontradaException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}


	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@RequestBody Map<String, Object> campos, 
			@PathVariable("restauranteId") Long id) {

		Restaurante restauranteAtual = cadastroRestauranteService.buscar(id);

		if(restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}

		merge(campos,restauranteAtual);

		return atualizar(restauranteAtual, id);
	}



	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach((nomePropriedade,valorPropriedade) ->{
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}





}
