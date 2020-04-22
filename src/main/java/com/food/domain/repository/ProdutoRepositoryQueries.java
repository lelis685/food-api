package com.food.domain.repository;

import com.food.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto fotoProduto);
	void delete(FotoProduto fotoProduto);
}