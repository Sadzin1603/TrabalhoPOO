package edu.curso.controle;

import edu.curso.dao.CaixaDAO;
import edu.curso.dao.CaixaDAOImpl;
import edu.curso.dao.PersistenciaException;
import edu.curso.entidade.Caixa;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CaixaControl {
    private ObservableList<Caixa> lista = FXCollections.observableArrayList();
    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty erroNome = new SimpleStringProperty("");
    private StringProperty cpf = new SimpleStringProperty("");
    private StringProperty erroCpf = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty erroTelefone = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty erroEmail = new SimpleStringProperty("");
    private StringProperty dataNascimento = new SimpleStringProperty("");
    private StringProperty erroDataNascimento = new SimpleStringProperty("");
    private StringProperty salario = new SimpleStringProperty("");
    private StringProperty erroSalario = new SimpleStringProperty("");
    private StringProperty turno = new SimpleStringProperty("");
    private StringProperty erroTurno = new SimpleStringProperty("");
    private CaixaDAO dao = new CaixaDAOImpl();

    public CaixaControl() {
        try {
            pesquisarTodos();
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void popularCaixa(Caixa e) {
        if (e != null) {
            nome.set(e.getNome());
            cpf.set(e.getCpf());
            telefone.set(e.getTelefone());
            email.set(e.getEmail());
            dataNascimento.set(Validador.formatarData(e.getDataNascimento()));
            salario.set(String.valueOf(e.getSalario()));
            turno.set(e.getTurno());
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.pesquisarTodos());
    }

    // Valida cada campo individualmente: cada um recebe a sua mensagem de erro
    public boolean validar() {
        erroNome.set(Validador.nome(nome.get()));
        erroCpf.set(Validador.cpf(cpf.get()));
        erroTelefone.set(Validador.telefone(telefone.get()));
        erroEmail.set(Validador.email(email.get()));
        erroDataNascimento.set(Validador.nascimento(dataNascimento.get()));
        erroSalario.set(Validador.decimal(salario.get()));
        erroTurno.set(Validador.texto(turno.get()));
        return erroNome.get().isEmpty() && erroCpf.get().isEmpty() && erroTelefone.get().isEmpty() && erroEmail.get().isEmpty() && erroDataNascimento.get().isEmpty() && erroSalario.get().isEmpty() && erroTurno.get().isEmpty();
    }

    public boolean salvar() {
        if (!validar()) {
            return false;
        }
        Caixa e = new Caixa();
        e.setNome(nome.get().trim());
        e.setCpf(cpf.get().trim());
        e.setTelefone(telefone.get().trim());
        e.setEmail(email.get().trim());
        e.setDataNascimento(Validador.parseData(dataNascimento.get()));
        e.setSalario(Validador.parseDecimal(salario.get()));
        e.setTurno(turno.get().trim());
        if (id.get() == 0) {
            dao.salvar(e);
        } else {
            dao.atualizar(id.get(), e);
        }
        pesquisarTodos();
        limparCampos();
        return true;
    }

    public void limparCampos() {
        id.set(0);
        nome.set("");
        erroNome.set("");
        cpf.set("");
        erroCpf.set("");
        telefone.set("");
        erroTelefone.set("");
        email.set("");
        erroEmail.set("");
        dataNascimento.set("");
        erroDataNascimento.set("");
        salario.set("");
        erroSalario.set("");
        turno.set("");
        erroTurno.set("");
    }

    public void excluir(Caixa e) {
        dao.excluir(e);
        if (id.get() == e.getId()) {
            limparCampos();
        }
        pesquisarTodos();
    }

    public void atualizar(Caixa e) {
        id.set(e.getId());
        popularCaixa(e);
    }

    // Filtra a tabela pelo campo "nome" digitado no formulário
    public void pesquisar() {
        String termo = nome.get().trim().toLowerCase();
        pesquisarTodos();
        if (!termo.isEmpty()) {
            lista.removeIf(e -> e == null || !String.valueOf(e.getNome()).toLowerCase().contains(termo));
        }
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty erroNomeProperty() {
        return erroNome;
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    public StringProperty erroCpfProperty() {
        return erroCpf;
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public StringProperty erroTelefoneProperty() {
        return erroTelefone;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty erroEmailProperty() {
        return erroEmail;
    }

    public StringProperty dataNascimentoProperty() {
        return dataNascimento;
    }

    public StringProperty erroDataNascimentoProperty() {
        return erroDataNascimento;
    }

    public StringProperty salarioProperty() {
        return salario;
    }

    public StringProperty erroSalarioProperty() {
        return erroSalario;
    }

    public StringProperty turnoProperty() {
        return turno;
    }

    public StringProperty erroTurnoProperty() {
        return erroTurno;
    }

    public ObservableList<Caixa> getLista() {
        return lista;
    }
}
