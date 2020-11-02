package br.com.pedidos.managedbeans;

import br.com.pedidos.dao.ClienteDAO;
import br.com.pedidos.models.Cliente;

import javax.inject.Named;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class ClientesMB {
    ClienteDAO dao;
    List<Cliente> clientes;
    Cliente cliente;
    
    @PostConstruct
    public void init() {
        this.clientes = new ArrayList<>();
        this.cliente = new Cliente();
        this.dao = new ClienteDAO();
        
        try {
            this.clientes = dao.fetchAll();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar a lista de clientes: " + e.getMessage());
        }
    }
    
    public List<Cliente> getClientes() throws SQLException {
        return this.clientes;
    }
}
