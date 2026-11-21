package edu.curso.fronteira;

import edu.curso.controle.CaixaControl;
import edu.curso.entidade.Caixa;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CaixaView {

    private CaixaControl control = new CaixaControl();
    private TableView<Caixa> table = new TableView<>();

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

        TextField txtSalario = new TextField();
        Tela.linha(p, 5, "Salário", txtSalario, control.erroSalarioProperty());
        Bindings.bindBidirectional(control.salarioProperty(), txtSalario.textProperty());

        TextField txtTurno = new TextField();
        Tela.linha(p, 6, "Turno", txtTurno, control.erroTurnoProperty());
        Bindings.bindBidirectional(control.turnoProperty(), txtTurno.textProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        p.add(new HBox(10, btnSalvar, btnLimpar, btnPesquisar), 1, 7);

        TableColumn<Caixa, String> colNome = Tela.coluna("Nome", Caixa::getNome);
        TableColumn<Caixa, String> colCpf = Tela.coluna("CPF", Caixa::getCpf);
        TableColumn<Caixa, String> colTelefone = Tela.coluna("Telefone", Caixa::getTelefone);
        TableColumn<Caixa, String> colEmail = Tela.coluna("Email", Caixa::getEmail);
        TableColumn<Caixa, String> colDataNascimento = Tela.coluna("Data de Nascimento", Caixa::getDataNascimento);
        TableColumn<Caixa, String> colSalario = Tela.coluna("Salário", Caixa::getSalario);
        TableColumn<Caixa, String> colTurno = Tela.coluna("Turno", Caixa::getTurno);
        TableColumn<Caixa, Void> colAcoes = Tela.colunaAcoes(control::atualizar, control::excluir);

        table.getColumns().addAll(colNome, colCpf, colTelefone, colEmail, colDataNascimento, colSalario, colTurno, colAcoes);
        table.setItems(control.getLista());

        btnSalvar.setOnAction(e -> Tela.salvar(control::salvar, "Caixa"));
        btnLimpar.setOnAction(e -> control.limparCampos());
        btnPesquisar.setOnAction(e -> Tela.tentar(control::pesquisar));

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);
        return panPrincipal;
    }
}
