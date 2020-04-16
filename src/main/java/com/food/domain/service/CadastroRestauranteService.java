package com.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.RestauranteNaoEncontradoException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Cozinha;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.Restaurante;
import com.food.domain.model.Usuario;
import com.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;


	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = buscar(id);
		restaurante.ativar();
	}

	@Transactional
	public void inativar(Long id) {
		Restaurante restaurante = buscar(id);
		restaurante.inativar();
	}
	
	@Transactional
	public void ativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar); 
	}
	
	@Transactional
	public void inativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::inativar); 
	}

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);

		Long cidadeID = restaurante.getEndereco().getCidade().getId();
		Cidade cidade = cadastroCidadeService.buscar(cidadeID);

		restaurante.getEndereco().setCidade(cidade);
		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);
		restaurante.desassociarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);
		restaurante.associarFormaPagamento(formaPagamento);
	}

	@Transactional
	public Restaurante buscar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restaurante = buscar(restauranteId);
		restaurante.fechar();
	}


	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restaurante = buscar(restauranteId);
		restaurante.abrir();
	}

	@Transactional
	public void associarUsuario(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscar(usuarioId);
		restaurante.associarUsuario(usuario);
	}

	@Transactional
	public void desassociarUsuario(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscar(usuarioId);
		restaurante.desassociarUsuario(usuario);
	}
	
	
	


}