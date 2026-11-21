package edu.curso.entidade;

public class Fornecedor extends PessoaJuridica {
    private long id;
    private String razaoSocial;

    public long getId() {
        return this.id;
    }
    public void setId(long valor) {
        this.id = valor;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }
    public void setRazaoSocial(String valor) {
        this.razaoSocial = valor;
    }

    @Override
    public String toString() {
        return "Fornecedor[" + id + ", " + getNome() + ", " + razaoSocial + ", " + getCnpj() + ", " + getTelefone() + ", " + getEmail() + ", " + getEndereco() + "]";
    }
}
