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
    private long idFornecedor;

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

    public long getIdFornecedor() {
        return this.idFornecedor;
    }
    public void setIdFornecedor(long valor) {
        this.idFornecedor = valor;
    }

    @Override
    public String toString() {
        return "Compra[" + id + ", " + status + ", " + dataCompra + ", " + previsaoEntrega + ", " + volumeTotal + ", " + valorFrete + ", " + valorTotal + ", " + idFornecedor + "]";
    }
}
