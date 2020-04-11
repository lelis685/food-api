package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.domain.exception.PermissaoNaoEncontradaException;
import com.food.domain.model.Permissao;
import com.food.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	
	@Autowired
	private PermissaoRepository permissaoRepository;
	

	public Permissao buscar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId).orElseThrow(() ->new PermissaoNaoEncontradaException(permissaoId));
	}
	
}
