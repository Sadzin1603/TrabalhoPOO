package edu.curso.fronteira;

import edu.curso.controle.CaixaControl;
import edu.curso.entidade.Caixa;
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

public class CaixaView {

    private CaixaControl control = new CaixaControl();
    private TableView<Caixa> table = new TableView<>();

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

        TextField txtCpf = new TextField();
        Label lblErroCpf = new Label();
        lblErroCpf.setTextFill(Color.RED);
        lblErroCpf.textProperty().bind(control.erroCpfProperty());
        p.add(new Label("CPF: "), 0, 1);
        p.add(txtCpf, 1, 1);
        p.add(lblErroCpf, 2, 1);
        Bindings.bindBidirectional(control.cpfProperty(), txtCpf.textProperty());

        TextField txtTelefone = new TextField();
        Label lblErroTelefone = new Label();
        lblErroTelefone.setTextFill(Color.RED);
        lblErroTelefone.textProperty().bind(control.erroTelefoneProperty());
        p.add(new Label("Telefone: "), 0, 2);
        p.add(txtTelefone, 1, 2);
        p.add(lblErroTelefone, 2, 2);
        Bindings.bindBidirectional(control.telefoneProperty(), txtTelefone.textProperty());

        TextField txtEmail = new TextField();
        Label lblErroEmail = new Label();
        lblErroEmail.setTextFill(Color.RED);
        lblErroEmail.textProperty().bind(control.erroEmailProperty());
        p.add(new Label("Email: "), 0, 3);
        p.add(txtEmail, 1, 3);
        p.add(lblErroEmail, 2, 3);
        Bindings.bindBidirectional(control.emailProperty(), txtEmail.textProperty());

        TextField txtDataNascimento = new TextField();
        txtDataNascimento.setPromptText("dd/MM/aaaa");
        Label lblErroDataNascimento = new Label();
        lblErroDataNascimento.setTextFill(Color.RED);
        lblErroDataNascimento.textProperty().bind(control.erroDataNascimentoProperty());
        p.add(new Label("Data de Nascimento: "), 0, 4);
        p.add(txtDataNascimento, 1, 4);
        p.add(lblErroDataNascimento, 2, 4);
        Bindings.bindBidirectional(control.dataNascimentoProperty(), txtDataNascimento.textProperty());

        TextField txtSalario = new TextField();
        Label lblErroSalario = new Label();
        lblErroSalario.setTextFill(Color.RED);
        lblErroSalario.textProperty().bind(control.erroSalarioProperty());
        p.add(new Label("Salário: "), 0, 5);
        p.add(txtSalario, 1, 5);
        p.add(lblErroSalario, 2, 5);
        Bindings.bindBidirectional(control.salarioProperty(), txtSalario.textProperty());

        TextField txtTurno = new TextField();
        Label lblErroTurno = new Label();
        lblErroTurno.setTextFill(Color.RED);
        lblErroTurno.textProperty().bind(control.erroTurnoProperty());
        p.add(new Label("Turno: "), 0, 6);
        p.add(txtTurno, 1, 6);
        p.add(lblErroTurno, 2, 6);
        Bindings.bindBidirectional(control.turnoProperty(), txtTurno.textProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        HBox painelAcoes = new HBox(10, btnSalvar, btnLimpar, btnPesquisar);
        p.add(painelAcoes, 1, 7);

        TableColumn<Caixa, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getNome()))
        );

        TableColumn<Caixa, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getCpf()))
        );

        TableColumn<Caixa, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getTelefone()))
        );

        TableColumn<Caixa, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getEmail()))
        );

        TableColumn<Caixa, String> colDataNascimento = new TableColumn<>("Data de Nascimento");
        colDataNascimento.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getDataNascimento()))
        );

        TableColumn<Caixa, String> colSalario = new TableColumn<>("Salário");
        colSalario.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getSalario()))
        );

        TableColumn<Caixa, String> colTurno = new TableColumn<>("Turno");
        colTurno.setCellValueFactory(
            item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getTurno()))
        );

        TableColumn<Caixa, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Caixa, Void>, TableCell<Caixa, Void>> callBack =
            new Callback<>() {
                public TableCell<Caixa, Void> call(TableColumn<Caixa, Void> tc) {
                    TableCell<Caixa, Void> tableCell = new TableCell<>() {

                        private final Button btnExcluir = new Button("Excluir");
                        private final Button btnAtualizar = new Button("Atualizar");
                        private final HBox painelBotoes = new HBox(5);

                        {
                            painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);
                            btnExcluir.setOnAction(ev -> {
                                Caixa item = getTableRow().getItem();
                                if (item != null) {
                                    try {
                                        control.excluir(item);
                                    } catch (PersistenciaException ex) {
                                        mostrarErro(ex);
                                    }
                                }
                            });
                            btnAtualizar.setOnAction(ev -> {
                                Caixa item = getTableRow().getItem();
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

        table.getColumns().addAll(colNome, colCpf, colTelefone, colEmail, colDataNascimento, colSalario, colTurno, colAcoes);
        table.setItems(control.getLista());

        table.getSelectionModel()
            .selectedItemProperty()
            .addListener((obs, antigo, novo) -> control.popularCaixa(novo));

        btnSalvar.setOnAction(e -> {
            try {
                if (control.salvar()) {
                    new Alert(AlertType.INFORMATION,
                        "Caixa salvo com sucesso", ButtonType.OK).showAndWait();
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
