package com.food.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.assembler.ProdutoDtoInputDisassembler;
import com.food.api.dto.ProdutoDto;
import com.food.api.dto.input.ProdutoDtoInput;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.service.CadastroProdutoService;
import com.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	private static final Class<ProdutoDto> PRODUTO_DTO_CLASS = ProdutoDto.class;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private GenericDtoAssembler<Produto, ProdutoDto> assembler;

	@Autowired
	private ProdutoDtoInputDisassembler diassembler;


	@GetMapping
	public List<ProdutoDto> listar(@PathVariable Long restauranteId, @RequestParam(defaultValue = "false") boolean incluirInativos) {
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
		List<Produto> todosProdutos = new ArrayList<Produto>();
		if(incluirInativos) {
			todosProdutos = cadastroProdutoService.buscarProdutosPorRestaurante(restaurante);
		}else {
			todosProdutos = cadastroProdutoService.buscarProdutosAtivoPorRestaurante(restaurante);
		}
		return assembler.toCollectionRepresentationModel(todosProdutos, PRODUTO_DTO_CLASS);
	}

	@GetMapping("/{produtoId}")
	public ProdutoDto listar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {	
		Produto produto = cadastroProdutoService.buscar(restauranteId, produtoId);
		return assembler.toRepresentationModel(produto, PRODUTO_DTO_CLASS);
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDto salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoDtoInput produtoInput) {	

		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
		Produto produto = diassembler.toDomainObject(produtoInput);

		produto.setRestaurante(restaurante);

		cadastroProdutoService.salvar(produto);

		return assembler.toRepresentationModel(produto, PRODUTO_DTO_CLASS);
	}


	@PutMapping("/{produtoId}")
	public ProdutoDto atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoDtoInput produtoInput) {

		Produto produtoAtual = cadastroProdutoService.buscar(restauranteId, produtoId);

		diassembler.copyToDomainObject(produtoInput, produtoAtual);

		produtoAtual = cadastroProdutoService.salvar(produtoAtual);

		return assembler.toRepresentationModel(produtoAtual, PRODUTO_DTO_CLASS);
	} 





}
