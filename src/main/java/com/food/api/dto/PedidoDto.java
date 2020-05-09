package com.food.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDto {

	@ApiModelProperty(example = "8d774bcf-b238-42f3-aef1-5fb3887544554")
	private String codigo;
	
	@ApiModelProperty(example = "87.20")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "17.20")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "17.20")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "ENTREGUE")
	private String status;
	
	@ApiModelProperty(example = "2019-11-03T02:00:30Z")
	private OffsetDateTime dataCriacao;
	
	@ApiModelProperty(example = "2019-11-03T03:00:30Z")
	private OffsetDateTime dataConfirmacao;
	
	@ApiModelProperty(example = "2019-11-04T02:00:30Z")
	private OffsetDateTime dataEntrega;
	
	@ApiModelProperty(example = "2019-11-04T02:00:30Z")
	private OffsetDateTime dataCancelamento;
	
	private RestauranteResumoDto restaurante;
	
	private UsuarioDto cliente;
	
	private FormaPagamentoDto formaPagamento;
	
	private EnderecoDto enderecoEntrega;
	
	private List<ItemPedidoDto> itens;   

}
