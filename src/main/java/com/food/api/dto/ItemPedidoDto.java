package com.food.api.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDto {

	@ApiModelProperty(example = "1")
    private Long produtoId;
    
	@ApiModelProperty(example = "Porco com molho agridoce")
    private String produtoNome;
	
	@ApiModelProperty(example = "11")
    private Integer quantidade;
	
	@ApiModelProperty(example = "11.58")
    private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "110.55")
    private BigDecimal precoTotal;
	
	@ApiModelProperty(example = "Menos picante, por favor")
    private String observacao;    
	
}
