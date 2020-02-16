package com.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante,
			@PathVariable("restauranteId") Long id) {
		
		try {
		
			restaurante = cadastroRestauranteService.atualizar(restaurante,id);
			
			return ResponseEntity.ok(restaurante);
		
		}catch (EntidadeRestauranteNaoEncontrada e) {
			return ResponseEntity.notFound().build();
		
		}catch (EntidadeCozinhaNaoEncontradaException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}

	}


}
