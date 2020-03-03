package com.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.domain.exception.EntidadeCozinhaNaoEncontradaException;
import com.food.domain.exception.EntidadeNaoEncontradaExeption;
import com.food.domain.exception.EntidadeRestauranteNaoEncontrada;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante atualizar(Restaurante restaurante, Long id) {

		Restaurante restauranteEncontrado = buscar(id);

		Long cozinhaId = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

		if (cozinha.isEmpty()) {
			throw new EntidadeCozinhaNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
		}

		BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id");

		return restauranteRepository.save(restauranteEncontrado);
	}

	public Restaurante salvar(Restaurante restaurante) {

		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaExeption(
				String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public Restaurante buscar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new EntidadeRestauranteNaoEncontrada(
				String.format("Não existe um cadastro de restaurante com código %d", id)));

	}

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

}
