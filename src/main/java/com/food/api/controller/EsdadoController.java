package com.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EsdadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.listar();
	}
	
	

}
