package edu.curso.fronteira;

import java.time.LocalDate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import edu.curso.controle.Validador;
import edu.curso.dao.PersistenciaException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

// Métodos que as telas (Views) usam para não repetir o mesmo código de JavaFX
public class Tela {

    private Tela() {
    }

    public static GridPane criarFormulario() {
        GridPane p = new GridPane();
        p.setHgap(10);
        p.setVgap(6);
        p.setPadding(new Insets(10));
        return p;
    }

    // Uma linha do formulário: rótulo, campo e a mensagem de erro em vermelho
    public static void linha(GridPane p, int linha, String rotulo, Node campo, StringProperty erro) {
        p.add(new Label(rotulo + ": "), 0, linha);
        p.add(campo, 1, linha);
        if (erro != null) {
            Label lblErro = new Label();
            lblErro.setTextFill(Color.RED);
            lblErro.textProperty().bind(erro);
            p.add(lblErro, 2, linha);
        }
    }

    // ComboBox que mostra o nome do objeto e recarrega a lista quando é aberto
    public static <T> ComboBox<T> combo(ObservableList<T> lista, Runnable aoAbrir, Function<T, String> nome) {
        ComboBox<T> cmb = new ComboBox<>(lista);
        cmb.setPromptText("Selecione...");
        cmb.setOnShowing(e -> aoAbrir.run());
        cmb.setConverter(new StringConverter<>() {
            @Override
            public String toString(T item) {
                return item == null ? "" : nome.apply(item);
            }

            @Override
            public T fromString(String s) {
                return null;
            }
        });
        return cmb;
    }

    // Coluna de texto da tabela. Ex.: Tela.coluna("Nome", Cliente::getNome)
    public static <T> TableColumn<T, String> coluna(String titulo, Function<T, Object> valor) {
        TableColumn<T, String> col = new TableColumn<>(titulo);
        col.setCellValueFactory(item -> new ReadOnlyStringWrapper(texto(valor.apply(item.getValue()))));
        return col;
    }

    private static String texto(Object valor) {
        if (valor == null) {
            return "";
        }
        if (valor instanceof LocalDate data) {
            return Validador.formatarData(data);
        }
        return String.valueOf(valor);
    }

    // Coluna de ações com os botões Atualizar e Excluir
    public static <T> TableColumn<T, Void> colunaAcoes(Consumer<T> atualizar, Consumer<T> excluir) {
        TableColumn<T, Void> col = new TableColumn<>("Ações");
        col.setCellFactory(tc -> new TableCell<T, Void>() {
            private final Button btnAtualizar = new Button("Atualizar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox painelBotoes = new HBox(5, btnAtualizar, btnExcluir);

            {
                btnAtualizar.setOnAction(e -> atualizar.accept(getTableRow().getItem()));
                btnExcluir.setOnAction(e -> {
                    if (confirmar("Deseja excluir este registro?")) {
                        tentar(() -> excluir.accept(getTableRow().getItem()));
                    }
                });
            }

            @Override
            protected void updateItem(Void valor, boolean empty) {
                super.updateItem(valor, empty);
                setGraphic(empty ? null : painelBotoes);
            }
        });
        return col;
    }

    // Coluna com um botão Remover (usada na tabela de itens)
    public static <T> TableColumn<T, Void> colunaRemover(Consumer<T> remover) {
        TableColumn<T, Void> col = new TableColumn<>("Ação");
        col.setCellFactory(tc -> new TableCell<T, Void>() {
            private final Button btnRemover = new Button("Remover");

            {
                btnRemover.setOnAction(e -> remover.accept(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void valor, boolean empty) {
                super.updateItem(valor, empty);
                setGraphic(empty ? null : btnRemover);
            }
        });
        return col;
    }

    // Executa a ação e, se o banco der erro, mostra a mensagem para o usuário
    public static void tentar(Runnable acao) {
        try {
            acao.run();
        } catch (PersistenciaException ex) {
            new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    // Botão Salvar: o Control devolve false quando algum campo está inválido
    public static void salvar(BooleanSupplier salvar, String entidade) {
        tentar(() -> {
            if (salvar.getAsBoolean()) {
                new Alert(AlertType.INFORMATION, entidade + " salvo com sucesso", ButtonType.OK).showAndWait();
            } else {
                new Alert(AlertType.WARNING, "Corrija os campos indicados em vermelho", ButtonType.OK).showAndWait();
            }
        });
    }

    public static boolean confirmar(String mensagem) {
        return new Alert(AlertType.CONFIRMATION, mensagem, ButtonType.OK, ButtonType.CANCEL)
            .showAndWait().filter(b -> b == ButtonType.OK).isPresent();
    }
}
