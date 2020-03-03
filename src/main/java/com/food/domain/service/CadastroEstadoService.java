package com.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.food.domain.exception.EntidadeEstadoNaoEncontradaException;
import com.food.domain.exception.EntidadeException;
import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado atualizar(Estado estado, Long id) {

		Estado estadoEncontrado = buscar(id);

		BeanUtils.copyProperties(estado, estadoEncontrado, "id");

		return estadoRepository.save(estadoEncontrado);
	}

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public Estado buscar(Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		if (estado.isEmpty()) {
			throw new EntidadeEstadoNaoEncontradaException(
					String.format("Não existe cadastro de estado com código %d", id));
		}
		return estado.get();
	}

	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	public void remover(Long id) {
		try {
			buscar(id);
			estadoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeException(
					String.format("Estado de código %d não pode ser removido pois está em uso.", id));
		}
	}

}
