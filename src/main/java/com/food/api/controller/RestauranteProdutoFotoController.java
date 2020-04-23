package com.food.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.dto.FotoProdutoDto;
import com.food.api.dto.input.FotoProdutoDtoInput;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.model.FotoProduto;
import com.food.domain.model.Produto;
import com.food.domain.repository.FotoStorageService;
import com.food.domain.service.CadastroProdutoService;
import com.food.domain.service.CatalogoFotoProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	private static final Class<FotoProdutoDto> FOTO_PRODUTO_DTO_CLASS = FotoProdutoDto.class;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private FotoStorageService fotoStorageService;

	@Autowired
	private GenericDtoAssembler<FotoProduto, FotoProdutoDto> assembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDto atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoDtoInput fotoProdutoInput) throws IOException {

		FotoProduto foto = criarFotoProduto(restauranteId, produtoId, fotoProdutoInput);
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, fotoProdutoInput.getArquivo().getInputStream());

		return assembler.toRepresentationModel(fotoSalva, FOTO_PRODUTO_DTO_CLASS);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDto buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto foto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);
		return assembler.toRepresentationModel(foto, FOTO_PRODUTO_DTO_CLASS);
	}

	@GetMapping
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto foto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);

			MediaType mediaType = MediaType.parseMediaType(foto.getContentType());

			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaType, mediaTypesAceitas);

			InputStream inputStream = fotoStorageService.recuperar(foto.getNomeArquivo());

			return ResponseEntity.ok()
					.contentType(mediaType)
					.body(new InputStreamResource(inputStream));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

	}

	private void verificarCompatibilidadeMediaType(MediaType mediaType, List<MediaType> mediaTypesAceitas)
			throws HttpMediaTypeNotAcceptableException {

		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaType));

		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}

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