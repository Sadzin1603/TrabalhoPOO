package edu.curso.entidade;

public class PessoaJuridica extends Pessoa{
    private String cnpj;

    public String getCnpj() {
        return this.cnpj;
    }
    public void setCnpj(String valor) {
        this.cnpj = valor;
    }
}
