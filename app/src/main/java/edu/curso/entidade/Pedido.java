package edu.curso.entidade;

import java.time.LocalDate;

public class Pedido {
    private long id;
    private String status;
    private String formaPagamento;
    private LocalDate dataPedido;
    private double valorTotal;
    private long idCliente;
    private long idCaixa;

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

    public String getFormaPagamento() {
        return this.formaPagamento;
    }
    public void setFormaPagamento(String valor) {
        this.formaPagamento = valor;
    }

    public LocalDate getDataPedido() {
        return this.dataPedido;
    }
    public void setDataPedido(LocalDate valor) {
        this.dataPedido = valor;
    }

    public double getValorTotal() {
        return this.valorTotal;
    }
    public void setValorTotal(double valor) {
        this.valorTotal = valor;
    }

    public long getIdCliente() {
        return this.idCliente;
    }
    public void setIdCliente(long valor) {
        this.idCliente = valor;
    }

    public long getIdCaixa() {
        return this.idCaixa;
    }
    public void setIdCaixa(long valor) {
        this.idCaixa = valor;
    }

    @Override
    public String toString() {
        return "Pedido[" + id + ", " + status + ", " + formaPagamento + ", " + dataPedido + ", " + valorTotal + ", " + idCliente + ", " + idCaixa + "]";
    }
}
