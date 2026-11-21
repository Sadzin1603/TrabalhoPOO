package edu.curso.entidade;

import java.time.LocalDate;

public class Produto {
    private long id;
    private String nome;
    private String marca;
    private double valorUnitario;
    private double valorAtacado;
    private int qtdAtacado;
    private LocalDate validade;

    public long getId() {
        return this.id;
    }
    public void setId(long valor) {
        this.id = valor;
    }

    public String getNome() {
        return this.nome;
    }
    public void setNome(String valor) {
        this.nome = valor;
    }

    public String getMarca() {
        return this.marca;
    }
    public void setMarca(String valor) {
        this.marca = valor;
    }

    public double getValorUnitario() {
        return this.valorUnitario;
    }
    public void setValorUnitario(double valor) {
        this.valorUnitario = valor;
    }

    public double getValorAtacado() {
        return this.valorAtacado;
    }
    public void setValorAtacado(double valor) {
        this.valorAtacado = valor;
    }

    public int getQtdAtacado() {
        return this.qtdAtacado;
    }
    public void setQtdAtacado(int valor) {
        this.qtdAtacado = valor;
    }

    public LocalDate getValidade() {
        return this.validade;
    }
    public void setValidade(LocalDate valor) {
        this.validade = valor;
    }

    @Override
    public String toString() {
        return "Produto[" + id + ", " + nome + ", " + marca + ", " + valorUnitario + ", " + valorAtacado + ", " + qtdAtacado + ", " + validade + "]";
    }
}
