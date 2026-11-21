package edu.curso.controle;

import edu.curso.dao.CompraDAO;
import edu.curso.dao.CompraDAOImpl;
import edu.curso.dao.PersistenciaException;
import edu.curso.entidade.Compra;
import edu.curso.entidade.Fornecedor;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
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
    private StringProperty idFornecedor = new SimpleStringProperty("");
    private StringProperty erroIdFornecedor = new SimpleStringProperty("");
    private CompraDAO dao = new CompraDAOImpl();

    public CompraControl() {
        try {
            pesquisarTodos();
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void popularCompra(Compra e) {
        if (e != null) {
            status.set(e.getStatus());
            dataCompra.set(Validador.formatarData(e.getDataCompra()));
            previsaoEntrega.set(Validador.formatarData(e.getPrevisaoEntrega()));
            volumeTotal.set(String.valueOf(e.getVolumeTotal()));
            valorFrete.set(String.valueOf(e.getValorFrete()));
            valorTotal.set(String.valueOf(e.getValorTotal()));
            idFornecedor.set(String.valueOf(e.getFornecedor().getId()));
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.pesquisarTodos());
    }

    // Valida cada campo individualmente: cada um recebe a sua mensagem de erro
    public boolean validar() {
        erroStatus.set(Validador.texto(status.get()));
        erroDataCompra.set(Validador.data(dataCompra.get()));
        erroPrevisaoEntrega.set(Validador.data(previsaoEntrega.get()));
        erroVolumeTotal.set(Validador.inteiro(volumeTotal.get()));
        erroValorFrete.set(Validador.decimal(valorFrete.get()));
        erroValorTotal.set(Validador.decimal(valorTotal.get()));
        erroIdFornecedor.set(Validador.fk(idFornecedor.get()));
        return erroStatus.get().isEmpty() && erroDataCompra.get().isEmpty() && erroPrevisaoEntrega.get().isEmpty() && erroVolumeTotal.get().isEmpty() && erroValorFrete.get().isEmpty() && erroValorTotal.get().isEmpty() && erroIdFornecedor.get().isEmpty();
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
        e.setFornecedor(new Fornecedor());
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
        idFornecedor.set("");
        erroIdFornecedor.set("");
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

    public StringProperty idFornecedorProperty() {
        return idFornecedor;
    }

    public StringProperty erroIdFornecedorProperty() {
        return erroIdFornecedor;
    }

    public ObservableList<Compra> getLista() {
        return lista;
    }
}
