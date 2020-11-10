package br.com.pedidos.managedbeans;

import br.com.pedidos.dao.PedidoDAO;
import br.com.pedidos.models.ItemDoPedido;
import br.com.pedidos.models.Pedido;
import br.com.pedidos.models.Produto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class PedidoMB {
    private PedidoDAO dao;
    private List<Pedido> pedidos;
    private Pedido pedido;
    private List<ItemDoPedido> itens;
    private final String PEDIDOS_PAGE = "index.xhtml";
    
    @PostConstruct
    public void init() {
        this.dao = new PedidoDAO();
        this.pedido = new Pedido();
        this.pedidos = new ArrayList<>();
        this.itens = new ArrayList<>();
        
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

    public List<ItemDoPedido> getItensPedido(int id) {
        System.out.println("Buscando itens do pedido do cliente com ID = " + id);
        
        try {
            this.itens = this.dao.findItensDoCliente(id);
        } catch (SQLException e) {
            this.addMessage("Erro ao buscar a lista de itens do pedido: " + e.getMessage());
        }
        return this.itens;
    }
    
    public String getPedidoIdFromUrl(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        return paramMap.get("id");
    }
    
    public int[] listaQuantidadeItens(){
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
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
}

