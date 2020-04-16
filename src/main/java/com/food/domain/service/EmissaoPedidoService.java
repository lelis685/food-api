package com.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.NegocioException;
import com.food.domain.exception.PedidoNaoEncontradoException;
import com.food.domain.model.Cidade;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.ItemPedido;
import com.food.domain.model.Pedido;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.model.Usuario;
import com.food.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	private static final String MSG_FORMA_PAGAMENTO_NAO_ACEITA = "Forma Pagamento '%s' não é aceita por esse restaurante.";

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;


	public Pedido buscar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}

	public List<Pedido> listar() {
		return pedidoRepository.findAll();
	}         

	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido, pedido.getRestaurante().getId());
		pedido.calcularValorTotal();
		return pedidoRepository.save(pedido);
	}


	private void validarPedido(Pedido pedido) {
		Usuario cliente = cadastroUsuarioService.buscar(1L);
		pedido.setCliente(cliente);

		Cidade cidade = cadastroCidadeService.buscar(pedido.getEnderecoEntrega().getCidade().getId());
		pedido.getEnderecoEntrega().setCidade(cidade);

		Restaurante restaurante = cadastroRestauranteService.buscar(pedido.getRestaurante().getId());
		pedido.setRestaurante(restaurante);
		pedido.setTaxaFrete(restaurante.getTaxaFrete());

		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscar(pedido.getFormaPagamento().getId());
		pedido.setFormaPagamento(formaPagamento);

		if(restaurante.isFormaPagamentoNaoAceita(formaPagamento)) {
			throw new NegocioException(String.format(MSG_FORMA_PAGAMENTO_NAO_ACEITA, formaPagamento.getDescricao()));
		}
	}

	private void validarItens(Pedido pedido, Long restauranteId) {
		for(ItemPedido item : pedido.getItens()) {
			Produto produto = cadastroProdutoService.buscar(restauranteId, item.getProduto().getId());
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		}
	}


}  