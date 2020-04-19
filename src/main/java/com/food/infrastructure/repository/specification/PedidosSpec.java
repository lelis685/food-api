package com.food.infrastructure.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.food.domain.model.Pedido;
import com.food.domain.repository.filter.PedidoFilter;

public class PedidosSpec {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filter) {
		return (root, query, builder) -> {
			
			if(Pedido.class.equals(query.getResultType())) {
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("cliente");
			}
			
			List<Predicate> predicates = new ArrayList<>();

			if(filter.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filter.getClienteId()));
			}
		
			if(filter.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
			}
			
			if(filter.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThan(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
			}

			if(filter.getDataCricaoFim() != null) {
				predicates.add(builder.lessThan(root.get("dataCriacao"), filter.getDataCricaoFim()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};


	}

}
