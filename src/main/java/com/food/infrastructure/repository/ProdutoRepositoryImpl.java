package com.food.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.model.FotoProduto;
import com.food.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	
	@Transactional
	@Override
	public FotoProduto save(FotoProduto fotoProduto) {
		return manager.merge(fotoProduto);
	}

	@Transactional
	@Override
	public void delete(FotoProduto fotoProduto) {
		manager.remove(fotoProduto);
	}
	
}
