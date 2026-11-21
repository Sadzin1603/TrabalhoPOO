package edu.curso.fronteira;

import edu.curso.controle.CompraControl;
import edu.curso.dao.PersistenciaException;
import edu.curso.entidade.Compra;
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

public class CompraView {

    private CompraControl control = new CompraControl();
    private TableView<Compra> table = new TableView<>();

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

        TextField txtDataCompra = new TextField();
        txtDataCompra.setPromptText("dd/MM/aaaa");
        Label lblErroDataCompra = new Label();
        lblErroDataCompra.setTextFill(Color.RED);
        lblErroDataCompra.textProperty().bind(control.erroDataCompraProperty());
        p.add(new Label("Data da Compra: "), 0, 1);
        p.add(txtDataCompra, 1, 1);
        p.add(lblErroDataCompra, 2, 1);
        Bindings.bindBidirectional(control.dataCompraProperty(), txtDataCompra.textProperty());

        TextField txtPrevisaoEntrega = new TextField();
        txtPrevisaoEntrega.setPromptText("dd/MM/aaaa");
        Label lblErroPrevisaoEntrega = new Label();
        lblErroPrevisaoEntrega.setTextFill(Color.RED);
        lblErroPrevisaoEntrega.textProperty().bind(control.erroPrevisaoEntregaProperty());
        p.add(new Label("Previsão de Entrega: "), 0, 2);
        p.add(txtPrevisaoEntrega, 1, 2);
        p.add(lblErroPrevisaoEntrega, 2, 2);
        Bindings.bindBidirectional(control.previsaoEntregaProperty(), txtPrevisaoEntrega.textProperty());

        TextField txtVolumeTotal = new TextField();
        Label lblErroVolumeTotal = new Label();
        lblErroVolumeTotal.setTextFill(Color.RED);
        lblErroVolumeTotal.textProperty().bind(control.erroVolumeTotalProperty());
        p.add(new Label("Volume Total: "), 0, 3);
        p.add(txtVolumeTotal, 1, 3);
        p.add(lblErroVolumeTotal, 2, 3);
        Bindings.bindBidirectional(control.volumeTotalProperty(), txtVolumeTotal.textProperty());

        TextField txtValorFrete = new TextField();
        Label lblErroValorFrete = new Label();
        lblErroValorFrete.setTextFill(Color.RED);
        lblErroValorFrete.textProperty().bind(control.erroValorFreteProperty());
        p.add(new Label("Valor do Frete: "), 0, 4);
        p.add(txtValorFrete, 1, 4);
        p.add(lblErroValorFrete, 2, 4);
        Bindings.bindBidirectional(control.valorFreteProperty(), txtValorFrete.textProperty());

        TextField txtValorTotal = new TextField();
        Label lblErroValorTotal = new Label();
        lblErroValorTotal.setTextFill(Color.RED);
        lblErroValorTotal.textProperty().bind(control.erroValorTotalProperty());
        p.add(new Label("Valor Total: "), 0, 5);
        p.add(txtValorTotal, 1, 5);
        p.add(lblErroValorTotal, 2, 5);
        Bindings.bindBidirectional(control.valorTotalProperty(), txtValorTotal.textProperty());

        TextField txtIdFornecedor = new TextField();
        Label lblErroIdFornecedor = new Label();
        lblErroIdFornecedor.setTextFill(Color.RED);
        lblErroIdFornecedor.textProperty().bind(control.erroIdFornecedorProperty());
        p.add(new Label("Id do Fornecedor: "), 0, 6);
        p.add(txtIdFornecedor, 1, 6);
        p.add(lblErroIdFornecedor, 2, 6);
        Bindings.bindBidirectional(control.idFornecedorProperty(), txtIdFornecedor.textProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        HBox painelAcoes = new HBox(10, btnSalvar, btnLimpar, btnPesquisar);
        p.add(painelAcoes, 1, 7);

        TableColumn<Compra, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getStatus()))
        );

        TableColumn<Compra, String> colDataCompra = new TableColumn<>("Data da Compra");
        colDataCompra.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getDataCompra()))
        );

        TableColumn<Compra, String> colPrevisaoEntrega = new TableColumn<>("Previsão de Entrega");
        colPrevisaoEntrega.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getPrevisaoEntrega()))
        );

        TableColumn<Compra, String> colVolumeTotal = new TableColumn<>("Volume Total");
        colVolumeTotal.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getVolumeTotal()))
        );

        TableColumn<Compra, String> colValorFrete = new TableColumn<>("Valor do Frete");
        colValorFrete.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getValorFrete()))
        );

        TableColumn<Compra, String> colValorTotal = new TableColumn<>("Valor Total");
        colValorTotal.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getValorTotal()))
        );

        TableColumn<Compra, String> colIdFornecedor = new TableColumn<>("Id do Fornecedor");
        colIdFornecedor.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getIdFornecedor()))
        );

        TableColumn<Compra, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Compra, Void>, TableCell<Compra, Void>> callBack =
            new Callback<>() {
                public TableCell<Compra, Void> call(TableColumn<Compra, Void> tc) {
                    TableCell<Compra, Void> tableCell = new TableCell<>() {

                        private final Button btnExcluir = new Button("Excluir");
                        private final Button btnAtualizar = new Button("Atualizar");
                        private final HBox painelBotoes = new HBox(5);

                        {
                            painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);
                            btnExcluir.setOnAction(ev -> {
                                Compra item = getTableRow().getItem();
                                if (item != null) {
                                    try {
                                        control.excluir(item);
                                    } catch (PersistenciaException ex) {
                                        mostrarErro(ex);
                                    }
                                }
                            });
                            btnAtualizar.setOnAction(ev -> {
                                Compra item = getTableRow().getItem();
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

        table.getColumns().addAll(colStatus, colDataCompra, colPrevisaoEntrega, colVolumeTotal, colValorFrete, colValorTotal, colIdFornecedor, colAcoes);
        table.setItems(control.getLista());

        table.getSelectionModel()
            .selectedItemProperty()
            .addListener((obs, antigo, novo) -> control.popularCompra(novo));

        btnSalvar.setOnAction(e -> {
            try {
                if (control.salvar()) {
                    new Alert(AlertType.INFORMATION,
                        "Compra salvo com sucesso", ButtonType.OK).showAndWait();
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
