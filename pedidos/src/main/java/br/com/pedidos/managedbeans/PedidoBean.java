package br.com.pedidos.managedbeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.pedidos.dao.ClienteDAO;
import br.com.pedidos.dao.PedidoDAO;
import br.com.pedidos.dao.ProdutoDAO;
import br.com.pedidos.models.Cliente;
import br.com.pedidos.models.ItemDoPedido;
import br.com.pedidos.models.Pedido;
import br.com.pedidos.models.Produto;

@Named
@ViewScoped
public class PedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Pedido pedido;
	private List<Pedido> pedidos;
	private List<Cliente> clientes;
	private String clienteId;

	@PostConstruct
	public void init() {

		Pedido p = new Pedido();
		p.setCliente(new Cliente());

		List<ItemDoPedido> itens = new ArrayList<>();

		List<Produto> produtos;
		produtos = new ProdutoDAO().fetchAll();

		for (Produto produto : produtos) {
			ItemDoPedido item = new ItemDoPedido();
			item.setProduto(produto);
			itens.add(item);
		}

//		for(ItemDoPedido item : itens) {
//			item.setPedido(p);
//		}

		p.setItens(itens);
		this.setPedido(p);
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Pedido> getPedidos() {
//		pedidos = pedidoAppService.obterTodos();
		return pedidos;
	}

	public List<Cliente> getClientes() {
		try {
			clientes = new ClienteDAO().fetchAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}

	public void salvar(Pedido pedido) {
		try {
			pedido.setData(new Date());

			List<ItemDoPedido> itens = new ArrayList<ItemDoPedido>();
			for (ItemDoPedido item : pedido.getItens()) {
				if (item.getQuantidade() > 0) {
					itens.add(item);
				}
			}
			pedido.setItens(itens);

			pedido = new PedidoDAO().salvarPedido(pedido);
			
			for (ItemDoPedido item : pedido.getItens()) {
	            new PedidoDAO().salvarItem(item, pedido.getId());
			}
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), null));
			e.printStackTrace();
		}
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

}
