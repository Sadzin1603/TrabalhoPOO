package edu.curso.entidade;

import java.time.LocalDate;

public class Caixa {
    private long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    private double salario;
    private String turno;

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

    public String getCpf() {
        return this.cpf;
    }
    public void setCpf(String valor) {
        this.cpf = valor;
    }

    public String getTelefone() {
        return this.telefone;
    }
    public void setTelefone(String valor) {
        this.telefone = valor;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String valor) {
        this.email = valor;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }
    public void setDataNascimento(LocalDate valor) {
        this.dataNascimento = valor;
    }

    public double getSalario() {
        return this.salario;
    }
    public void setSalario(double valor) {
        this.salario = valor;
    }

    public String getTurno() {
        return this.turno;
    }
    public void setTurno(String valor) {
        this.turno = valor;
    }

    @Override
    public String toString() {
        return "Caixa[" + id + ", " + nome + ", " + cpf + ", " + telefone + ", " + email + ", " + dataNascimento + ", " + salario + ", " + turno + "]";
    }
}
