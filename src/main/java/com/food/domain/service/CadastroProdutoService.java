package com.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.ProdutoNaoEncontradoException;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	public Produto buscar(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
	} 
	
	public List<Produto> buscarProdutosPorRestaurante(Restaurante restaurante) {
		return produtoRepository.findByRestaurante(restaurante);
	} 
	
	public List<Produto> buscarProdutosAtivoPorRestaurante(Restaurante restaurante) {
		return produtoRepository.findAtivoByRestaurante(restaurante);
	} 
	

}