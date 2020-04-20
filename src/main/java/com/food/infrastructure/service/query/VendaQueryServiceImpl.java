package com.food.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.food.domain.filter.VendaDiariaFilter;
import com.food.domain.model.Pedido;
import com.food.domain.model.StatusPedido;
import com.food.domain.model.dto.VendaDiaria;
import com.food.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filter, String timeOffset) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
		Root<Pedido> root = query.from(Pedido.class);
		
		Expression<Date> functionConvertTzDataCriacao = 
				builder.function("convert_tz", Date.class,root.get("dataCriacao"),
						builder.literal("+00:00"), builder.literal(timeOffset));

		Expression<Date> functionDataCriacao = 
				builder.function("date", Date.class, functionConvertTzDataCriacao);

		CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class, 
				functionDataCriacao, 
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal"))
				);

		List<Predicate> predicates = criarRestricoes(filter, builder, root);

		query.select(selection);
		query.groupBy(functionDataCriacao);
		query.where(predicates.toArray(new Predicate[0]));

		return manager.createQuery(query).getResultList();
	}

	private List<Predicate> criarRestricoes(VendaDiariaFilter filter, CriteriaBuilder builder, Root<Pedido> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, 
				StatusPedido.ENTREGUE));
		
		if(filter.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThan(root.get("dataCriacao"),filter.getDataCriacaoInicio()));
		}

		if(filter.getDataCricaoFim() != null) {
			predicates.add(builder.lessThan(root.get("dataCriacao"),filter.getDataCricaoFim()));
		}

		if(filter.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
		}
		return predicates;
	}

}
