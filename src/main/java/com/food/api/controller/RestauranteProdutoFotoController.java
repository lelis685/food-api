package com.food.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.dto.FotoProdutoDto;
import com.food.api.dto.input.FotoProdutoDtoInput;
import com.food.domain.model.FotoProduto;
import com.food.domain.model.Produto;
import com.food.domain.service.CadastroProdutoService;
import com.food.domain.service.CatalogoFotoProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	private static final Class<FotoProdutoDto> FOTO_PRODUTO_DTO_CLASS = FotoProdutoDto.class;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	@Autowired
	private GenericDtoAssembler<FotoProduto, FotoProdutoDto> assembler;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDto atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoDtoInput fotoProdutoInput) throws IOException {

		FotoProduto foto = criarFotoProduto(restauranteId, produtoId, fotoProdutoInput);
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, fotoProdutoInput.getArquivo().getInputStream());
		
		return assembler.toRepresentationModel(fotoSalva, FOTO_PRODUTO_DTO_CLASS);
	}


	private FotoProduto criarFotoProduto(Long restauranteId, Long produtoId, FotoProdutoDtoInput fotoProdutoInput) {
		Produto produto = cadastroProdutoService.buscar(restauranteId, produtoId);
		MultipartFile arquivo = fotoProdutoInput.getArquivo();

		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setContentType(arquivo.getContentType());
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		return foto;
	}


}