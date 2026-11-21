package edu.curso.fronteira;

import edu.curso.controle.ProdutoControl;
import edu.curso.entidade.Produto;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ProdutoView {

    private ProdutoControl control = new ProdutoControl();
    private TableView<Produto> table = new TableView<>();

    public BorderPane montar() {
        BorderPane panPrincipal = new BorderPane();
        GridPane p = Tela.criarFormulario();
        TextField txtNome = new TextField();
        Tela.linha(p, 0, "Nome", txtNome, control.erroNomeProperty());
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());

        TextField txtMarca = new TextField();
        Tela.linha(p, 1, "Marca", txtMarca, control.erroMarcaProperty());
        Bindings.bindBidirectional(control.marcaProperty(), txtMarca.textProperty());

        TextField txtValorUnitario = new TextField();
        Tela.linha(p, 2, "Valor Unitário", txtValorUnitario, control.erroValorUnitarioProperty());
        Bindings.bindBidirectional(control.valorUnitarioProperty(), txtValorUnitario.textProperty());

        TextField txtValorAtacado = new TextField();
        Tela.linha(p, 3, "Valor Atacado", txtValorAtacado, control.erroValorAtacadoProperty());
        Bindings.bindBidirectional(control.valorAtacadoProperty(), txtValorAtacado.textProperty());

        TextField txtQtdAtacado = new TextField();
        Tela.linha(p, 4, "Qtd. Atacado", txtQtdAtacado, control.erroQtdAtacadoProperty());
        Bindings.bindBidirectional(control.qtdAtacadoProperty(), txtQtdAtacado.textProperty());

        TextField txtValidade = new TextField();
        txtValidade.setPromptText("dd/MM/aaaa");
        Tela.linha(p, 5, "Validade", txtValidade, control.erroValidadeProperty());
        Bindings.bindBidirectional(control.validadeProperty(), txtValidade.textProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        p.add(new HBox(10, btnSalvar, btnLimpar, btnPesquisar), 1, 6);

        TableColumn<Produto, String> colNome = Tela.coluna("Nome", Produto::getNome);
        TableColumn<Produto, String> colMarca = Tela.coluna("Marca", Produto::getMarca);
        TableColumn<Produto, String> colValorUnitario = Tela.coluna("Valor Unitário", Produto::getValorUnitario);
        TableColumn<Produto, String> colValorAtacado = Tela.coluna("Valor Atacado", Produto::getValorAtacado);
        TableColumn<Produto, String> colQtdAtacado = Tela.coluna("Qtd. Atacado", Produto::getQtdAtacado);
        TableColumn<Produto, String> colValidade = Tela.coluna("Validade", Produto::getValidade);
        TableColumn<Produto, Void> colAcoes = Tela.colunaAcoes(control::atualizar, control::excluir);

        table.getColumns().addAll(colNome, colMarca, colValorUnitario, colValorAtacado, colQtdAtacado, colValidade, colAcoes);
        table.setItems(control.getLista());

        btnSalvar.setOnAction(e -> Tela.salvar(control::salvar, "Produto"));
        btnLimpar.setOnAction(e -> control.limparCampos());
        btnPesquisar.setOnAction(e -> Tela.tentar(control::pesquisar));

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);
        return panPrincipal;
    }
}
