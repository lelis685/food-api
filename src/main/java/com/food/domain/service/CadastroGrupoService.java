package com.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.GrupoNaoEncontradoException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Grupo;
import com.food.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";

	@Autowired
	private GrupoRepository grupoRepository;

	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	public List<Grupo> listar() {
		return grupoRepository.findAll();
	}

	public Grupo buscar(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}

	@Transactional
	public void excluir(Long id) {
		try {
			grupoRepository.deleteById(id);
			grupoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(String.format(MSG_GRUPO_EM_USO, id));
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(id);
		}

	}

}
