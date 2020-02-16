package com.food.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.domain.exception.EntidadeCidadeNaoEncontradaException;
import com.food.domain.exception.EntidadeEstadoNaoEncontradaException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {


	@Autowired
	private CidadeRepository cidadeRepository;


	@Autowired
	private EstadoRepository estadoRepository;


	public Cidade atualizar(Cidade cidade, Long id) {

		Cidade cidadeEcontrado = buscar(id);

		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);

		if(estado == null) {
			throw new EntidadeEstadoNaoEncontradaException(
					String.format("Não existe cadastro de estado com código %d",estadoId));
		}

		BeanUtils.copyProperties(cidade, cidadeEcontrado, "id");

		return cidadeRepository.salvar(cidadeEcontrado); 
	}


	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);

		if(estado == null) {
			throw new EntidadeEstadoNaoEncontradaException(
					String.format("Não existe cadastro de estado com código %d",estadoId));
		}

		cidade.setEstado(estado);

		return cidadeRepository.salvar(cidade);
	}


	public Cidade buscar(Long id) {
		Cidade cidade = cidadeRepository.buscar(id);
		if(cidade == null) {
			throw new EntidadeCidadeNaoEncontradaException(
					String.format("Não existe cadastro de cidade com código %d",id));
		}
		return cidade;
	}


	public List<Cidade> listar() {
		return cidadeRepository.listar();
	}


	public void remover(Long id) {
		buscar(id);
		cidadeRepository.remover(id);
	}

}
