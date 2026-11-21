package edu.curso.controle;

import edu.curso.entidade.Pedido;
import edu.curso.persistencia.PedidoDAO;
import edu.curso.persistencia.PedidoDAOImpl;
import edu.curso.persistencia.PersistenciaException;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PedidoControl {
    private ObservableList<Pedido> lista = FXCollections.observableArrayList();
    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty status = new SimpleStringProperty("");
    private StringProperty erroStatus = new SimpleStringProperty("");
    private StringProperty formaPagamento = new SimpleStringProperty("");
    private StringProperty erroFormaPagamento = new SimpleStringProperty("");
    private StringProperty dataPedido = new SimpleStringProperty("");
    private StringProperty erroDataPedido = new SimpleStringProperty("");
    private StringProperty valorTotal = new SimpleStringProperty("");
    private StringProperty erroValorTotal = new SimpleStringProperty("");
    private StringProperty idCliente = new SimpleStringProperty("");
    private StringProperty erroIdCliente = new SimpleStringProperty("");
    private StringProperty idCaixa = new SimpleStringProperty("");
    private StringProperty erroIdCaixa = new SimpleStringProperty("");
    private PedidoDAO dao = new PedidoDAOImpl();

    public PedidoControl() {
        try {
            pesquisarTodos();
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void popularPedido(Pedido e) {
        if (e != null) {
            status.set(e.getStatus());
            formaPagamento.set(e.getFormaPagamento());
            dataPedido.set(Validador.formatarData(e.getDataPedido()));
            valorTotal.set(String.valueOf(e.getValorTotal()));
            idCliente.set(String.valueOf(e.getIdCliente()));
            idCaixa.set(String.valueOf(e.getIdCaixa()));
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.pesquisarTodos());
    }

    // Valida cada campo individualmente: cada um recebe a sua mensagem de erro
    public boolean validar() {
        erroStatus.set(Validador.texto(status.get()));
        erroFormaPagamento.set(Validador.texto(formaPagamento.get()));
        erroDataPedido.set(Validador.data(dataPedido.get()));
        erroValorTotal.set(Validador.decimal(valorTotal.get()));
        erroIdCliente.set(Validador.fk(idCliente.get()));
        erroIdCaixa.set(Validador.fk(idCaixa.get()));
        return erroStatus.get().isEmpty() && erroFormaPagamento.get().isEmpty() && erroDataPedido.get().isEmpty() && erroValorTotal.get().isEmpty() && erroIdCliente.get().isEmpty() && erroIdCaixa.get().isEmpty();
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
        e.setIdCliente(Long.parseLong(idCliente.get().trim()));
        e.setIdCaixa(Long.parseLong(idCaixa.get().trim()));
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
        status.set("");
        erroStatus.set("");
        formaPagamento.set("");
        erroFormaPagamento.set("");
        dataPedido.set("");
        erroDataPedido.set("");
        valorTotal.set("");
        erroValorTotal.set("");
        idCliente.set("");
        erroIdCliente.set("");
        idCaixa.set("");
        erroIdCaixa.set("");
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

    // Filtra a tabela pelo campo "status" digitado no formulário
    public void pesquisar() {
        String termo = status.get().trim().toLowerCase();
        pesquisarTodos();
        if (!termo.isEmpty()) {
            lista.removeIf(e -> e == null || !String.valueOf(e.getStatus()).toLowerCase().contains(termo));
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

    public StringProperty idClienteProperty() {
        return idCliente;
    }

    public StringProperty erroIdClienteProperty() {
        return erroIdCliente;
    }

    public StringProperty idCaixaProperty() {
        return idCaixa;
    }

    public StringProperty erroIdCaixaProperty() {
        return erroIdCaixa;
    }

    public ObservableList<Pedido> getLista() {
        return lista;
    }
}
