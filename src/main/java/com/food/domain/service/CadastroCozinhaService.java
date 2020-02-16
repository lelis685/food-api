package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaExeption;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;


	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}


	public void remover(Long id) {
		try {
			cozinhaRepository.remover(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaExeption(
					String .format("Não existe um cadastro de cozinha com código %d",id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String .format("Cozinha de código %d não pode ser removida pois está em uso.", id));
		}
	}



}
