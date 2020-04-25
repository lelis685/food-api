package com.food.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.FotoProtudoNaoEncontradaException;
import com.food.domain.model.FotoProduto;
import com.food.domain.repository.ProdutoRepository;
import com.food.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArmazenar) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		
		foto.setNomeArquivo(novoNomeArquivo);
		foto =  produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(dadosArmazenar)
				.contentType(foto.getContentType())
				.build();
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}
	
	
	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {
	    FotoProduto foto = buscar(restauranteId, produtoId);
	    
	    produtoRepository.delete(foto);
	    produtoRepository.flush();

	    fotoStorageService.remover(foto.getNomeArquivo());
	}


	public FotoProduto buscar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() ->  new FotoProtudoNaoEncontradaException(restauranteId, produtoId));
	}


}
