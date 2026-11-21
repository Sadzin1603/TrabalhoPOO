package edu.curso.controle;

import edu.curso.entidade.Fornecedor;
import edu.curso.persistencia.FornecedorDAO;
import edu.curso.persistencia.FornecedorDAOImpl;
import edu.curso.persistencia.PersistenciaException;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FornecedorControl {
    private ObservableList<Fornecedor> lista = FXCollections.observableArrayList();
    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty erroNome = new SimpleStringProperty("");
    private StringProperty razaoSocial = new SimpleStringProperty("");
    private StringProperty erroRazaoSocial = new SimpleStringProperty("");
    private StringProperty cnpj = new SimpleStringProperty("");
    private StringProperty erroCnpj = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty erroTelefone = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty erroEmail = new SimpleStringProperty("");
    private StringProperty endereco = new SimpleStringProperty("");
    private StringProperty erroEndereco = new SimpleStringProperty("");
    private FornecedorDAO dao = new FornecedorDAOImpl();

    public FornecedorControl() {
        try {
            pesquisarTodos();
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void popularFornecedor(Fornecedor e) {
        if (e != null) {
            nome.set(e.getNome());
            razaoSocial.set(e.getRazaoSocial());
            cnpj.set(e.getCnpj());
            telefone.set(e.getTelefone());
            email.set(e.getEmail());
            endereco.set(e.getEndereco());
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.pesquisarTodos());
    }

    // Valida cada campo individualmente: cada um recebe a sua mensagem de erro
    public boolean validar() {
        erroNome.set(Validador.nome(nome.get()));
        erroRazaoSocial.set(Validador.texto(razaoSocial.get()));
        erroCnpj.set(Validador.cnpj(cnpj.get()));
        erroTelefone.set(Validador.telefone(telefone.get()));
        erroEmail.set(Validador.email(email.get()));
        erroEndereco.set(Validador.texto(endereco.get()));
        return erroNome.get().isEmpty() && erroRazaoSocial.get().isEmpty() && erroCnpj.get().isEmpty() && erroTelefone.get().isEmpty() && erroEmail.get().isEmpty() && erroEndereco.get().isEmpty();
    }

    public boolean salvar() {
        if (!validar()) {
            return false;
        }
        Fornecedor e = new Fornecedor();
        e.setNome(nome.get().trim());
        e.setRazaoSocial(razaoSocial.get().trim());
        e.setCnpj(cnpj.get().trim());
        e.setTelefone(telefone.get().trim());
        e.setEmail(email.get().trim());
        e.setEndereco(endereco.get().trim());
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
        razaoSocial.set("");
        erroRazaoSocial.set("");
        cnpj.set("");
        erroCnpj.set("");
        telefone.set("");
        erroTelefone.set("");
        email.set("");
        erroEmail.set("");
        endereco.set("");
        erroEndereco.set("");
    }

    public void excluir(Fornecedor e) {
        dao.excluir(e);
        if (id.get() == e.getId()) {
            limparCampos();
        }
        pesquisarTodos();
    }

    public void atualizar(Fornecedor e) {
        id.set(e.getId());
        popularFornecedor(e);
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

    public StringProperty razaoSocialProperty() {
        return razaoSocial;
    }

    public StringProperty erroRazaoSocialProperty() {
        return erroRazaoSocial;
    }

    public StringProperty cnpjProperty() {
        return cnpj;
    }

    public StringProperty erroCnpjProperty() {
        return erroCnpj;
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

    public StringProperty enderecoProperty() {
        return endereco;
    }

    public StringProperty erroEnderecoProperty() {
        return erroEndereco;
    }

    public ObservableList<Fornecedor> getLista() {
        return lista;
    }
}
