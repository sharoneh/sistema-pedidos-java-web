/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pedidos.managedbeans;

import br.com.pedidos.dao.ClienteDAO;
import br.com.pedidos.models.Cliente;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author sharonhasegawa
 */
@Named
@RequestScoped
public class ClienteMB {
    private ClienteDAO dao;
    private Cliente cliente;
    
    @PostConstruct
    public void init() {
        this.cliente = new Cliente();
        this.dao = new ClienteDAO();
    }
    
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public String adicionaCliente() throws SQLException {
        try {
            this.dao.createCliente(this.cliente);
        }  catch (SQLException e) {
            System.out.println("Erro na tentativa de adicionar um cliente: " + e.getMessage());
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage("Cliente cadastrado com sucesso"));

        return "index.xhtml";
    }
    
    //    public Cliente getCliente(int id) throws SQLException {
//        System.out.println("Buscando cliente com ID = " + id);
//        Cliente result = new Cliente();
//        try {
//            dao = new ClienteDAO();
//            result = dao.find(id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
    
    //
//    public String editar(Cliente cliente) throws SQLException {
//        System.out.println("Editando cliente com CPF = " + cliente.getNomeCompleto());
//        try {
//            dao = new ClienteDAO();
//            dao.updateCliente(cliente);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return "index.xhtml";
//    }
//    
//    public void excluir(int id) throws SQLException {
//        System.out.println("Tentando excluir cliente com ID = " + id);
//        Cliente c = new Cliente();
//        c.setId(id);
//        try {
//            dao = new ClienteDAO();
//            dao.removeCliente(c);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public String getClienteIdFromUrl(){
//        FacesContext context = FacesContext.getCurrentInstance();
//        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
//        return paramMap.get("id");
//    }

//    public void prepararEdicao(int id) {
//        System.out.println("id: "+id);
//        
////        try {
//////            int clienteId = Integer.parseInt(getClienteIdFromUrl());
////            this.cliente = this.clientes.stream()
////                .filter(c -> c.getId() == id)
////                .findFirst()
////                .orElse(null);
////        } catch(NumberFormatException ex){
////            ex.printStackTrace();
////        }
//    }

//    public void prepararAdicao() {
//        System.out.println("prepararAdicao");
//        this.cliente = new Cliente();
//    }
    //    public void setCliente(Cliente cliente) {
//        this.cliente = cliente;
//    }
//
//    public Cliente getCliente(int id) throws SQLException {
//        System.out.println("Buscando cliente com ID = " + id);
//        Cliente result = new Cliente();
//        try {
//            dao = new ClienteDAO();
//            result = dao.find(id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
}
