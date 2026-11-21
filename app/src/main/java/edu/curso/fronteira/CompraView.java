package edu.curso.fronteira;

import edu.curso.controle.CompraControl;
import edu.curso.entidade.Compra;
import edu.curso.entidade.Fornecedor;
import edu.curso.entidade.ItemCompra;
import edu.curso.entidade.Produto;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class CompraView {

    private CompraControl control = new CompraControl();
    private TableView<Compra> table = new TableView<>();

    public BorderPane montar() {
        BorderPane panPrincipal = new BorderPane();
        GridPane p = Tela.criarFormulario();

        TextField txtNumeroCompra = new TextField();
        txtNumeroCompra.setPromptText("Somente para pesquisa");
        Tela.linha(p, 0, "Nº da Compra", txtNumeroCompra, control.erroNumeroCompraProperty());
        Bindings.bindBidirectional(control.numeroCompraProperty(), txtNumeroCompra.textProperty());

        ComboBox<String> cmbStatus = new ComboBox<>();
        cmbStatus.getItems().addAll("Aguardando pagamento", "Compra cancelado", "Pagamento efetuado",
            "Envio em andamento", "Chegou");
        Tela.linha(p, 1, "Status", cmbStatus, control.erroStatusProperty());
        cmbStatus.valueProperty().bindBidirectional(control.statusProperty());

        TextField txtDataCompra = new TextField();
        txtDataCompra.setPromptText("dd/MM/aaaa");
        Tela.linha(p, 2, "Data da Compra", txtDataCompra, control.erroDataCompraProperty());
        Bindings.bindBidirectional(control.dataCompraProperty(), txtDataCompra.textProperty());

        TextField txtPrevisaoEntrega = new TextField();
        txtPrevisaoEntrega.setPromptText("dd/MM/aaaa");
        Tela.linha(p, 3, "Previsão de Entrega", txtPrevisaoEntrega, control.erroPrevisaoEntregaProperty());
        Bindings.bindBidirectional(control.previsaoEntregaProperty(), txtPrevisaoEntrega.textProperty());

        TextField txtVolumeTotal = new TextField();
        txtVolumeTotal.setEditable(false);
        txtVolumeTotal.setPromptText("Soma das quantidades");
        Tela.linha(p, 4, "Volume Total", txtVolumeTotal, null);
        Bindings.bindBidirectional(control.volumeTotalProperty(), txtVolumeTotal.textProperty());

        TextField txtValorFrete = new TextField();
        Tela.linha(p, 5, "Valor do Frete", txtValorFrete, control.erroValorFreteProperty());
        Bindings.bindBidirectional(control.valorFreteProperty(), txtValorFrete.textProperty());

        TextField txtValorTotal = new TextField();
        txtValorTotal.setEditable(false);
        txtValorTotal.setPromptText("Itens + frete");
        Tela.linha(p, 6, "Valor Total", txtValorTotal, null);
        Bindings.bindBidirectional(control.valorTotalProperty(), txtValorTotal.textProperty());

        ComboBox<Fornecedor> cmbFornecedor = Tela.combo(control.getListaFornecedores(), control::recarregarListaFornecedor, Fornecedor::getNome);
        Tela.linha(p, 7, "Fornecedor", cmbFornecedor, control.erroFornecedorProperty());
        cmbFornecedor.valueProperty().bindBidirectional(control.fornecedorProperty());

        ComboBox<Produto> cmbProduto = Tela.combo(control.getListaProdutos(), control::recarregarListaProduto, Produto::getNome);
        Tela.linha(p, 8, "Produto", cmbProduto, control.erroProdutoProperty());
        cmbProduto.valueProperty().bindBidirectional(control.produtoProperty());

        TextField txtQuantidade = new TextField();
        Tela.linha(p, 9, "Quantidade", txtQuantidade, control.erroQuantidadeProperty());
        Bindings.bindBidirectional(control.quantidadeProperty(), txtQuantidade.textProperty());

        TextField txtValorUnitario = new TextField();
        Tela.linha(p, 10, "Valor Unit.", txtValorUnitario, control.erroValorUnitarioProperty());
        Bindings.bindBidirectional(control.valorUnitarioProperty(), txtValorUnitario.textProperty());

        Button btnAdicionarItem = new Button("Adicionar item");
        Label lblErroItens = new Label();
        lblErroItens.setTextFill(Color.RED);
        lblErroItens.textProperty().bind(control.erroItensProperty());
        p.add(new HBox(10, btnAdicionarItem, lblErroItens), 1, 11);
        btnAdicionarItem.setOnAction(e -> control.adicionarItem());

        // Tabela dos itens da compra
        TableView<ItemCompra> tableItens = new TableView<>();
        TableColumn<ItemCompra, String> colItemProduto = Tela.coluna("Produto", item -> item.getProduto().getNome());
        TableColumn<ItemCompra, String> colItemQtd = Tela.coluna("Qtd", ItemCompra::getQuantidade);
        TableColumn<ItemCompra, String> colItemValor = Tela.coluna("Valor Unit.", ItemCompra::getValorUnitario);
        TableColumn<ItemCompra, String> colItemSubtotal = Tela.coluna("Subtotal", ItemCompra::getSubtotal);
        TableColumn<ItemCompra, Void> colItemAcao = Tela.colunaRemover(control::removerItem);
        tableItens.getColumns().addAll(colItemProduto, colItemQtd, colItemValor, colItemSubtotal, colItemAcao);
        tableItens.setItems(control.getItens());
        tableItens.setPrefHeight(130);
        p.add(tableItens, 0, 12, 3, 1);

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        p.add(new HBox(10, btnSalvar, btnLimpar, btnPesquisar), 1, 13);

        // Tabela das compras
        TableColumn<Compra, String> colId = Tela.coluna("Nº", Compra::getId);
        TableColumn<Compra, String> colStatus = Tela.coluna("Status", Compra::getStatus);
        TableColumn<Compra, String> colDataCompra = Tela.coluna("Data da Compra", Compra::getDataCompra);
        TableColumn<Compra, String> colPrevisaoEntrega = Tela.coluna("Previsão de Entrega", Compra::getPrevisaoEntrega);
        TableColumn<Compra, String> colVolumeTotal = Tela.coluna("Volume Total", Compra::getVolumeTotal);
        TableColumn<Compra, String> colValorFrete = Tela.coluna("Valor do Frete", Compra::getValorFrete);
        TableColumn<Compra, String> colValorTotal = Tela.coluna("Valor Total", Compra::getValorTotal);
        TableColumn<Compra, String> colFornecedor = Tela.coluna("Fornecedor", cmp -> cmp.getFornecedor().getNome());
        TableColumn<Compra, String> colItens = Tela.coluna("Itens", cmp -> cmp.getItens().size());
        TableColumn<Compra, Void> colAcoes = Tela.colunaAcoes(control::atualizar, control::excluir);

        table.getColumns().addAll(colId, colStatus, colDataCompra, colPrevisaoEntrega, colVolumeTotal,
            colValorFrete, colValorTotal, colFornecedor, colItens, colAcoes);
        table.setItems(control.getLista());

        btnSalvar.setOnAction(e -> Tela.salvar(control::salvar, "Compra"));
        btnLimpar.setOnAction(e -> control.limparCampos());
        btnPesquisar.setOnAction(e -> Tela.tentar(control::pesquisar));

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);
        return panPrincipal;
    }
}
