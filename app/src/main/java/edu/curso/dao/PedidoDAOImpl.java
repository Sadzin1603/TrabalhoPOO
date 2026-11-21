package edu.curso.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.curso.entidade.Caixa;
import edu.curso.entidade.Cliente;
import edu.curso.entidade.ItemPedido;
import edu.curso.entidade.Pedido;
import edu.curso.entidade.Produto;

public class PedidoDAOImpl implements PedidoDAO {

    private void preencher(PreparedStatement stmt, Pedido e) throws SQLException {
        stmt.setString(1, e.getStatus());
        stmt.setString(2, e.getFormaPagamento());
        stmt.setDate(3, e.getDataPedido() == null ? null : Date.valueOf(e.getDataPedido()));
        stmt.setDouble(4, e.getValorTotal());
        stmt.setLong(5, e.getCliente().getId());
        stmt.setLong(6, e.getCaixa().getId());
    }

    private Pedido mapear(ResultSet rs) throws SQLException {
        Pedido e = new Pedido();
        e.setId(rs.getLong("id"));
        e.setStatus(rs.getString("status"));
        e.setFormaPagamento(rs.getString("forma_pagamento"));
        e.setDataPedido(rs.getDate("data_pedido") == null ? null : rs.getDate("data_pedido").toLocalDate());
        e.setValorTotal(rs.getDouble("valor_total"));

        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id_cliente"));
        cliente.setNome(rs.getString("nome_cliente"));
        e.setCliente(cliente);

        Caixa caixa = new Caixa();
        caixa.setId(rs.getLong("id_caixa"));
        caixa.setNome(rs.getString("nome_caixa"));
        e.setCaixa(caixa);

        return e;
    }

    private void salvarItens(Connection con, Pedido e) throws SQLException {
        String sql = "INSERT INTO itens_pedido (id_pedido, id_produto, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (ItemPedido i : e.getItens()) {
                stmt.setLong(1, e.getId());
                stmt.setLong(2, i.getProduto().getId());
                stmt.setInt(3, i.getQuantidade());
                stmt.setDouble(4, i.getValorUnitario());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void carregarItens(Connection con, Pedido p) throws SQLException {
        String sql = "SELECT i.id, i.quantidade, i.valor_unitario, i.id_produto, pr.nome AS nome_produto "
                   + "FROM itens_pedido i "
                   + "JOIN produtos pr ON pr.id = i.id_produto "
                   + "WHERE i.id_pedido = ? ORDER BY i.id";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, p.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemPedido i = new ItemPedido();
                    i.setId(rs.getLong("id"));
                    i.setQuantidade(rs.getInt("quantidade"));
                    i.setValorUnitario(rs.getDouble("valor_unitario"));
                    Produto produto = new Produto();
                    produto.setId(rs.getLong("id_produto"));
                    produto.setNome(rs.getString("nome_produto"));
                    i.setProduto(produto);
                    p.getItens().add(i);
                }
            }
        }
    }

    @Override
    public Pedido salvar(Pedido e) {
        String sql = "INSERT INTO pedidos (status, forma_pagamento, data_pedido, valor_total, id_cliente, id_caixa) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexaoFactory.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    preencher(stmt, e);
                    stmt.executeUpdate();
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            e.setId(rs.getLong(1));
                        }
                    }
                }
                salvarItens(con, e);
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao inserir pedido no banco de dados: " + ex.getMessage(), ex);
        }
        return e;
    }

    @Override
    public List<Pedido> pesquisarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.status, p.forma_pagamento, p.data_pedido, p.valor_total, "
                   + "p.id_cliente, c.nome AS nome_cliente, p.id_caixa, cx.nome AS nome_caixa "
                   + "FROM pedidos p "
                   + "JOIN clientes c ON c.id = p.id_cliente "
                   + "JOIN caixas cx ON cx.id = p.id_caixa "
                   + "ORDER BY p.id";
        try (Connection con = ConexaoFactory.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            for (Pedido p : lista) {
                carregarItens(con, p);
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao pesquisar pedido no banco de dados: " + ex.getMessage(), ex);
        }
        return lista;
    }

    @Override
    public void excluir(Pedido e) {
        String sql = "DELETE FROM pedidos WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, e.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir pedido (verifique se há registros ligados a ele): " + ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(long id, Pedido e) {
        String sql = "UPDATE pedidos SET status=?, forma_pagamento=?, data_pedido=?, valor_total=?, id_cliente=?, id_caixa=? WHERE id=?";
        e.setId(id);
        try (Connection con = ConexaoFactory.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement stmt = con.prepareStatement(sql)) {
                    preencher(stmt, e);
                    stmt.setLong(7, id);
                    stmt.executeUpdate();
                }
                try (PreparedStatement del = con.prepareStatement("DELETE FROM itens_pedido WHERE id_pedido=?")) {
                    del.setLong(1, id);
                    del.executeUpdate();
                }
                salvarItens(con, e);
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao atualizar pedido no banco de dados: " + ex.getMessage(), ex);
        }
    }
}