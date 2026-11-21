package edu.curso.entidade;

public class Fornecedor {
    private long id;
    private String nome;
    private String razaoSocial;
    private String cnpj;
    private String telefone;
    private String email;
    private String endereco;

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

    public String getRazaoSocial() {
        return this.razaoSocial;
    }
    public void setRazaoSocial(String valor) {
        this.razaoSocial = valor;
    }

    public String getCnpj() {
        return this.cnpj;
    }
    public void setCnpj(String valor) {
        this.cnpj = valor;
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

    @Override
    public String toString() {
        return "Fornecedor[" + id + ", " + nome + ", " + razaoSocial + ", " + cnpj + ", " + telefone + ", " + email + ", " + endereco + "]";
    }
}
