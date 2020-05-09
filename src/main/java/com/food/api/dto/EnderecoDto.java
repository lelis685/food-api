package com.food.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDto {

	@ApiModelProperty(example = "38400-999")
	private String cep;
	
	@ApiModelProperty(example = "Rua João Pinheiro")
	private String logradouro;
	
	@ApiModelProperty(example = "100")
	private String numero;
	
	@ApiModelProperty(example = "Na esquina")
	private String complemento;
	
	@ApiModelProperty(example = "Centro")
	private String bairro;
	
	private CidadeResumoDto cidade;
}
