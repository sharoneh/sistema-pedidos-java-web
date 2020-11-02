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
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class ProdutoMB implements Serializable{
/*    private Produto produto = new Produto();
    private List<Produto> produtos;

    public void setProduto(Produto produto) {
        this.produto = produto;
    }*/

    ProdutoDAO dao = new ProdutoDAO();

    public Produto getProduto(int id) {
        Produto result = new Produto();
        try {
            dao = new ProdutoDAO();
            result = dao.find(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Produto> getProdutos() {
        List<Produto> result = new ArrayList<>();
        try {
            dao = new ProdutoDAO();
            result = dao.fetchAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public String getProdutoIdFromUrl(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        return paramMap.get("id");
    }
    
    public void excluir(Produto p) {
        try {
            dao = new ProdutoDAO();
            dao.removeProduto(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void prepararEdicao() {
        try{
            int produtoId = Integer.parseInt(getProdutoIdFromUrl());
            Produto produto = new Produto();
            produto = getProdutos().stream()
                .filter(prod -> prod.getId() == produtoId)
                .findFirst()
                .orElse(null);
        }catch(NumberFormatException ex){
            return;
        }
    }
    
    public String editar(Produto p){
        try {
            dao = new ProdutoDAO();
            dao.updateProduto(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "index.xhtml";
    }
    
    public Produto prepararAdicao() {
        return new Produto();
    }
    
    
    public String adicionar(Produto p){
        try {
            dao = new ProdutoDAO();
            dao.createProduto(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage("Produto cadastrado com sucesso"));
        
        return "index.xhtml";
    }   
    
}