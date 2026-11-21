package edu.curso.fronteira;

import edu.curso.controle.FornecedorControl;
import edu.curso.entidade.Fornecedor;
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

public class FornecedorView {

    private FornecedorControl control = new FornecedorControl();
    private TableView<Fornecedor> table = new TableView<>();

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

        TextField txtRazaoSocial = new TextField();
        Label lblErroRazaoSocial = new Label();
        lblErroRazaoSocial.setTextFill(Color.RED);
        lblErroRazaoSocial.textProperty().bind(control.erroRazaoSocialProperty());
        p.add(new Label("Razão Social: "), 0, 1);
        p.add(txtRazaoSocial, 1, 1);
        p.add(lblErroRazaoSocial, 2, 1);
        Bindings.bindBidirectional(control.razaoSocialProperty(), txtRazaoSocial.textProperty());

        TextField txtCnpj = new TextField();
        Label lblErroCnpj = new Label();
        lblErroCnpj.setTextFill(Color.RED);
        lblErroCnpj.textProperty().bind(control.erroCnpjProperty());
        p.add(new Label("CNPJ: "), 0, 2);
        p.add(txtCnpj, 1, 2);
        p.add(lblErroCnpj, 2, 2);
        Bindings.bindBidirectional(control.cnpjProperty(), txtCnpj.textProperty());

        TextField txtTelefone = new TextField();
        Label lblErroTelefone = new Label();
        lblErroTelefone.setTextFill(Color.RED);
        lblErroTelefone.textProperty().bind(control.erroTelefoneProperty());
        p.add(new Label("Telefone: "), 0, 3);
        p.add(txtTelefone, 1, 3);
        p.add(lblErroTelefone, 2, 3);
        Bindings.bindBidirectional(control.telefoneProperty(), txtTelefone.textProperty());

        TextField txtEmail = new TextField();
        Label lblErroEmail = new Label();
        lblErroEmail.setTextFill(Color.RED);
        lblErroEmail.textProperty().bind(control.erroEmailProperty());
        p.add(new Label("Email: "), 0, 4);
        p.add(txtEmail, 1, 4);
        p.add(lblErroEmail, 2, 4);
        Bindings.bindBidirectional(control.emailProperty(), txtEmail.textProperty());

        TextField txtEndereco = new TextField();
        Label lblErroEndereco = new Label();
        lblErroEndereco.setTextFill(Color.RED);
        lblErroEndereco.textProperty().bind(control.erroEnderecoProperty());
        p.add(new Label("Endereço: "), 0, 5);
        p.add(txtEndereco, 1, 5);
        p.add(lblErroEndereco, 2, 5);
        Bindings.bindBidirectional(control.enderecoProperty(), txtEndereco.textProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        HBox painelAcoes = new HBox(10, btnSalvar, btnLimpar, btnPesquisar);
        p.add(painelAcoes, 1, 6);

        TableColumn<Fornecedor, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getNome()))
        );

        TableColumn<Fornecedor, String> colRazaoSocial = new TableColumn<>("Razão Social");
        colRazaoSocial.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getRazaoSocial()))
        );

        TableColumn<Fornecedor, String> colCnpj = new TableColumn<>("CNPJ");
        colCnpj.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getCnpj()))
        );

        TableColumn<Fornecedor, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getTelefone()))
        );

        TableColumn<Fornecedor, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getEmail()))
        );

        TableColumn<Fornecedor, String> colEndereco = new TableColumn<>("Endereço");
        colEndereco.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getEndereco()))
        );

        TableColumn<Fornecedor, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Fornecedor, Void>, TableCell<Fornecedor, Void>> callBack =
            new Callback<>() {
                public TableCell<Fornecedor, Void> call(TableColumn<Fornecedor, Void> tc) {
                    TableCell<Fornecedor, Void> tableCell = new TableCell<>() {

                        private final Button btnExcluir = new Button("Excluir");
                        private final Button btnAtualizar = new Button("Atualizar");
                        private final HBox painelBotoes = new HBox(5);

                        {
                            painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);
                            btnExcluir.setOnAction(ev -> {
                                Fornecedor item = getTableRow().getItem();
                                if (item != null) {
                                    try {
                                        control.excluir(item);
                                    } catch (PersistenciaException ex) {
                                        mostrarErro(ex);
                                    }
                                }
                            });
                            btnAtualizar.setOnAction(ev -> {
                                Fornecedor item = getTableRow().getItem();
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

        table.getColumns().addAll(colNome, colRazaoSocial, colCnpj, colTelefone, colEmail, colEndereco, colAcoes);
        table.setItems(control.getLista());

        table.getSelectionModel()
            .selectedItemProperty()
            .addListener((obs, antigo, novo) -> control.popularFornecedor(novo));

        btnSalvar.setOnAction(e -> {
            try {
                if (control.salvar()) {
                    new Alert(AlertType.INFORMATION,
                        "Fornecedor salvo com sucesso", ButtonType.OK).showAndWait();
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
