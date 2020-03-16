package com.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.food.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	List<Restaurante> findComFreteGratis(String nome);
	
}