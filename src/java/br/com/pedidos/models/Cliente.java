package br.com.pedidos.models;

public class Cliente {
    private int id;
    private String nome;
    private String sobrenome;
    private String cpf;

    public Cliente() {
        this.id = 0;
        this.nome = "";
        this.sobrenome = "";
        this.cpf = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getNomeCompleto(){
        return this.nome + " " + this.sobrenome;
    }
}
