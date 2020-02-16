package com.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeEstadoNaoEncontradaException;
import com.food.domain.model.Estado;
import com.food.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EsdadoController {

	@Autowired
	private CadastroEstadoService cadastroEstadoService;


	@GetMapping
	public List<Estado> listar(){
		return cadastroEstadoService.listar();
	}


	@PostMapping
	public ResponseEntity<Estado> salvar(@RequestBody Estado estado) {
		cadastroEstadoService.salvar(estado);
		return ResponseEntity.status(HttpStatus.CREATED).body(estado);
	}


	@PutMapping("/{estadoId}")
	public ResponseEntity<?> atualizar(@RequestBody Estado estado,
			@PathVariable("estadoId") Long id) {
		try {
			cadastroEstadoService.atualizar(estado,id);
			return ResponseEntity.ok(estado);
		} catch (EntidadeEstadoNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} 
	}


	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable("estadoId") Long id){
		try {
			cadastroEstadoService.remover(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeEstadoNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}


}
