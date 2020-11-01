package br.com.pedidos.models;

public class ItemDoPedido {
    private int quantidade;
    private Produto produto;

    public ItemDoPedido() {
    }

    public ItemDoPedido(int quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
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
}
