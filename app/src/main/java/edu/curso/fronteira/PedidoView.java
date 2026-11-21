package edu.curso.fronteira;

import edu.curso.controle.PedidoControl;
import edu.curso.entidade.Caixa;
import edu.curso.entidade.Cliente;
import edu.curso.entidade.Pedido;
import edu.curso.persistencia.PersistenciaException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PedidoView {

    private PedidoControl control = new PedidoControl();
    private TableView<Pedido> table = new TableView<>();

    private void mostrarErro(PersistenciaException ex) {
        new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
    }

    public BorderPane montar() {
        BorderPane panPrincipal = new BorderPane();
        GridPane p = new GridPane();
        p.setHgap(10);
        p.setVgap(6);
        p.setPadding(new Insets(10));

        TextField txtNumeroPedido = new TextField();
        txtNumeroPedido.setPromptText("Somente para pesquisa");
        Label lblErroNumeroPedido = new Label();
        lblErroNumeroPedido.setTextFill(Color.RED);
        lblErroNumeroPedido.textProperty().bind(control.erroNumeroPedidoProperty());
        p.add(new Label("Nº do Pedido: "), 0, 0);
        p.add(txtNumeroPedido, 1, 0);
        p.add(lblErroNumeroPedido, 2, 0);
        Bindings.bindBidirectional(control.numeroPedidoProperty(), txtNumeroPedido.textProperty());

        ComboBox<String> cmbStatus = new ComboBox<>();
        cmbStatus.getItems().addAll("Em andamento", "Cancelado", "Concluído");
        Label lblErroStatus = new Label();
        lblErroStatus.setTextFill(Color.RED);
        lblErroStatus.textProperty().bind(control.erroStatusProperty());
        p.add(new Label("Status: "), 0, 1);
        p.add(cmbStatus, 1, 1);
        p.add(lblErroStatus, 2, 1);
        cmbStatus.valueProperty().bindBidirectional(control.statusProperty());

        ComboBox<String> cmbFormaPagamento = new ComboBox<>();
        cmbFormaPagamento.getItems().addAll("Dinheiro", "Cartão de crédito", "Cartão de débito", "Pix");
        Label lblErroFormaPagamento = new Label();
        lblErroFormaPagamento.setTextFill(Color.RED);
        lblErroFormaPagamento.textProperty().bind(control.erroFormaPagamentoProperty());
        p.add(new Label("Forma de Pagamento: "), 0, 2);
        p.add(cmbFormaPagamento, 1, 2);
        p.add(lblErroFormaPagamento, 2, 2);
        cmbFormaPagamento.valueProperty().bindBidirectional(control.formaPagamentoProperty());

        TextField txtDataPedido = new TextField();
        txtDataPedido.setPromptText("dd/MM/aaaa");
        Label lblErroDataPedido = new Label();
        lblErroDataPedido.setTextFill(Color.RED);
        lblErroDataPedido.textProperty().bind(control.erroDataPedidoProperty());
        p.add(new Label("Data do Pedido: "), 0, 3);
        p.add(txtDataPedido, 1, 3);
        p.add(lblErroDataPedido, 2, 3);
        Bindings.bindBidirectional(control.dataPedidoProperty(), txtDataPedido.textProperty());

        TextField txtValorTotal = new TextField();
        Label lblErroValorTotal = new Label();
        lblErroValorTotal.setTextFill(Color.RED);
        lblErroValorTotal.textProperty().bind(control.erroValorTotalProperty());
        p.add(new Label("Valor Total: "), 0, 4);
        p.add(txtValorTotal, 1, 4);
        p.add(lblErroValorTotal, 2, 4);
        Bindings.bindBidirectional(control.valorTotalProperty(), txtValorTotal.textProperty());

        ComboBox<Cliente> cmbCliente = new ComboBox<>(control.getListaClientes());
        cmbCliente.setPromptText("Selecione...");
        cmbCliente.setOnShowing(e -> control.recarregarListaCliente());
        cmbCliente.setConverter(new StringConverter<>() {
            @Override public String toString(Cliente c) { return c == null ? "" : c.getNome(); }
            @Override public Cliente fromString(String s) { return null; }
        });
        Label lblErroCliente = new Label();
        lblErroCliente.setTextFill(Color.RED);
        lblErroCliente.textProperty().bind(control.erroClienteProperty());
        p.add(new Label("Cliente: "), 0, 5);
        p.add(cmbCliente, 1, 5);
        p.add(lblErroCliente, 2, 5);
        cmbCliente.valueProperty().bindBidirectional(control.clienteProperty());

        ComboBox<Caixa> cmbCaixa = new ComboBox<>(control.getListaCaixas());
        cmbCaixa.setPromptText("Selecione...");
        cmbCaixa.setOnShowing(e -> control.recarregarListaCaixa());
        cmbCaixa.setConverter(new StringConverter<>() {
            @Override public String toString(Caixa c) { return c == null ? "" : c.getNome(); }
            @Override public Caixa fromString(String s) { return null; }
        });
        Label lblErroCaixa = new Label();
        lblErroCaixa.setTextFill(Color.RED);
        lblErroCaixa.textProperty().bind(control.erroCaixaProperty());
        p.add(new Label("Caixa: "), 0, 6);
        p.add(cmbCaixa, 1, 6);
        p.add(lblErroCaixa, 2, 6);
        cmbCaixa.valueProperty().bindBidirectional(control.caixaProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        HBox painelAcoes = new HBox(10, btnSalvar, btnLimpar, btnPesquisar);
        p.add(painelAcoes, 1, 7);

        TableColumn<Pedido, String> colId = new TableColumn<>("Nº");
        colId.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getId()))
        );

        TableColumn<Pedido, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getStatus()))
        );

        TableColumn<Pedido, String> colFormaPagamento = new TableColumn<>("Forma de Pagamento");
        colFormaPagamento.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getFormaPagamento()))
        );

        TableColumn<Pedido, String> colDataPedido = new TableColumn<>("Data do Pedido");
        colDataPedido.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getDataPedido()))
        );

        TableColumn<Pedido, String> colValorTotal = new TableColumn<>("Valor Total");
        colValorTotal.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getValorTotal()))
        );

        TableColumn<Pedido, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(item.getValue().getCliente() == null ? "" : item.getValue().getCliente().getNome())
        );

        TableColumn<Pedido, String> colCaixa = new TableColumn<>("Caixa");
        colCaixa.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(item.getValue().getCaixa() == null ? "" : item.getValue().getCaixa().getNome())
        );

        TableColumn<Pedido, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Pedido, Void>, TableCell<Pedido, Void>> callBack =
            new Callback<>() {
                public TableCell<Pedido, Void> call(TableColumn<Pedido, Void> tc) {
                    TableCell<Pedido, Void> tableCell = new TableCell<>() {

                        private final Button btnExcluir = new Button("Excluir");
                        private final Button btnAtualizar = new Button("Atualizar");
                        private final HBox painelBotoes = new HBox(5);

                        {
                            painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);
                            btnExcluir.setOnAction(ev -> {
                                Pedido item = getTableRow().getItem();
                                if (item != null) {
                                    try {
                                        control.excluir(item);
                                    } catch (PersistenciaException ex) {
                                        mostrarErro(ex);
                                    }
                                }
                            });
                            btnAtualizar.setOnAction(ev -> {
                                Pedido item = getTableRow().getItem();
                                if (item != null) {
                                    control.atualizar(item);
                                }
                            });
                        }

                        @Override
                        public void updateItem(Void valor, boolean empty) {
                            super.updateItem(valor, empty);
                            if (!empty) {
                                setGraphic(painelBotoes);
                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                    return tableCell;
                }
            };
        colAcoes.setCellFactory(callBack);

        table.getColumns().addAll(colId, colStatus, colFormaPagamento, colDataPedido, colValorTotal, colCliente, colCaixa, colAcoes);
        table.setItems(control.getLista());

        table.getSelectionModel()
            .selectedItemProperty()
            .addListener((obs, antigo, novo) -> control.popularPedido(novo));

        btnSalvar.setOnAction(e -> {
            try {
                if (control.salvar()) {
                    new Alert(AlertType.INFORMATION,
                        "Pedido salvo com sucesso", ButtonType.OK).showAndWait();
                } else {
                    new Alert(AlertType.WARNING,
                        "Corrija os campos indicados em vermelho", ButtonType.OK).showAndWait();
                }
            } catch (PersistenciaException ex) {
                mostrarErro(ex);
            }
        });
        btnLimpar.setOnAction(e -> control.limparCampos());
        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisar();
            } catch (PersistenciaException ex) {
                mostrarErro(ex);
            }
        });

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);
        return panPrincipal;
    }
}