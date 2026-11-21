package edu.curso.fronteira;

import edu.curso.controle.FornecedorControl;
import edu.curso.entidade.Fornecedor;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class FornecedorView {

    private FornecedorControl control = new FornecedorControl();
    private TableView<Fornecedor> table = new TableView<>();

    public BorderPane montar() {
        BorderPane panPrincipal = new BorderPane();
        GridPane p = Tela.criarFormulario();
        TextField txtNome = new TextField();
        Tela.linha(p, 0, "Nome", txtNome, control.erroNomeProperty());
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());

        TextField txtRazaoSocial = new TextField();
        Tela.linha(p, 1, "Razão Social", txtRazaoSocial, control.erroRazaoSocialProperty());
        Bindings.bindBidirectional(control.razaoSocialProperty(), txtRazaoSocial.textProperty());

        TextField txtCnpj = new TextField();
        Tela.linha(p, 2, "CNPJ", txtCnpj, control.erroCnpjProperty());
        Bindings.bindBidirectional(control.cnpjProperty(), txtCnpj.textProperty());

        TextField txtTelefone = new TextField();
        Tela.linha(p, 3, "Telefone", txtTelefone, control.erroTelefoneProperty());
        Bindings.bindBidirectional(control.telefoneProperty(), txtTelefone.textProperty());

        TextField txtEmail = new TextField();
        Tela.linha(p, 4, "Email", txtEmail, control.erroEmailProperty());
        Bindings.bindBidirectional(control.emailProperty(), txtEmail.textProperty());

        TextField txtEndereco = new TextField();
        Tela.linha(p, 5, "Endereço", txtEndereco, control.erroEnderecoProperty());
        Bindings.bindBidirectional(control.enderecoProperty(), txtEndereco.textProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        p.add(new HBox(10, btnSalvar, btnLimpar, btnPesquisar), 1, 6);

        TableColumn<Fornecedor, String> colNome = Tela.coluna("Nome", Fornecedor::getNome);
        TableColumn<Fornecedor, String> colRazaoSocial = Tela.coluna("Razão Social", Fornecedor::getRazaoSocial);
        TableColumn<Fornecedor, String> colCnpj = Tela.coluna("CNPJ", Fornecedor::getCnpj);
        TableColumn<Fornecedor, String> colTelefone = Tela.coluna("Telefone", Fornecedor::getTelefone);
        TableColumn<Fornecedor, String> colEmail = Tela.coluna("Email", Fornecedor::getEmail);
        TableColumn<Fornecedor, String> colEndereco = Tela.coluna("Endereço", Fornecedor::getEndereco);
        TableColumn<Fornecedor, Void> colAcoes = Tela.colunaAcoes(control::atualizar, control::excluir);

        table.getColumns().addAll(colNome, colRazaoSocial, colCnpj, colTelefone, colEmail, colEndereco, colAcoes);
        table.setItems(control.getLista());

        btnSalvar.setOnAction(e -> Tela.salvar(control::salvar, "Fornecedor"));
        btnLimpar.setOnAction(e -> control.limparCampos());
        btnPesquisar.setOnAction(e -> Tela.tentar(control::pesquisar));

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);
        return panPrincipal;
    }
}
