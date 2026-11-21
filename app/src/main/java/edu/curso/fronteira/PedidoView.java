package edu.curso.fronteira;

import edu.curso.controle.PedidoControl;
import edu.curso.entidade.Caixa;
import edu.curso.entidade.Cliente;
import edu.curso.entidade.ItemPedido;
import edu.curso.entidade.Pedido;
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

public class PedidoView {

    private PedidoControl control = new PedidoControl();
    private TableView<Pedido> table = new TableView<>();

    public BorderPane montar() {
        BorderPane panPrincipal = new BorderPane();
        GridPane p = Tela.criarFormulario();

        TextField txtNumeroPedido = new TextField();
        txtNumeroPedido.setPromptText("Somente para pesquisa");
        Tela.linha(p, 0, "Nº do Pedido", txtNumeroPedido, control.erroNumeroPedidoProperty());
        Bindings.bindBidirectional(control.numeroPedidoProperty(), txtNumeroPedido.textProperty());

        ComboBox<String> cmbStatus = new ComboBox<>();
        cmbStatus.getItems().addAll("Em andamento", "Cancelado", "Concluído");
        Tela.linha(p, 1, "Status", cmbStatus, control.erroStatusProperty());
        cmbStatus.valueProperty().bindBidirectional(control.statusProperty());

        ComboBox<String> cmbFormaPagamento = new ComboBox<>();
        cmbFormaPagamento.getItems().addAll("Dinheiro", "Cartão de crédito", "Cartão de débito", "Pix");
        Tela.linha(p, 2, "Forma de Pagamento", cmbFormaPagamento, control.erroFormaPagamentoProperty());
        cmbFormaPagamento.valueProperty().bindBidirectional(control.formaPagamentoProperty());

        TextField txtDataPedido = new TextField();
        txtDataPedido.setPromptText("dd/MM/aaaa");
        Tela.linha(p, 3, "Data do Pedido", txtDataPedido, control.erroDataPedidoProperty());
        Bindings.bindBidirectional(control.dataPedidoProperty(), txtDataPedido.textProperty());

        TextField txtValorTotal = new TextField();
        txtValorTotal.setEditable(false);
        txtValorTotal.setPromptText("Calculado pelos itens");
        Tela.linha(p, 4, "Valor Total", txtValorTotal, control.erroValorTotalProperty());
        Bindings.bindBidirectional(control.valorTotalProperty(), txtValorTotal.textProperty());

        ComboBox<Cliente> cmbCliente = Tela.combo(control.getListaClientes(), control::recarregarListaCliente, Cliente::getNome);
        Tela.linha(p, 5, "Cliente", cmbCliente, control.erroClienteProperty());
        cmbCliente.valueProperty().bindBidirectional(control.clienteProperty());

        ComboBox<Caixa> cmbCaixa = Tela.combo(control.getListaCaixas(), control::recarregarListaCaixa, Caixa::getNome);
        Tela.linha(p, 6, "Caixa", cmbCaixa, control.erroCaixaProperty());
        cmbCaixa.valueProperty().bindBidirectional(control.caixaProperty());

        ComboBox<Produto> cmbProduto = Tela.combo(control.getListaProdutos(), control::recarregarListaProduto, Produto::getNome);
        Tela.linha(p, 7, "Produto", cmbProduto, control.erroProdutoProperty());
        cmbProduto.valueProperty().bindBidirectional(control.produtoProperty());

        TextField txtQuantidade = new TextField();
        Tela.linha(p, 8, "Quantidade", txtQuantidade, control.erroQuantidadeProperty());
        Bindings.bindBidirectional(control.quantidadeProperty(), txtQuantidade.textProperty());

        Button btnAdicionarItem = new Button("Adicionar item");
        Label lblErroItens = new Label();
        lblErroItens.setTextFill(Color.RED);
        lblErroItens.textProperty().bind(control.erroItensProperty());
        p.add(new HBox(10, btnAdicionarItem, lblErroItens), 1, 9);
        btnAdicionarItem.setOnAction(e -> control.adicionarItem());

        // Tabela dos itens do pedido
        TableView<ItemPedido> tableItens = new TableView<>();
        TableColumn<ItemPedido, String> colItemProduto = Tela.coluna("Produto", item -> item.getProduto().getNome());
        TableColumn<ItemPedido, String> colItemQtd = Tela.coluna("Qtd", ItemPedido::getQuantidade);
        TableColumn<ItemPedido, String> colItemValor = Tela.coluna("Valor Unit.", ItemPedido::getValorUnitario);
        TableColumn<ItemPedido, String> colItemSubtotal = Tela.coluna("Subtotal", ItemPedido::getSubtotal);
        TableColumn<ItemPedido, Void> colItemAcao = Tela.colunaRemover(control::removerItem);
        tableItens.getColumns().addAll(colItemProduto, colItemQtd, colItemValor, colItemSubtotal, colItemAcao);
        tableItens.setItems(control.getItens());
        tableItens.setPrefHeight(130);
        p.add(tableItens, 0, 10, 3, 1);

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnPesquisar = new Button("Pesquisar");
        p.add(new HBox(10, btnSalvar, btnLimpar, btnPesquisar), 1, 11);

        // Tabela dos pedidos
        TableColumn<Pedido, String> colId = Tela.coluna("Nº", Pedido::getId);
        TableColumn<Pedido, String> colStatus = Tela.coluna("Status", Pedido::getStatus);
        TableColumn<Pedido, String> colFormaPagamento = Tela.coluna("Forma de Pagamento", Pedido::getFormaPagamento);
        TableColumn<Pedido, String> colDataPedido = Tela.coluna("Data do Pedido", Pedido::getDataPedido);
        TableColumn<Pedido, String> colValorTotal = Tela.coluna("Valor Total", Pedido::getValorTotal);
        TableColumn<Pedido, String> colCliente = Tela.coluna("Cliente", ped -> ped.getCliente().getNome());
        TableColumn<Pedido, String> colCaixa = Tela.coluna("Caixa", ped -> ped.getCaixa().getNome());
        TableColumn<Pedido, String> colItens = Tela.coluna("Itens", ped -> ped.getItens().size());
        TableColumn<Pedido, Void> colAcoes = Tela.colunaAcoes(control::atualizar, control::excluir);

        table.getColumns().addAll(colId, colStatus, colFormaPagamento, colDataPedido, colValorTotal, colCliente, colCaixa, colItens, colAcoes);
        table.setItems(control.getLista());

        btnSalvar.setOnAction(e -> Tela.salvar(control::salvar, "Pedido"));
        btnLimpar.setOnAction(e -> control.limparCampos());
        btnPesquisar.setOnAction(e -> Tela.tentar(control::pesquisar));

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);
        return panPrincipal;
    }
}
