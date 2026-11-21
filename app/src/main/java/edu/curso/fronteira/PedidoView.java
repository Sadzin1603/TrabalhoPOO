package edu.curso.fronteira;

import edu.curso.controle.PedidoControl;
import edu.curso.entidade.Pedido;
import edu.curso.persistencia.PersistenciaException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

        TextField txtStatus = new TextField();
        Label lblErroStatus = new Label();
        lblErroStatus.setTextFill(Color.RED);
        lblErroStatus.textProperty().bind(control.erroStatusProperty());
        p.add(new Label("Status: "), 0, 0);
        p.add(txtStatus, 1, 0);
        p.add(lblErroStatus, 2, 0);
        Bindings.bindBidirectional(control.statusProperty(), txtStatus.textProperty());

        TextField txtFormaPagamento = new TextField();
        Label lblErroFormaPagamento = new Label();
        lblErroFormaPagamento.setTextFill(Color.RED);
        lblErroFormaPagamento.textProperty().bind(control.erroFormaPagamentoProperty());
        p.add(new Label("Forma de Pagamento: "), 0, 1);
        p.add(txtFormaPagamento, 1, 1);
        p.add(lblErroFormaPagamento, 2, 1);
        Bindings.bindBidirectional(control.formaPagamentoProperty(), txtFormaPagamento.textProperty());

        TextField txtDataPedido = new TextField();
        txtDataPedido.setPromptText("dd/MM/aaaa");
        Label lblErroDataPedido = new Label();
        lblErroDataPedido.setTextFill(Color.RED);
        lblErroDataPedido.textProperty().bind(control.erroDataPedidoProperty());
        p.add(new Label("Data do Pedido: "), 0, 2);
        p.add(txtDataPedido, 1, 2);
        p.add(lblErroDataPedido, 2, 2);
        Bindings.bindBidirectional(control.dataPedidoProperty(), txtDataPedido.textProperty());

        TextField txtValorTotal = new TextField();
        Label lblErroValorTotal = new Label();
        lblErroValorTotal.setTextFill(Color.RED);
        lblErroValorTotal.textProperty().bind(control.erroValorTotalProperty());
        p.add(new Label("Valor Total: "), 0, 3);
        p.add(txtValorTotal, 1, 3);
        p.add(lblErroValorTotal, 2, 3);
        Bindings.bindBidirectional(control.valorTotalProperty(), txtValorTotal.textProperty());

        TextField txtIdCliente = new TextField();
        Label lblErroIdCliente = new Label();
        lblErroIdCliente.setTextFill(Color.RED);
        lblErroIdCliente.textProperty().bind(control.erroIdClienteProperty());
        p.add(new Label("Id do Cliente: "), 0, 4);
        p.add(txtIdCliente, 1, 4);
        p.add(lblErroIdCliente, 2, 4);
        Bindings.bindBidirectional(control.idClienteProperty(), txtIdCliente.textProperty());

        TextField txtIdCaixa = new TextField();
        Label lblErroIdCaixa = new Label();
        lblErroIdCaixa.setTextFill(Color.RED);
        lblErroIdCaixa.textProperty().bind(control.erroIdCaixaProperty());
        p.add(new Label("Id do Caixa: "), 0, 5);
        p.add(txtIdCaixa, 1, 5);
        p.add(lblErroIdCaixa, 2, 5);
        Bindings.bindBidirectional(control.idCaixaProperty(), txtIdCaixa.textProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        HBox painelAcoes = new HBox(10, btnSalvar, btnLimpar, btnPesquisar);
        p.add(painelAcoes, 1, 6);

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

        TableColumn<Pedido, String> colIdCliente = new TableColumn<>("Id do Cliente");
        colIdCliente.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getIdCliente()))
        );

        TableColumn<Pedido, String> colIdCaixa = new TableColumn<>("Id do Caixa");
        colIdCaixa.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getIdCaixa()))
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

        table.getColumns().addAll(colStatus, colFormaPagamento, colDataPedido, colValorTotal, colIdCliente, colIdCaixa, colAcoes);
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
