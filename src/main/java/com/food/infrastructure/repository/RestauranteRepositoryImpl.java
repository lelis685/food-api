package com.food.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;
import com.food.domain.repository.RestauranteRepositoryQueries;
import com.food.infrastructure.repository.specification.RestaurantesSpec;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	@Lazy
	private RestauranteRepository restauranteRepository;

	public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

		Root<Restaurante> root = criteria.from(Restaurante.class);

		ArrayList<Predicate> predicates = new ArrayList<>();

		if (StringUtils.hasLength(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}

		if (taxaInicial != null) {
			predicates.add(builder.greaterThan(root.get("taxaFrete"), taxaInicial));
		}

		if (taxaFinal != null) {
			predicates.add(builder.lessThan(root.get("taxaFrete"), taxaFinal));
		}

		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurante> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository
				.findAll(RestaurantesSpec.comFreteGratis().and(RestaurantesSpec.comNomeSemelhante(nome)));
	}

}
