package edu.curso.fronteira;

import edu.curso.dao.PersistenciaException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MenuPrincipalView extends Application {

    private Tab criarAba(String titulo, Node conteudo) {
        Tab aba = new Tab(titulo, conteudo);
        aba.setClosable(false);
        return aba;
    }

    @Override
    public void start(Stage stage) {
        TabPane abas = new TabPane();
        try {
            abas.getTabs().addAll(
                criarAba("Cliente", new ClienteView().montar()),
                criarAba("Caixa", new CaixaView().montar()),
                criarAba("Produto", new ProdutoView().montar()),
                criarAba("Pedido", new PedidoView().montar()),
                criarAba("Compra", new CompraView().montar()),
                criarAba("Fornecedor", new FornecedorView().montar())
            );
        } catch (PersistenciaException ex) {
            new Alert(AlertType.ERROR, ex.getMessage()
                + "\nConfira se o MariaDB está rodando e se o banco gestao_mercado foi criado.",
                ButtonType.OK).showAndWait();
            Platform.exit();
            return;
        }
        stage.setTitle("Gestão de Mercado");
        stage.setScene(new Scene(abas, 1100, 650));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(MenuPrincipalView.class, args);
    }
}
