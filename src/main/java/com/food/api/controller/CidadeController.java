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

import com.food.domain.exception.EntidadeCidadeNaoEncontradaException;
import com.food.domain.exception.EntidadeEstadoNaoEncontradaException;
import com.food.domain.model.Cidade;
import com.food.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CadastroCidadeService cadastroCidadeService;


	@GetMapping
	public ResponseEntity<List<Cidade>> listar(){
		return ResponseEntity.ok(cadastroCidadeService.listar());
	}


	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
		try {
			cadastroCidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (EntidadeEstadoNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@RequestBody Cidade cidade,
										@PathVariable("cidadeId") Long id) {
		try {
			cadastroCidadeService.atualizar(cidade,id);
			return ResponseEntity.ok(cidade);
		} catch (EntidadeEstadoNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (EntidadeCidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}


	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> remover(@PathVariable("cidadeId") Long id){
		try {
			cadastroCidadeService.remover(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeCidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}



}
