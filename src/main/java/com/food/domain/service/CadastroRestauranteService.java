package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe cadastro de restaurante com código %d";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

	    Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public Restaurante buscar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)));
	}

}