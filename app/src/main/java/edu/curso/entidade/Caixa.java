package edu.curso.entidade;

public class Caixa extends PessoaFisica {
    private long id;
    private double salario;
    private String turno;

    public long getId() {
        return this.id;
    }
    public void setId(long valor) {
        this.id = valor;
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
        return "Caixa[" + id + ", " + getNome() + ", " + getCpf() + ", " + getTelefone() + ", " + getEmail() + ", " + getDataNascimento() + ", " + salario + ", " + turno + "]";
    }
}
