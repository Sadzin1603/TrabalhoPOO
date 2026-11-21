package edu.curso.controle;

import java.util.ArrayList;
import edu.curso.dao.CaixaDAO;
import edu.curso.dao.CaixaDAOImpl;
import edu.curso.dao.ClienteDAO;
import edu.curso.dao.ClienteDAOImpl;
import edu.curso.dao.PedidoDAO;
import edu.curso.dao.PedidoDAOImpl;
import edu.curso.dao.PersistenciaException;
import edu.curso.dao.ProdutoDAO;
import edu.curso.dao.ProdutoDAOImpl;
import edu.curso.entidade.Caixa;
import edu.curso.entidade.Cliente;
import edu.curso.entidade.ItemPedido;
import edu.curso.entidade.Pedido;
import edu.curso.entidade.Produto;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PedidoControl {
    private ObservableList<Pedido> lista = FXCollections.observableArrayList();
    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty numeroPedido = new SimpleStringProperty("");
    private StringProperty erroNumeroPedido = new SimpleStringProperty("");
    private StringProperty status = new SimpleStringProperty("");
    private StringProperty erroStatus = new SimpleStringProperty("");
    private StringProperty formaPagamento = new SimpleStringProperty("");
    private StringProperty erroFormaPagamento = new SimpleStringProperty("");
    private StringProperty dataPedido = new SimpleStringProperty("");
    private StringProperty erroDataPedido = new SimpleStringProperty("");
    private StringProperty valorTotal = new SimpleStringProperty("");
    private StringProperty erroValorTotal = new SimpleStringProperty("");
    private ObjectProperty<Cliente> cliente = new SimpleObjectProperty<>();
    private StringProperty erroCliente = new SimpleStringProperty("");
    private ObjectProperty<Caixa> caixa = new SimpleObjectProperty<>();
    private StringProperty erroCaixa = new SimpleStringProperty("");
    private ObjectProperty<Produto> produto = new SimpleObjectProperty<>();
    private StringProperty erroProduto = new SimpleStringProperty("");
    private StringProperty quantidade = new SimpleStringProperty("");
    private StringProperty erroQuantidade = new SimpleStringProperty("");
    private StringProperty erroItens = new SimpleStringProperty("");
    private ObservableList<ItemPedido> itens = FXCollections.observableArrayList();
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private ObservableList<Caixa> listaCaixas = FXCollections.observableArrayList();
    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    private ClienteDAO clienteDAO = new ClienteDAOImpl();
    private CaixaDAO caixaDAO = new CaixaDAOImpl();
    private ProdutoDAO produtoDAO = new ProdutoDAOImpl();
    private PedidoDAO dao = new PedidoDAOImpl();

    public PedidoControl() {
        try {
            pesquisarTodos();
            listaClientes.addAll(clienteDAO.pesquisarTodos());
            listaCaixas.addAll(caixaDAO.pesquisarTodos());
            listaProdutos.addAll(produtoDAO.pesquisarTodos());
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private ItemPedido copiar(ItemPedido origem) {
        ItemPedido c = new ItemPedido();
        c.setId(origem.getId());
        c.setProduto(origem.getProduto());
        c.setQuantidade(origem.getQuantidade());
        c.setValorUnitario(origem.getValorUnitario());
        return c;
    }

    public void popularPedido(Pedido e) {
        if (e != null) {
            numeroPedido.set(String.valueOf(e.getId()));
            status.set(e.getStatus());
            formaPagamento.set(e.getFormaPagamento());
            dataPedido.set(Validador.formatarData(e.getDataPedido()));
            valorTotal.set(String.valueOf(e.getValorTotal()));
            cliente.set(e.getCliente());
            caixa.set(e.getCaixa());
            itens.clear();
            for (ItemPedido i : e.getItens()) {
                itens.add(copiar(i));
            }
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.pesquisarTodos());
    }

    public boolean validar() {
        erroStatus.set(Validador.texto(status.get()));
        erroFormaPagamento.set(Validador.texto(formaPagamento.get()));
        erroDataPedido.set(Validador.data(dataPedido.get()));
        erroValorTotal.set(Validador.decimal(valorTotal.get()));
        erroCliente.set(cliente.get() == null ? "Selecione um cliente" : "");
        erroCaixa.set(caixa.get() == null ? "Selecione um caixa" : "");
        erroItens.set(itens.isEmpty() ? "Adicione ao menos um produto" : "");
        return erroStatus.get().isEmpty() && erroFormaPagamento.get().isEmpty()
            && erroDataPedido.get().isEmpty() && erroValorTotal.get().isEmpty()
            && erroCliente.get().isEmpty() && erroCaixa.get().isEmpty()
            && erroItens.get().isEmpty();
    }

    public boolean salvar() {
        if (!validar()) {
            return false;
        }
        Pedido e = new Pedido();
        e.setStatus(status.get().trim());
        e.setFormaPagamento(formaPagamento.get().trim());
        e.setDataPedido(Validador.parseData(dataPedido.get()));
        e.setValorTotal(Validador.parseDecimal(valorTotal.get()));
        e.setCliente(cliente.get());
        e.setCaixa(caixa.get());
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
        numeroPedido.set("");
        erroNumeroPedido.set("");
        status.set("");
        erroStatus.set("");
        formaPagamento.set("");
        erroFormaPagamento.set("");
        dataPedido.set("");
        erroDataPedido.set("");
        valorTotal.set("");
        erroValorTotal.set("");
        cliente.set(null);
        erroCliente.set("");
        caixa.set(null);
        erroCaixa.set("");
        produto.set(null);
        erroProduto.set("");
        quantidade.set("");
        erroQuantidade.set("");
        itens.clear();
        erroItens.set("");
    }

    public void excluir(Pedido e) {
        dao.excluir(e);
        if (id.get() == e.getId()) {
            limparCampos();
        }
        pesquisarTodos();
    }

    public void atualizar(Pedido e) {
        id.set(e.getId());
        popularPedido(e);
    }

    public void pesquisar() {
        String termo = numeroPedido.get().trim();
        erroNumeroPedido.set("");
        pesquisarTodos();
        if (termo.isEmpty()) {
            return;
        }
        try {
            long idBuscado = Long.parseLong(termo);
            lista.removeIf(e -> e == null || e.getId() != idBuscado);
        } catch (NumberFormatException ex) {
            erroNumeroPedido.set("Digite um número de pedido válido");
        }
    }

    public void adicionarItem() {
        erroProduto.set(produto.get() == null ? "Selecione um produto" : "");
        erroQuantidade.set(Validador.inteiro(quantidade.get()));
        if (erroQuantidade.get().isEmpty() && Integer.parseInt(quantidade.get().trim()) <= 0) {
            erroQuantidade.set("A quantidade deve ser maior que zero");
        }
        if (!erroProduto.get().isEmpty() || !erroQuantidade.get().isEmpty()) {
            return;
        }
        int qtd = Integer.parseInt(quantidade.get().trim());
        Produto escolhido = produto.get();

        ItemPedido existente = null;
        for (ItemPedido i : itens) {
            if (i.getProduto().getId() == escolhido.getId()) {
                existente = i;
            }
        }
        if (existente != null) {
            existente.setQuantidade(existente.getQuantidade() + qtd);
            itens.set(itens.indexOf(existente), existente);
        } else {
            ItemPedido novo = new ItemPedido();
            novo.setProduto(escolhido);
            novo.setQuantidade(qtd);
            novo.setValorUnitario(escolhido.getValorUnitario());
            itens.add(novo);
        }
        erroItens.set("");
        produto.set(null);
        quantidade.set("");
        erroProduto.set("");
        erroQuantidade.set("");
        recalcularTotal();
    }

    public void removerItem(ItemPedido item) {
        itens.remove(item);
        recalcularTotal();
    }

    private void recalcularTotal() {
        double total = 0;
        for (ItemPedido i : itens) {
            total += i.getSubtotal();
        }
        valorTotal.set(String.valueOf(Math.round(total * 100) / 100.0));
    }

    public void recarregarListaCliente() {
        Cliente atual = cliente.get();
        listaClientes.setAll(clienteDAO.pesquisarTodos());
        if (atual != null) {
            for (Cliente c : listaClientes) {
                if (c.getId() == atual.getId()) {
                    cliente.set(c);
                    break;
                }
            }
        }
    }

    public void recarregarListaCaixa() {
        Caixa atual = caixa.get();
        listaCaixas.setAll(caixaDAO.pesquisarTodos());
        if (atual != null) {
            for (Caixa c : listaCaixas) {
                if (c.getId() == atual.getId()) {
                    caixa.set(c);
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

    public StringProperty numeroPedidoProperty() {
        return numeroPedido;
    }

    public StringProperty erroNumeroPedidoProperty() {
        return erroNumeroPedido;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty erroStatusProperty() {
        return erroStatus;
    }

    public StringProperty formaPagamentoProperty() {
        return formaPagamento;
    }

    public StringProperty erroFormaPagamentoProperty() {
        return erroFormaPagamento;
    }

    public StringProperty dataPedidoProperty() {
        return dataPedido;
    }

    public StringProperty erroDataPedidoProperty() {
        return erroDataPedido;
    }

    public StringProperty valorTotalProperty() {
        return valorTotal;
    }

    public StringProperty erroValorTotalProperty() {
        return erroValorTotal;
    }

    public ObjectProperty<Cliente> clienteProperty() {
        return cliente;
    }

    public StringProperty erroClienteProperty() {
        return erroCliente;
    }

    public ObjectProperty<Caixa> caixaProperty() {
        return caixa;
    }

    public StringProperty erroCaixaProperty() {
        return erroCaixa;
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

    public StringProperty erroItensProperty() {
        return erroItens;
    }

    public ObservableList<ItemPedido> getItens() {
        return itens;
    }

    public ObservableList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public ObservableList<Caixa> getListaCaixas() {
        return listaCaixas;
    }

    public ObservableList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public ObservableList<Pedido> getLista() {
        return lista;
    }
}