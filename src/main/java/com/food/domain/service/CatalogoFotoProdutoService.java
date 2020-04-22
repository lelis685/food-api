package com.food.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.model.FotoProduto;
import com.food.domain.repository.FotoStorageService;
import com.food.domain.repository.FotoStorageService.NovaFoto;
import com.food.domain.repository.ProdutoRepository;

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
				.build();
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}

}
