package edu.curso.controle;

import edu.curso.dao.CompraDAO;
import edu.curso.dao.CompraDAOImpl;
import edu.curso.dao.PersistenciaException;
import edu.curso.entidade.Compra;
import edu.curso.entidade.Fornecedor;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CompraControl {
    private ObservableList<Compra> lista = FXCollections.observableArrayList();
    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty status = new SimpleStringProperty("");
    private StringProperty erroStatus = new SimpleStringProperty("");
    private StringProperty dataCompra = new SimpleStringProperty("");
    private StringProperty erroDataCompra = new SimpleStringProperty("");
    private StringProperty previsaoEntrega = new SimpleStringProperty("");
    private StringProperty erroPrevisaoEntrega = new SimpleStringProperty("");
    private StringProperty volumeTotal = new SimpleStringProperty("");
    private StringProperty erroVolumeTotal = new SimpleStringProperty("");
    private StringProperty valorFrete = new SimpleStringProperty("");
    private StringProperty erroValorFrete = new SimpleStringProperty("");
    private StringProperty valorTotal = new SimpleStringProperty("");
    private StringProperty erroValorTotal = new SimpleStringProperty("");
    private ObjectProperty<Fornecedor> fornecedor = new SimpleObjectProperty<>();
    private StringProperty erroFornecedor = new SimpleStringProperty("");
    private ObjectProperty<Produto> produto = new SimpleObjectProperty<>();
    private StringProperty erroProduto = new SimpleStringProperty("");
    private ObservableList<Fornecedor> listaFornecedores = FXCollections.observableArrayList();
    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    private FornecedorDAO fornecedorDAO = new FornecedorDAOImpl();
    private ProdutoDAO produtoDAO = new ProdutoDAOImpl();
    private CompraDAO dao = new CompraDAOImpl();
    private StringProperty numeroCompra = new SimpleStringProperty("");
    private StringProperty erroNumeroCompra = new SimpleStringProperty("");

    public CompraControl() {
        try {
            pesquisarTodos();
            listaFornecedores.addAll(fornecedorDAO.pesquisarTodos());
            listaProdutos.addAll(produtoDAO.pesquisarTodos());
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void popularCompra(Compra e) {
        if (e != null) {
            numeroCompra.set(String.valueOf(e.getId()));
            status.set(e.getStatus());
            dataCompra.set(Validador.formatarData(e.getDataCompra()));
            previsaoEntrega.set(Validador.formatarData(e.getPrevisaoEntrega()));
            volumeTotal.set(String.valueOf(e.getVolumeTotal()));
            valorFrete.set(String.valueOf(e.getValorFrete()));
            valorTotal.set(String.valueOf(e.getValorTotal()));
            fornecedor.set(e.getFornecedor());
            produto.set(e.getProduto());
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.pesquisarTodos());
    }

    public boolean validar() {
        erroStatus.set(Validador.texto(status.get()));
        erroDataCompra.set(Validador.data(dataCompra.get()));
        erroPrevisaoEntrega.set(Validador.data(previsaoEntrega.get()));
        erroVolumeTotal.set(Validador.inteiro(volumeTotal.get()));
        erroValorFrete.set(Validador.decimal(valorFrete.get()));
        erroValorTotal.set(Validador.decimal(valorTotal.get()));
        erroFornecedor.set(fornecedor.get() == null ? "Selecione um fornecedor" : "");
        erroProduto.set(produto.get() == null ? "Selecione um produto" : "");
        return erroStatus.get().isEmpty() && erroDataCompra.get().isEmpty()
            && erroPrevisaoEntrega.get().isEmpty() && erroVolumeTotal.get().isEmpty()
            && erroValorFrete.get().isEmpty() && erroValorTotal.get().isEmpty()
            && erroFornecedor.get().isEmpty() && erroProduto.get().isEmpty();
    }

    public boolean salvar() {
        if (!validar()) {
            return false;
        }
        Compra e = new Compra();
        e.setStatus(status.get().trim());
        e.setDataCompra(Validador.parseData(dataCompra.get()));
        e.setPrevisaoEntrega(Validador.parseData(previsaoEntrega.get()));
        e.setVolumeTotal(Integer.parseInt(volumeTotal.get().trim()));
        e.setValorFrete(Validador.parseDecimal(valorFrete.get()));
        e.setValorTotal(Validador.parseDecimal(valorTotal.get()));
        e.setFornecedor(fornecedor.get());
        e.setProduto(produto.get());
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
        numeroCompra.set("");
    erroNumeroCompra.set("");
        status.set("");
        erroStatus.set("");
        dataCompra.set("");
        erroDataCompra.set("");
        previsaoEntrega.set("");
        erroPrevisaoEntrega.set("");
        volumeTotal.set("");
        erroVolumeTotal.set("");
        valorFrete.set("");
        erroValorFrete.set("");
        valorTotal.set("");
        erroValorTotal.set("");
        fornecedor.set(null);
        erroFornecedor.set("");
        produto.set(null);
        erroProduto.set("");
    }

    public void excluir(Compra e) {
        dao.excluir(e);
        if (id.get() == e.getId()) {
            limparCampos();
        }
        pesquisarTodos();
    }

    public void atualizar(Compra e) {
        id.set(e.getId());
        popularCompra(e);
    }

    public void pesquisar() {
        String termo = numeroCompra.get().trim();
        erroNumeroCompra.set("");
        pesquisarTodos();
        if (termo.isEmpty()) {
            return;
        }
        try {
            long idBuscado = Long.parseLong(termo);
            lista.removeIf(e -> e == null || e.getId() != idBuscado);
        } catch (NumberFormatException ex) {
            erroNumeroCompra.set("Digite um número de compra válido");
        }
    }

    public void recarregarListaFornecedor() {
        Fornecedor atual = fornecedor.get();
        listaFornecedores.setAll(fornecedorDAO.pesquisarTodos());
        if (atual != null) {
            for (Fornecedor f : listaFornecedores) {
                if (f.getId() == atual.getId()) {
                    fornecedor.set(f);
                    break;
                }
            }
        }
    }

    public void recarregarListaProduto() {
        Produto atual = produto.get();
        listaProdutos.setAll(produtoDAO.pesquisarTodos());
        if (atual != null) {
            for (Produto p : listaProdutos) {
                if (p.getId() == atual.getId()) {
                    produto.set(p);
                    break;
                }
            }
        }
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty erroStatusProperty() {
        return erroStatus;
    }

    public StringProperty dataCompraProperty() {
        return dataCompra;
    }

    public StringProperty erroDataCompraProperty() {
        return erroDataCompra;
    }

    public StringProperty previsaoEntregaProperty() {
        return previsaoEntrega;
    }

    public StringProperty erroPrevisaoEntregaProperty() {
        return erroPrevisaoEntrega;
    }

    public StringProperty volumeTotalProperty() {
        return volumeTotal;
    }

    public StringProperty erroVolumeTotalProperty() {
        return erroVolumeTotal;
    }

    public StringProperty valorFreteProperty() {
        return valorFrete;
    }

    public StringProperty erroValorFreteProperty() {
        return erroValorFrete;
    }

    public StringProperty valorTotalProperty() {
        return valorTotal;
    }

    public StringProperty erroValorTotalProperty() {
        return erroValorTotal;
    }

    public ObjectProperty<Fornecedor> fornecedorProperty() {
        return fornecedor;
    }

    public StringProperty erroFornecedorProperty() {
        return erroFornecedor;
    }

    public ObjectProperty<Produto> produtoProperty() {
        return produto;
    }

    public StringProperty erroProdutoProperty() {
        return erroProduto;
    }

    public ObservableList<Fornecedor> getListaFornecedores() {
        return listaFornecedores;
    }

    public ObservableList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public StringProperty numeroCompraProperty() {
        return numeroCompra;
    }

    public StringProperty erroNumeroCompraProperty() {
        return erroNumeroCompra;
    }

    public ObservableList<Compra> getLista() {
        return lista;
    }
}