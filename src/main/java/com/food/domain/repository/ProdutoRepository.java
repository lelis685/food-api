package com.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food.domain.model.FotoProduto;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries{
	
	@Query("FROM Produto WHERE restaurante.id = :restauranteId AND id = :produtoId")
	Optional<Produto> findById(@Param("restauranteId") Long restauranteId, @Param("produtoId") Long produtoId);

	List<Produto> findByRestaurante(Restaurante restaurante);
	
	@Query("FROM Produto p WHERE p.ativo = true AND p.restaurante = :restaurante")
	List<Produto> findAtivoByRestaurante(Restaurante restaurante);
	
	@Query("SELECT f FROM FotoProduto f JOIN f.produto p WHERE p.restaurante.id = :restauranteId AND f.produto.id = :produtoId")
	Optional<FotoProduto> findFotoById(@Param("restauranteId") Long restauranteId, @Param("produtoId") Long produtoId);
	
}
