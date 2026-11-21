package edu.curso.entidade;

import java.time.LocalDate;

public class PessoaFisica extends Pessoa {
    private String cpf;
    private LocalDate dataNascimento;

    public String getCpf() {
        return this.cpf;
    }
    public void setCpf(String valor) {
        this.cpf = valor;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }
    public void setDataNascimento(LocalDate valor) {
        this.dataNascimento = valor;
    }
}
