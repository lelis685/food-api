package com.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.assembler.CidadeDtoInputDisassembler;
import com.food.api.assembler.GenericDtoAssembler;
import com.food.api.dto.CidadeDto;
import com.food.api.dto.input.CidadeDtoInput;
import com.food.domain.exception.EstadoNaoEncontradoException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Cidade;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	private static final Class<CidadeDto> CIDADE_DTO_CLASS = CidadeDto.class;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private GenericDtoAssembler<Cidade, CidadeDto> assembler;

	@Autowired
	private CidadeDtoInputDisassembler cidadeDtoInputDisassembler;

	@ApiOperation(value = "Lista as cidades")
	@GetMapping
	public List<CidadeDto> listar() {
		return assembler.toCollectionRepresentationModel(cidadeRepository.findAll(),CIDADE_DTO_CLASS);
	}

	@ApiOperation(value = "Busca uma cidade por id")
	@GetMapping("/{cidadeId}")
	public CidadeDto buscar(@ApiParam(value = "ID de uma cidade", example = "1") 
							@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscar(cidadeId);
		return assembler.toRepresentationModel(cidade, CIDADE_DTO_CLASS);
	}

	@ApiOperation(value = "Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDto adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") 
								@Valid @RequestBody CidadeDtoInput cidadeInput) {
		try {
			Cidade cidade = cidadeDtoInputDisassembler.toDomainObject(cidadeInput);
			cidade = cadastroCidade.salvar(cidade);
			return assembler.toRepresentationModel(cidade, CIDADE_DTO_CLASS);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Atualiza uma cidade por id")
	@PutMapping("/{cidadeId}")
	public CidadeDto atualizar(
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") 
			@Valid @RequestBody CidadeDtoInput cidadeInput , 
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long cidadeId ) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);

			cidadeDtoInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cadastroCidade.salvar(cidadeAtual);

			return assembler.toRepresentationModel(cidadeAtual,CIDADE_DTO_CLASS);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Exclui uma cidade por id")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1") 
						@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}


}