package edu.curso.controle;

import java.util.ArrayList;
import edu.curso.dao.CompraDAO;
import edu.curso.dao.CompraDAOImpl;
import edu.curso.dao.FornecedorDAO;
import edu.curso.dao.FornecedorDAOImpl;
import edu.curso.dao.ProdutoDAO;
import edu.curso.dao.ProdutoDAOImpl;
import edu.curso.entidade.Compra;
import edu.curso.entidade.Fornecedor;
import edu.curso.entidade.ItemCompra;
import edu.curso.entidade.Produto;
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
    private StringProperty numeroCompra = new SimpleStringProperty("");
    private StringProperty erroNumeroCompra = new SimpleStringProperty("");
    private StringProperty status = new SimpleStringProperty("");
    private StringProperty erroStatus = new SimpleStringProperty("");
    private StringProperty dataCompra = new SimpleStringProperty("");
    private StringProperty erroDataCompra = new SimpleStringProperty("");
    private StringProperty previsaoEntrega = new SimpleStringProperty("");
    private StringProperty erroPrevisaoEntrega = new SimpleStringProperty("");
    private StringProperty volumeTotal = new SimpleStringProperty("");
    private StringProperty valorFrete = new SimpleStringProperty("");
    private StringProperty erroValorFrete = new SimpleStringProperty("");
    private StringProperty valorTotal = new SimpleStringProperty("");
    private ObjectProperty<Fornecedor> fornecedor = new SimpleObjectProperty<>();
    private StringProperty erroFornecedor = new SimpleStringProperty("");
    private ObjectProperty<Produto> produto = new SimpleObjectProperty<>();
    private StringProperty erroProduto = new SimpleStringProperty("");
    private StringProperty quantidade = new SimpleStringProperty("");
    private StringProperty erroQuantidade = new SimpleStringProperty("");
    private StringProperty valorUnitario = new SimpleStringProperty("");
    private StringProperty erroValorUnitario = new SimpleStringProperty("");
    private StringProperty erroItens = new SimpleStringProperty("");
    private ObservableList<ItemCompra> itens = FXCollections.observableArrayList();
    private ObservableList<Fornecedor> listaFornecedores = FXCollections.observableArrayList();
    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    private FornecedorDAO fornecedorDAO = new FornecedorDAOImpl();
    private ProdutoDAO produtoDAO = new ProdutoDAOImpl();
    private CompraDAO dao = new CompraDAOImpl();

    public CompraControl() {
        valorFrete.addListener((obs, antigo, novo) -> recalcularTotais());
        pesquisarTodos();
        listaFornecedores.addAll(fornecedorDAO.pesquisarTodos());
        listaProdutos.addAll(produtoDAO.pesquisarTodos());
    }

    private ItemCompra copiar(ItemCompra origem) {
        ItemCompra c = new ItemCompra();
        c.setId(origem.getId());
        c.setProduto(origem.getProduto());
        c.setQuantidade(origem.getQuantidade());
        c.setValorUnitario(origem.getValorUnitario());
        return c;
    }

    public void popularCompra(Compra e) {
        if (e != null) {
            numeroCompra.set(String.valueOf(e.getId()));
            status.set(e.getStatus());
            dataCompra.set(Validador.formatarData(e.getDataCompra()));
            previsaoEntrega.set(Validador.formatarData(e.getPrevisaoEntrega()));
            fornecedor.set(e.getFornecedor());
            itens.clear();
            for (ItemCompra i : e.getItens()) {
                itens.add(copiar(i));
            }
            valorFrete.set(String.valueOf(e.getValorFrete()));
            recalcularTotais();
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
        erroValorFrete.set(Validador.decimal(valorFrete.get()));
        if (erroDataCompra.get().isEmpty() && erroPrevisaoEntrega.get().isEmpty()
            && Validador.parseData(previsaoEntrega.get()).isBefore(Validador.parseData(dataCompra.get()))) {
            erroPrevisaoEntrega.set("A previsão não pode ser antes da data da compra");
        }
        erroFornecedor.set(fornecedor.get() == null ? "Selecione um fornecedor" : "");
        erroItens.set(itens.isEmpty() ? "Adicione ao menos um produto" : "");
        return Validador.semErros(erroStatus, erroDataCompra, erroPrevisaoEntrega, erroValorFrete, erroFornecedor, erroItens);
    }

    public boolean salvar() {
        if (!validar()) {
            return false;
        }
        double frete = Validador.parseDecimal(valorFrete.get());
        Compra e = new Compra();
        e.setStatus(status.get().trim());
        e.setDataCompra(Validador.parseData(dataCompra.get()));
        e.setPrevisaoEntrega(Validador.parseData(previsaoEntrega.get()));
        e.setValorFrete(frete);
        e.setVolumeTotal(somaQuantidades());
        e.setValorTotal(Math.round((somaSubtotais() + frete) * 100) / 100.0);
        e.setFornecedor(fornecedor.get());
        e.setItens(new ArrayList<>(itens));
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
        fornecedor.set(null);
        erroFornecedor.set("");
        produto.set(null);
        erroProduto.set("");
        quantidade.set("");
        erroQuantidade.set("");
        valorUnitario.set("");
        erroValorUnitario.set("");
        itens.clear();
        erroItens.set("");
        valorFrete.set("");
        erroValorFrete.set("");
        volumeTotal.set("");
        valorTotal.set("");
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

    public void adicionarItem() {
        erroProduto.set(produto.get() == null ? "Selecione um produto" : "");
        erroQuantidade.set(Validador.inteiroPositivo(quantidade.get()));
        erroValorUnitario.set(Validador.decimal(valorUnitario.get()));
        if (erroValorUnitario.get().isEmpty() && Validador.parseDecimal(valorUnitario.get()) < 0) {
            erroValorUnitario.set("O valor não pode ser negativo");
        }
        if (!erroProduto.get().isEmpty() || !erroQuantidade.get().isEmpty() || !erroValorUnitario.get().isEmpty()) {
            return;
        }
        int qtd = Integer.parseInt(quantidade.get().trim());
        double valor = Validador.parseDecimal(valorUnitario.get());
        Produto escolhido = produto.get();

        ItemCompra existente = null;
        for (ItemCompra i : itens) {
            if (i.getProduto().getId() == escolhido.getId()) {
                existente = i;
            }
        }
        if (existente != null) {
            existente.setQuantidade(existente.getQuantidade() + qtd);
            existente.setValorUnitario(valor);
            itens.set(itens.indexOf(existente), existente);
        } else {
            ItemCompra novo = new ItemCompra();
            novo.setProduto(escolhido);
            novo.setQuantidade(qtd);
            novo.setValorUnitario(valor);
            itens.add(novo);
        }
        erroItens.set("");
        produto.set(null);
        quantidade.set("");
        valorUnitario.set("");
        erroProduto.set("");
        erroQuantidade.set("");
        erroValorUnitario.set("");
        recalcularTotais();
    }

    public void removerItem(ItemCompra item) {
        itens.remove(item);
        recalcularTotais();
    }

    private int somaQuantidades() {
        int total = 0;
        for (ItemCompra i : itens) {
            total += i.getQuantidade();
        }
        return total;
    }

    private double somaSubtotais() {
        double total = 0;
        for (ItemCompra i : itens) {
            total += i.getSubtotal();
        }
        return total;
    }

    private void recalcularTotais() {
        double frete = 0;
        try {
            frete = Double.parseDouble(valorFrete.get().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            frete = 0;
        }
        volumeTotal.set(String.valueOf(somaQuantidades()));
        valorTotal.set(String.valueOf(Math.round((somaSubtotais() + frete) * 100) / 100.0));
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

    public StringProperty numeroCompraProperty() {
        return numeroCompra;
    }

    public StringProperty erroNumeroCompraProperty() {
        return erroNumeroCompra;
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

    public StringProperty valorFreteProperty() {
        return valorFrete;
    }

    public StringProperty erroValorFreteProperty() {
        return erroValorFrete;
    }

    public StringProperty valorTotalProperty() {
        return valorTotal;
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

    public StringProperty quantidadeProperty() {
        return quantidade;
    }

    public StringProperty erroQuantidadeProperty() {
        return erroQuantidade;
    }

    public StringProperty valorUnitarioProperty() {
        return valorUnitario;
    }

    public StringProperty erroValorUnitarioProperty() {
        return erroValorUnitario;
    }

    public StringProperty erroItensProperty() {
        return erroItens;
    }

    public ObservableList<ItemCompra> getItens() {
        return itens;
    }

    public ObservableList<Fornecedor> getListaFornecedores() {
        return listaFornecedores;
    }

    public ObservableList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public ObservableList<Compra> getLista() {
        return lista;
    }
}