package edu.curso.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.curso.entidade.Pedido;

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
        e.setCliente(ClienteDAOImpl.getInstance().pesquisarPorId(rs.getLong("id_cliente")));
        e.setCaixa(CaixaDAOImpl.getInstance().pesquisarPorId(rs.getLong("id_caixa")));
        return e;
    }

    @Override
    public Pedido salvar(Pedido e) {
        String sql = "INSERT INTO pedidos (status, forma_pagamento, data_pedido, valor_total, id_cliente, id_caixa) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencher(stmt, e);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao inserir pedido no banco de dados: " + ex.getMessage(), ex);
        }
        return e;
    }

    @Override
    public List<Pedido> pesquisarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT id, status, forma_pagamento, data_pedido, valor_total, id_cliente, id_caixa FROM pedidos ORDER BY id";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
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
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            preencher(stmt, e);
            stmt.setLong(7, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao atualizar pedido no banco de dados: " + ex.getMessage(), ex);
        }
    }
}
