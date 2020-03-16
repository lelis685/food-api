package com.food.infrastructure.repository.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.food.domain.model.Restaurante;

public class RestaurantesSpec {

	public static Specification<Restaurante> comFreteGratis() {
		return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

	public static Specification<Restaurante> comNomeSemelhante(String nome) {
		return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
	}
}
