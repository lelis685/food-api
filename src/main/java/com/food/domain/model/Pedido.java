package com.food.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

import com.food.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Pedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;

	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;

	@Embedded
	private Endereco enderecoEntrega;

	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;

	@CreationTimestamp
	private LocalDateTime dataCriacao;

	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;

	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();
	
	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO);
		this.setDataConfirmacao(OffsetDateTime.now());
	}
	
	public void entregar() {
		setStatus(StatusPedido.ENTREGUE);
		this.setDataEntrega(OffsetDateTime.now());
	}
	
	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		this.setDataCancelamento(OffsetDateTime.now());
	}
	
	private void setStatus(StatusPedido novoStatusPedido) {
		if(this.getStatus().naoPodeAlterarPara(novoStatusPedido)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", this.getCodigo(),
					this.getStatus().getDescricao(), novoStatusPedido.getDescricao()));
		}
		this.status = novoStatusPedido;
	}

	/** Método para atribuir valor aos atributos 'valorTotal' e subtotal
	 * @return void*/
	public void calcularValorTotal() {
		if(this.getValorTotal() == null)
			this.setValorTotal(BigDecimal.ZERO);
		if(this.getSubtotal() == null)
			this.setSubtotal(BigDecimal.ZERO);
		for (ItemPedido item : this.getItens()) {
			item.calcularPrecoTotal();
			this.subtotal.add(item.getPrecoTotal());
		}
		this.valorTotal = getSubtotal().add(getTaxaFrete());
	}
	
	@PrePersist
	private void gerarCodigo() {
		this.setCodigo(UUID.randomUUID().toString());
	}

}
