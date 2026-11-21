package edu.curso.entidade;

public class Cliente extends PessoaFisica {
    private long id;

    public long getId() {
        return this.id;
    }
    public void setId(long valor) {
        this.id = valor;
    }

    @Override
    public String toString() {
        return "Cliente[" + id + ", " + getNome() + ", " + getCpf() + ", " + getTelefone() + ", " + getEmail() + ", " + getDataNascimento() + ", " + getEndereco() + "]";
    }
}
