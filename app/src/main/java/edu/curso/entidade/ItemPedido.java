package edu.curso.entidade;

public class ItemPedido {
    private long id;
    private Produto produto;
    private int quantidade;
    private double valorUnitario;

    public long getId() {
        return this.id;
    }
    public void setId(long valor) {
        this.id = valor;
    }

    public Produto getProduto() {
        return this.produto;
    }
    public void setProduto(Produto valor) {
        this.produto = valor;
    }

    public int getQuantidade() {
        return this.quantidade;
    }
    public void setQuantidade(int valor) {
        this.quantidade = valor;
    }

    public double getValorUnitario() {
        return this.valorUnitario;
    }
    public void setValorUnitario(double valor) {
        this.valorUnitario = valor;
    }

    public double getSubtotal() {
        return Math.round(quantidade * valorUnitario * 100) / 100.0;
    }

    @Override
    public String toString() {
        return "ItemPedido[" + id + ", " + produto + ", " + quantidade + ", " + valorUnitario + "]";
    }
}