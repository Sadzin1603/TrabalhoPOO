package edu.curso.fronteira;

import edu.curso.controle.ProdutoControl;
import edu.curso.entidade.Produto;
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

public class ProdutoView {

    private ProdutoControl control = new ProdutoControl();
    private TableView<Produto> table = new TableView<>();

    private void mostrarErro(PersistenciaException ex) {
        new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
    }

    public BorderPane montar() {
        BorderPane panPrincipal = new BorderPane();
        GridPane p = new GridPane();
        p.setHgap(10);
        p.setVgap(6);
        p.setPadding(new Insets(10));

        TextField txtNome = new TextField();
        Label lblErroNome = new Label();
        lblErroNome.setTextFill(Color.RED);
        lblErroNome.textProperty().bind(control.erroNomeProperty());
        p.add(new Label("Nome: "), 0, 0);
        p.add(txtNome, 1, 0);
        p.add(lblErroNome, 2, 0);
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());

        TextField txtMarca = new TextField();
        Label lblErroMarca = new Label();
        lblErroMarca.setTextFill(Color.RED);
        lblErroMarca.textProperty().bind(control.erroMarcaProperty());
        p.add(new Label("Marca: "), 0, 1);
        p.add(txtMarca, 1, 1);
        p.add(lblErroMarca, 2, 1);
        Bindings.bindBidirectional(control.marcaProperty(), txtMarca.textProperty());

        TextField txtValorUnitario = new TextField();
        Label lblErroValorUnitario = new Label();
        lblErroValorUnitario.setTextFill(Color.RED);
        lblErroValorUnitario.textProperty().bind(control.erroValorUnitarioProperty());
        p.add(new Label("Valor Unitário: "), 0, 2);
        p.add(txtValorUnitario, 1, 2);
        p.add(lblErroValorUnitario, 2, 2);
        Bindings.bindBidirectional(control.valorUnitarioProperty(), txtValorUnitario.textProperty());

        TextField txtValorAtacado = new TextField();
        Label lblErroValorAtacado = new Label();
        lblErroValorAtacado.setTextFill(Color.RED);
        lblErroValorAtacado.textProperty().bind(control.erroValorAtacadoProperty());
        p.add(new Label("Valor Atacado: "), 0, 3);
        p.add(txtValorAtacado, 1, 3);
        p.add(lblErroValorAtacado, 2, 3);
        Bindings.bindBidirectional(control.valorAtacadoProperty(), txtValorAtacado.textProperty());

        TextField txtQtdAtacado = new TextField();
        Label lblErroQtdAtacado = new Label();
        lblErroQtdAtacado.setTextFill(Color.RED);
        lblErroQtdAtacado.textProperty().bind(control.erroQtdAtacadoProperty());
        p.add(new Label("Qtd. Atacado: "), 0, 4);
        p.add(txtQtdAtacado, 1, 4);
        p.add(lblErroQtdAtacado, 2, 4);
        Bindings.bindBidirectional(control.qtdAtacadoProperty(), txtQtdAtacado.textProperty());

        TextField txtValidade = new TextField();
        txtValidade.setPromptText("dd/MM/aaaa");
        Label lblErroValidade = new Label();
        lblErroValidade.setTextFill(Color.RED);
        lblErroValidade.textProperty().bind(control.erroValidadeProperty());
        p.add(new Label("Validade: "), 0, 5);
        p.add(txtValidade, 1, 5);
        p.add(lblErroValidade, 2, 5);
        Bindings.bindBidirectional(control.validadeProperty(), txtValidade.textProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        HBox painelAcoes = new HBox(10, btnSalvar, btnLimpar, btnPesquisar);
        p.add(painelAcoes, 1, 6);

        TableColumn<Produto, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getNome()))
        );

        TableColumn<Produto, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getMarca()))
        );

        TableColumn<Produto, String> colValorUnitario = new TableColumn<>("Valor Unitário");
        colValorUnitario.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getValorUnitario()))
        );

        TableColumn<Produto, String> colValorAtacado = new TableColumn<>("Valor Atacado");
        colValorAtacado.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getValorAtacado()))
        );

        TableColumn<Produto, String> colQtdAtacado = new TableColumn<>("Qtd. Atacado");
        colQtdAtacado.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getQtdAtacado()))
        );

        TableColumn<Produto, String> colValidade = new TableColumn<>("Validade");
        colValidade.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getValidade()))
        );

        TableColumn<Produto, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> callBack =
            new Callback<>() {
                public TableCell<Produto, Void> call(TableColumn<Produto, Void> tc) {
                    TableCell<Produto, Void> tableCell = new TableCell<>() {

                        private final Button btnExcluir = new Button("Excluir");
                        private final Button btnAtualizar = new Button("Atualizar");
                        private final HBox painelBotoes = new HBox(5);

                        {
                            painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);
                            btnExcluir.setOnAction(ev -> {
                                Produto item = getTableRow().getItem();
                                if (item != null) {
                                    try {
                                        control.excluir(item);
                                    } catch (PersistenciaException ex) {
                                        mostrarErro(ex);
                                    }
                                }
                            });
                            btnAtualizar.setOnAction(ev -> {
                                Produto item = getTableRow().getItem();
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

        table.getColumns().addAll(colNome, colMarca, colValorUnitario, colValorAtacado, colQtdAtacado, colValidade, colAcoes);
        table.setItems(control.getLista());

        table.getSelectionModel()
            .selectedItemProperty()
            .addListener((obs, antigo, novo) -> control.popularProduto(novo));

        btnSalvar.setOnAction(e -> {
            try {
                if (control.salvar()) {
                    new Alert(AlertType.INFORMATION,
                        "Produto salvo com sucesso", ButtonType.OK).showAndWait();
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
