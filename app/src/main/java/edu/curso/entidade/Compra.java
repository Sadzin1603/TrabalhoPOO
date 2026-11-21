package edu.curso.entidade;

import java.time.LocalDate;

public class Compra {
    private long id;
    private String status;
    private LocalDate dataCompra;
    private LocalDate previsaoEntrega;
    private int volumeTotal;
    private double valorFrete;
    private double valorTotal;
    private Fornecedor fornecedor;
    private Produto produto;

    public long getId() {
        return this.id;
    }
    public void setId(long valor) {
        this.id = valor;
    }

    public String getStatus() {
        return this.status;
    }
    public void setStatus(String valor) {
        this.status = valor;
    }

    public LocalDate getDataCompra() {
        return this.dataCompra;
    }
    public void setDataCompra(LocalDate valor) {
        this.dataCompra = valor;
    }

    public LocalDate getPrevisaoEntrega() {
        return this.previsaoEntrega;
    }
    public void setPrevisaoEntrega(LocalDate valor) {
        this.previsaoEntrega = valor;
    }

    public int getVolumeTotal() {
        return this.volumeTotal;
    }
    public void setVolumeTotal(int valor) {
        this.volumeTotal = valor;
    }

    public double getValorFrete() {
        return this.valorFrete;
    }
    public void setValorFrete(double valor) {
        this.valorFrete = valor;
    }

    public double getValorTotal() {
        return this.valorTotal;
    }
    public void setValorTotal(double valor) {
        this.valorTotal = valor;
    }

    public Fornecedor getFornecedor() {
        return this.fornecedor;
    }
    public void setFornecedor(Fornecedor valor) {
        this.fornecedor = valor;
    }
    public Produto getProduto() {
        return this.produto;
    }
    public void setProduto(Produto valor) {
        this.produto = valor;
    }

    @Override
    public String toString() {
        return "Compra[" + id + ", " + status + ", " + dataCompra + ", " + previsaoEntrega + ", " + volumeTotal + ", " + valorFrete + ", " + valorTotal + ", " + fornecedor + ", " + produto + "]";
    }
}
