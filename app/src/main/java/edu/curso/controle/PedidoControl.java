package edu.curso.controle;

import edu.curso.entidade.Caixa;
import edu.curso.entidade.Cliente;
import edu.curso.entidade.Pedido;
import edu.curso.persistencia.CaixaDAO;
import edu.curso.persistencia.CaixaDAOImpl;
import edu.curso.persistencia.ClienteDAO;
import edu.curso.persistencia.ClienteDAOImpl;
import edu.curso.persistencia.PedidoDAO;
import edu.curso.persistencia.PedidoDAOImpl;
import edu.curso.persistencia.PersistenciaException;
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
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private ObservableList<Caixa> listaCaixas = FXCollections.observableArrayList();
    private ClienteDAO clienteDAO = new ClienteDAOImpl();
    private CaixaDAO caixaDAO = new CaixaDAOImpl();
    private PedidoDAO dao = new PedidoDAOImpl();

    public PedidoControl() {
        try {
            pesquisarTodos();
            listaClientes.addAll(clienteDAO.pesquisarTodos());
            listaCaixas.addAll(caixaDAO.pesquisarTodos());
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
        }
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
        return erroStatus.get().isEmpty() && erroFormaPagamento.get().isEmpty()
            && erroDataPedido.get().isEmpty() && erroValorTotal.get().isEmpty()
            && erroCliente.get().isEmpty() && erroCaixa.get().isEmpty();
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

    public ObservableList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public ObservableList<Caixa> getListaCaixas() {
        return listaCaixas;
    }

    public ObservableList<Pedido> getLista() {
        return lista;
    }
}