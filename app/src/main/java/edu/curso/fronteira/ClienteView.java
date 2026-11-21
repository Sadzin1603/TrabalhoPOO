package edu.curso.fronteira;

import edu.curso.controle.ClienteControl;
import edu.curso.entidade.Cliente;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ClienteView {

    private ClienteControl control = new ClienteControl();
    private TableView<Cliente> table = new TableView<>();

    public BorderPane montar() {
        BorderPane panPrincipal = new BorderPane();
        GridPane p = Tela.criarFormulario();
        TextField txtNome = new TextField();
        Tela.linha(p, 0, "Nome", txtNome, control.erroNomeProperty());
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());

        TextField txtCpf = new TextField();
        Tela.linha(p, 1, "CPF", txtCpf, control.erroCpfProperty());
        Bindings.bindBidirectional(control.cpfProperty(), txtCpf.textProperty());

        TextField txtTelefone = new TextField();
        Tela.linha(p, 2, "Telefone", txtTelefone, control.erroTelefoneProperty());
        Bindings.bindBidirectional(control.telefoneProperty(), txtTelefone.textProperty());

        TextField txtEmail = new TextField();
        Tela.linha(p, 3, "Email", txtEmail, control.erroEmailProperty());
        Bindings.bindBidirectional(control.emailProperty(), txtEmail.textProperty());

        TextField txtDataNascimento = new TextField();
        txtDataNascimento.setPromptText("dd/MM/aaaa");
        Tela.linha(p, 4, "Data de Nascimento", txtDataNascimento, control.erroDataNascimentoProperty());
        Bindings.bindBidirectional(control.dataNascimentoProperty(), txtDataNascimento.textProperty());

        TextField txtEndereco = new TextField();
        Tela.linha(p, 5, "Endereço", txtEndereco, control.erroEnderecoProperty());
        Bindings.bindBidirectional(control.enderecoProperty(), txtEndereco.textProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        p.add(new HBox(10, btnSalvar, btnLimpar, btnPesquisar), 1, 6);

        TableColumn<Cliente, String> colNome = Tela.coluna("Nome", Cliente::getNome);
        TableColumn<Cliente, String> colCpf = Tela.coluna("CPF", Cliente::getCpf);
        TableColumn<Cliente, String> colTelefone = Tela.coluna("Telefone", Cliente::getTelefone);
        TableColumn<Cliente, String> colEmail = Tela.coluna("Email", Cliente::getEmail);
        TableColumn<Cliente, String> colDataNascimento = Tela.coluna("Data de Nascimento", Cliente::getDataNascimento);
        TableColumn<Cliente, String> colEndereco = Tela.coluna("Endereço", Cliente::getEndereco);
        TableColumn<Cliente, Void> colAcoes = Tela.colunaAcoes(control::atualizar, control::excluir);

        table.getColumns().addAll(colNome, colCpf, colTelefone, colEmail, colDataNascimento, colEndereco, colAcoes);
        table.setItems(control.getLista());

        btnSalvar.setOnAction(e -> Tela.salvar(control::salvar, "Cliente"));
        btnLimpar.setOnAction(e -> control.limparCampos());
        btnPesquisar.setOnAction(e -> Tela.tentar(control::pesquisar));

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);
        return panPrincipal;
    }
}
