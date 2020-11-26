/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pedidos.managedbeans;

import br.com.pedidos.dao.ProdutoDAO;
import br.com.pedidos.models.Produto;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ProdutoMB implements Serializable {
    private ProdutoDAO dao;
    private List<Produto> produtos;
    private Produto produto;
    private final String PRODUCTS_PAGE = "index.xhtml";
    
    @PostConstruct
    public void init() {
        this.dao = new ProdutoDAO();
        this.produtos = new ArrayList<>();
        this.produto = new Produto();
        
        try {
            this.produtos = dao.fetchAll();
        } catch (Exception e) {
            this.addMessage("Erro ao buscar a lista de produtos.");
        }
        
        String produtoIdParam = this.getProdutoIdParam();
        
        if (produtoIdParam != null) {
            this.setProduto(Integer.parseInt(produtoIdParam));
        }
    }
    
    public String getProdutoIdParam(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        return paramMap.get("id");
    }
    
    public void addMessage(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage(msg));
    }
    
    public List<Produto> getProdutos() {
        return this.produtos;
    }
    
    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(int id) {
        try {
            this.produto = this.dao.find(id);
        } catch (SQLException | NumberFormatException e) {
            this.addMessage("Erro ao definir o produto do managed bean ProdutoMB.");
        }
    }
    
    public String adicionar() {
        try {
            this.dao.createProduto(this.produto);
        }  catch (SQLException e) {
            this.addMessage("Erro na tentativa de adicionar um produto.");
            return this.PRODUCTS_PAGE;
        }
        
        this.addMessage("Produto " + this.produto.getDescricao() + " cadastrado com sucesso!");
        return this.PRODUCTS_PAGE;
    }
    
    public void excluir() {
        try {
            this.dao.removeProduto(this.produto);
            this.produtos = this.dao.fetchAll();
        } catch (SQLException e) {
            this.addMessage("Erro na tentativa de excluir um produto.");
            return;
        }
        
        this.addMessage("Produto " + this.produto.getDescricao()+ " excluído com sucesso!");
    }
    
    public String editar() {
        try {
            this.dao.updateProduto(this.produto);
        }  catch (SQLException e) {
            this.addMessage("Erro na tentativa de editar um produto.");
            return this.PRODUCTS_PAGE;
        }
        
        this.addMessage("Produto " + this.produto.getDescricao() + " editado com sucesso!");
        return this.PRODUCTS_PAGE;
    }
}