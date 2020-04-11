package com.food.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.NegocioException;
import com.food.domain.exception.UsuarioNaoEncontradoException;
import com.food.domain.model.Grupo;
import com.food.domain.model.Usuario;
import com.food.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MSG_SENHA_ATUAL_INVALIDA = "Senha atual informada não coincide com a senha do usuário.";
	private static final String MSG_EMAIL_JA_EXISTENTE = "Já exite um usuário cadastrado com o  e-mail %s";

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;

	public Usuario buscar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}

	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@Transactional
	public Usuario salvar(Usuario usuario) {
	
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		if(usuarioExistente.isPresent() && usuario.isUsuarioDifferent(usuario, usuarioExistente.get()) ) {
			throw new NegocioException(String.format(MSG_EMAIL_JA_EXISTENTE,usuario.getEmail()));
		}else {
			return usuarioRepository.save(usuario);
		}
	}

	@Transactional
	public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
		Usuario usuario = buscar(id);
		if (usuario.isSenhaAtual(senhaAtual)) {
			usuario.setSenha(novaSenha);
		}else {
			throw new NegocioException(MSG_SENHA_ATUAL_INVALIDA);
		}
	}

	public Set<Grupo> listarGrupos(Long id) {
		Usuario usuario = buscar(id);
		return usuario.getGrupos();
	}

	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = cadastroGrupoService.buscar(grupoId);
		usuario.desassociarGrupo(grupo);
		
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = cadastroGrupoService.buscar(grupoId);
		usuario.associarGrupo(grupo);
	}

}
