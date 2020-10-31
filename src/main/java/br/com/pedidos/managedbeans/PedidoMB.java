package br.com.pedidos.managedbeans;

import br.com.pedidos.dao.PedidoDAO;
import br.com.pedidos.models.Cliente;
import br.com.pedidos.models.ItemDoPedido;
import br.com.pedidos.models.Pedido;
import br.com.pedidos.models.Produto;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.FetchProfile;

@Named
@SessionScoped
public class PedidoMB implements Serializable {
/*  private Pedido pedido = new Pedido();
    private List<Pedido> pedidos;

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }*/

    PedidoDAO dao;

    public Pedido getPedido(int id) {
        System.out.println("Buscando pedido com ID = " + id);
        Pedido result = new Pedido();
        try {
            dao = new PedidoDAO();
            result = dao.find(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<ItemDoPedido> getItensPedido(int id) {
        System.out.println("Buscando itens do pedido do cliente com ID = " + id);
        List<ItemDoPedido> result = new ArrayList<>();
        try {
            dao = new PedidoDAO();
            result = dao.findItensDoCliente(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public List<Pedido> getPedidos() {
        System.out.println("Buscando todos os pedidos");
        List<Pedido> result = new ArrayList<>();
        try {
            dao = new PedidoDAO();
            result = dao.fetchAll();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public String getPedidoIdFromUrl(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        return paramMap.get("id");
    }
    
    public void show() {
         try{
            int pedidoId = Integer.parseInt(getPedidoIdFromUrl());
            Pedido pedido = new Pedido();
            pedido = getPedidos().stream()
                .filter(ped -> ped.getId() == pedidoId)
                .findFirst()
                .orElse(null);
        }catch(NumberFormatException ex){
            return;
        }
    }
    
    public int[] listaQuantidadeItens(){
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9,10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    }
    
    public String incluirPedido(Produto produto, int q, Pedido pedido) {
        System.out.println("Adicionando produto " + produto.getDescricao() + " ao pedido numero " + pedido.getId());
        try {
            dao = new PedidoDAO();
            ItemDoPedido item = new ItemDoPedido(q, produto);
            dao.saveItem(item, pedido.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "index.xhtml";
    }
}

