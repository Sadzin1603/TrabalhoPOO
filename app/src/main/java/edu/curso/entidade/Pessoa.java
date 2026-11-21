package edu.curso.entidade;

public class Pessoa{
    private String nome;
    private String telefone;
    private String email;
    private String endereco;

    public String getNome() {
        return this.nome;
    }
    public void setNome(String valor) {
        this.nome = valor;
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

    public String getEndereco() {
        return this.endereco;
    }
    public void setEndereco(String valor) {
        this.endereco = valor;
    }
}
