package br.com.pedidos.managedbeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.pedidos.dao.PedidoDAO;
import br.com.pedidos.models.ItemDoPedido;
import br.com.pedidos.models.Pedido;
import br.com.pedidos.models.Produto;

@ManagedBean
@RequestScoped
public class PedidoMB implements Serializable {
/*  private Pedido pedido = new Pedido();
    private List<Pedido> pedidos;

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }*/

	private PedidoDAO dao;
    private List<Pedido> pedidos;
    private Pedido pedido;
    private List<ItemDoPedido> itensPedido;
    private List<ItemDoPedido> prodPedido;
    private int quantidade;
    private Produto produto;

    private int[] qtdes = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    
	private final String PEDIDOS_PAGE = "index.xhtml";
    
    @PostConstruct
    public void init() {
        this.dao = new PedidoDAO();
        this.pedido = new Pedido();
        this.pedidos = new ArrayList<>();
        this.itensPedido = new ArrayList<>();
        this.prodPedido = new ArrayList<>();
        this.produto = new Produto();
        
        try {
            this.pedidos = dao.fetchAll();
        } catch (Exception e) {
            this.addMessage("Erro ao buscar a lista de clientes: " + e.getMessage());
        }
        
        String pedidoIdParam = this.getPedidoIdParam();
        
        if (pedidoIdParam != null) {
            this.setPedido(Integer.parseInt(pedidoIdParam));
        }
    }
    
    public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getPedidoIdParam(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        return paramMap.get("id");
    }
    
    public void addMessage(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage(msg));
    }

    public void setPedido(int id) {
        try {
            this.pedido = dao.find(id);
        } catch (SQLException e) {
            this.addMessage("SQLException: Erro ao definir o pedido do managed bean PedidoMB: " + e.getMessage());
        }
    }
    
    public Pedido getPedido() {
        return this.pedido;
    }
    
    public List<Pedido> getPedidos() {
        return this.pedidos;
    }
 
    public List<ItemDoPedido> getItensPedido() {
        return this.itensPedido;
    }

    public int[] getQtdes() {
		return qtdes;
	}

	public void setQtdes(int[] qtdes) {
		this.qtdes = qtdes;
	}
    
    public String getPedidoIdFromUrl(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        return paramMap.get("id");
    }
    
    public String incluirPedido(Produto produto, int q, Pedido pedido) {
        System.out.println("Adicionando produto " + produto.getDescricao() + " ao pedido numero " + pedido.getId());
        
        try {
            ItemDoPedido item = new ItemDoPedido(q, produto);
            this.dao.saveItem(item, pedido.getId());
        } catch (SQLException e) {
            this.addMessage("Erro ao incluir um pedido: " + e.getMessage());
        }
        
        return this.PEDIDOS_PAGE;
    }

	public List<ItemDoPedido> getProdPedido() {
		return prodPedido;
	}
	
	public void addProduto() {
		ItemDoPedido item = new ItemDoPedido();
		item.setQuantidade(quantidade);
		item.setProduto(produto);
		prodPedido.add(item);
	}
}

