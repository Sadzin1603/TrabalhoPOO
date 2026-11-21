package edu.curso.controle;

import edu.curso.dao.ProdutoDAO;
import edu.curso.dao.ProdutoDAOImpl;
import edu.curso.entidade.Produto;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutoControl {
    private ObservableList<Produto> lista = FXCollections.observableArrayList();
    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty erroNome = new SimpleStringProperty("");
    private StringProperty marca = new SimpleStringProperty("");
    private StringProperty erroMarca = new SimpleStringProperty("");
    private StringProperty valorUnitario = new SimpleStringProperty("");
    private StringProperty erroValorUnitario = new SimpleStringProperty("");
    private StringProperty valorAtacado = new SimpleStringProperty("");
    private StringProperty erroValorAtacado = new SimpleStringProperty("");
    private StringProperty qtdAtacado = new SimpleStringProperty("");
    private StringProperty erroQtdAtacado = new SimpleStringProperty("");
    private StringProperty validade = new SimpleStringProperty("");
    private StringProperty erroValidade = new SimpleStringProperty("");
    private ProdutoDAO dao = new ProdutoDAOImpl();

    public ProdutoControl() {
        pesquisarTodos();
    }

    public void popularProduto(Produto e) {
        if (e != null) {
            nome.set(e.getNome());
            marca.set(e.getMarca());
            valorUnitario.set(String.valueOf(e.getValorUnitario()));
            valorAtacado.set(String.valueOf(e.getValorAtacado()));
            qtdAtacado.set(String.valueOf(e.getQtdAtacado()));
            validade.set(Validador.formatarData(e.getValidade()));
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.pesquisarTodos());
    }

    // Valida cada campo individualmente: cada um recebe a sua mensagem de erro
    public boolean validar() {
        erroNome.set(Validador.nome(nome.get()));
        erroMarca.set(Validador.texto(marca.get()));
        erroValorUnitario.set(Validador.decimalPositivo(valorUnitario.get()));
        erroValorAtacado.set(Validador.decimalPositivo(valorAtacado.get()));
        erroQtdAtacado.set(Validador.inteiroPositivo(qtdAtacado.get()));
        if (erroValorUnitario.get().isEmpty() && erroValorAtacado.get().isEmpty()
            && Validador.parseDecimal(valorAtacado.get()) > Validador.parseDecimal(valorUnitario.get())) {
            erroValorAtacado.set("O valor de atacado não pode ser maior que o unitário");
        }
        erroValidade.set(Validador.data(validade.get()));
        return Validador.semErros(erroNome, erroMarca, erroValorUnitario, erroValorAtacado, erroQtdAtacado, erroValidade);
    }

    public boolean salvar() {
        if (!validar()) {
            return false;
        }
        Produto e = new Produto();
        e.setNome(nome.get().trim());
        e.setMarca(marca.get().trim());
        e.setValorUnitario(Validador.parseDecimal(valorUnitario.get()));
        e.setValorAtacado(Validador.parseDecimal(valorAtacado.get()));
        e.setQtdAtacado(Integer.parseInt(qtdAtacado.get().trim()));
        e.setValidade(Validador.parseData(validade.get()));
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
        marca.set("");
        erroMarca.set("");
        valorUnitario.set("");
        erroValorUnitario.set("");
        valorAtacado.set("");
        erroValorAtacado.set("");
        qtdAtacado.set("");
        erroQtdAtacado.set("");
        validade.set("");
        erroValidade.set("");
    }

    public void excluir(Produto e) {
        dao.excluir(e);
        if (id.get() == e.getId()) {
            limparCampos();
        }
        pesquisarTodos();
    }

    public void atualizar(Produto e) {
        id.set(e.getId());
        popularProduto(e);
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

    public StringProperty marcaProperty() {
        return marca;
    }

    public StringProperty erroMarcaProperty() {
        return erroMarca;
    }

    public StringProperty valorUnitarioProperty() {
        return valorUnitario;
    }

    public StringProperty erroValorUnitarioProperty() {
        return erroValorUnitario;
    }

    public StringProperty valorAtacadoProperty() {
        return valorAtacado;
    }

    public StringProperty erroValorAtacadoProperty() {
        return erroValorAtacado;
    }

    public StringProperty qtdAtacadoProperty() {
        return qtdAtacado;
    }

    public StringProperty erroQtdAtacadoProperty() {
        return erroQtdAtacado;
    }

    public StringProperty validadeProperty() {
        return validade;
    }

    public StringProperty erroValidadeProperty() {
        return erroValidade;
    }

    public ObservableList<Produto> getLista() {
        return lista;
    }
}
