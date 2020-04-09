package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.CidadeNaoEncontradaException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	public Cidade buscar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}

	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();

		Estado estado = cadastroEstado.buscar(estadoId);

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
		}
	}

}