package br.com.pedidos.managedbeans;

import br.com.pedidos.dao.ClienteDAO;
import br.com.pedidos.models.Cliente;

//import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
//import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class ClienteMB {
    ClienteDAO dao;
    List<Cliente> clientes;
    Cliente cliente;
    
    @PostConstruct
    public void init() {
        this.clientes = new ArrayList<Cliente>();
        
        try {
            dao = new ClienteDAO();
            this.clientes = dao.fetchAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente(int id) throws SQLException {
        System.out.println("Buscando cliente com ID = " + id);
        Cliente result = new Cliente();
        try {
            dao = new ClienteDAO();
            result = dao.find(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public List<Cliente> getClientes() throws SQLException {
        return this.clientes;
    }

    public String adicionar(Cliente cliente) throws SQLException {
        System.out.println("Criando cliente com CPF = " + cliente.getCpf());
        try {
            dao = new ClienteDAO();
            dao.createCliente(cliente);
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage("Cliente cadastrado com sucesso"));

        return "index.xhtml";
    }

    public String editar(Cliente cliente) throws SQLException {
        System.out.println("Editando cliente com CPF = " + cliente.getNomeCompleto());
        try {
            dao = new ClienteDAO();
            dao.updateCliente(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "index.xhtml";
    }
    
    public void excluir(int id) throws SQLException {
        System.out.println("Tentando excluir cliente com ID = " + id);
        Cliente c = new Cliente();
        c.setId(id);
        try {
            dao = new ClienteDAO();
            dao.removeCliente(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getClienteIdFromUrl(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        return paramMap.get("id");
    }

    public void prepararEdicao(int id) {
        System.out.println("id: "+id);
        
//        try {
////            int clienteId = Integer.parseInt(getClienteIdFromUrl());
//            this.cliente = this.clientes.stream()
//                .filter(c -> c.getId() == id)
//                .findFirst()
//                .orElse(null);
//        } catch(NumberFormatException ex){
//            ex.printStackTrace();
//        }
    }

    public Cliente prepararAdicao() {
       return new Cliente();
    }
}
