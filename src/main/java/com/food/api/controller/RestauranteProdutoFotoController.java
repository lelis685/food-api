package com.food.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.dto.FotoProdutoDto;
import com.food.api.dto.input.FotoProdutoDtoInput;
import com.food.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.model.FotoProduto;
import com.food.domain.model.Produto;
import com.food.domain.service.CadastroProdutoService;
import com.food.domain.service.CatalogoFotoProdutoService;
import com.food.domain.service.FotoStorageService;
import com.food.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi{

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
			@Valid FotoProdutoDtoInput fotoProdutoInput,
			@RequestPart(required = true)MultipartFile arquivo) throws IOException {
		
		Produto produto = cadastroProdutoService.buscar(restauranteId, produtoId);
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());

		return assembler.toRepresentationModel(fotoSalva, FOTO_PRODUTO_DTO_CLASS);
	}

	@GetMapping
	public FotoProdutoDto buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto foto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);
		return assembler.toRepresentationModel(foto, FOTO_PRODUTO_DTO_CLASS);
	}

	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> servir(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto foto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);

			MediaType mediaType = MediaType.parseMediaType(foto.getContentType());

			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaType, mediaTypesAceitas);

			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(foto.getNomeArquivo());

			if(fotoRecuperada.temUrl()) {
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}else {
				return ResponseEntity.ok()
						.contentType(mediaType)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}


		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

	}


	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId) {
		catalogoFotoProdutoService.excluir(restauranteId, produtoId);
	} 

	private void verificarCompatibilidadeMediaType(MediaType mediaType, List<MediaType> mediaTypesAceitas)
			throws HttpMediaTypeNotAcceptableException {

		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaType));

		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}

	}


}