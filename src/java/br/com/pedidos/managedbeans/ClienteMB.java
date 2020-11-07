/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pedidos.managedbeans;

import br.com.pedidos.dao.ClienteDAO;
import br.com.pedidos.models.Cliente;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author sharonhasegawa
 */
@Named
@ViewScoped
public class ClienteMB implements Serializable {
    private ClienteDAO dao;
    private Cliente cliente;
    
    @PostConstruct
    public void init() {
        this.cliente = new Cliente();
        this.dao = new ClienteDAO();
        
        String clienteIdParam = this.getClienteIdParam();
        
        if (clienteIdParam != null) {
            this.setCliente(Integer.parseInt(clienteIdParam));
        }
    }
    
    public String getClienteIdParam(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        return paramMap.get("id");
    }
    
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public void setCliente(int id) {
        try {
            this.cliente = this.dao.find(id);
        } catch (SQLException e) {
            System.out.println("SQLException: Erro ao definir o cliente do managed bean ClienteMB: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: Erro ao definir o cliente do managed bean ClienteMB: " + e.getMessage());
        }
    }
    
    public String adicionar() {
        try {
            this.dao.createCliente(this.cliente);
        }  catch (SQLException e) {
            System.out.println("Erro na tentativa de adicionar um cliente: " + e.getMessage());
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage("Cliente " + this.cliente.getNomeCompleto() + " cadastrado com sucesso!"));

        return "index.xhtml";
    }
    
    public void excluir() {
        try {
            this.dao.removeCliente(this.cliente);
        } catch (SQLException e) {
            System.out.println("Erro na tentativa de excluir um cliente: " + e.getMessage());
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage("Cliente " + this.cliente.getNomeCompleto() + " excluído com sucesso!"));
    }
    
    public String editar() {
        try {
            this.dao.updateCliente(this.cliente);
        } catch (SQLException e) {
            System.out.println("Erro ao editar cliente: " + e.getMessage());
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage("Cliente " + this.cliente.getNomeCompleto() + " editado com sucesso!"));
        
        return "index.xhtml";
    }
}
