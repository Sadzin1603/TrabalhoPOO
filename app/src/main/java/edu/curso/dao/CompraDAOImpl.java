package edu.curso.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.curso.entidade.Compra;
import edu.curso.entidade.Fornecedor;
import edu.curso.entidade.ItemCompra;
import edu.curso.entidade.Produto;

public class CompraDAOImpl implements CompraDAO {

    private void preencher(PreparedStatement stmt, Compra e) throws SQLException {
        stmt.setString(1, e.getStatus());
        stmt.setDate(2, e.getDataCompra() == null ? null : Date.valueOf(e.getDataCompra()));
        stmt.setDate(3, e.getPrevisaoEntrega() == null ? null : Date.valueOf(e.getPrevisaoEntrega()));
        stmt.setInt(4, e.getVolumeTotal());
        stmt.setDouble(5, e.getValorFrete());
        stmt.setDouble(6, e.getValorTotal());
        stmt.setLong(7, e.getFornecedor().getId());
    }

    private Compra mapear(ResultSet rs) throws SQLException {
        Compra e = new Compra();
        e.setId(rs.getLong("id"));
        e.setStatus(rs.getString("status"));
        e.setDataCompra(rs.getDate("data_compra") == null ? null : rs.getDate("data_compra").toLocalDate());
        e.setPrevisaoEntrega(rs.getDate("previsao_entrega") == null ? null : rs.getDate("previsao_entrega").toLocalDate());
        e.setVolumeTotal(rs.getInt("volume_total"));
        e.setValorFrete(rs.getDouble("valor_frete"));
        e.setValorTotal(rs.getDouble("valor_total"));

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(rs.getLong("id_fornecedor"));
        fornecedor.setNome(rs.getString("nome_fornecedor"));
        e.setFornecedor(fornecedor);

        return e;
    }

    private void salvarItens(Connection con, Compra e) throws SQLException {
        String sql = "INSERT INTO itens_compra (id_compra, id_produto, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (ItemCompra i : e.getItens()) {
                stmt.setLong(1, e.getId());
                stmt.setLong(2, i.getProduto().getId());
                stmt.setInt(3, i.getQuantidade());
                stmt.setDouble(4, i.getValorUnitario());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void carregarItens(Connection con, Compra c) throws SQLException {
        String sql = "SELECT i.id, i.quantidade, i.valor_unitario, i.id_produto, pr.nome AS nome_produto "
                   + "FROM itens_compra i "
                   + "JOIN produtos pr ON pr.id = i.id_produto "
                   + "WHERE i.id_compra = ? ORDER BY i.id";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, c.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemCompra i = new ItemCompra();
                    i.setId(rs.getLong("id"));
                    i.setQuantidade(rs.getInt("quantidade"));
                    i.setValorUnitario(rs.getDouble("valor_unitario"));
                    Produto produto = new Produto();
                    produto.setId(rs.getLong("id_produto"));
                    produto.setNome(rs.getString("nome_produto"));
                    i.setProduto(produto);
                    c.getItens().add(i);
                }
            }
        }
    }

    @Override
    public Compra salvar(Compra e) {
        String sql = "INSERT INTO compras (status, data_compra, previsao_entrega, volume_total, valor_frete, valor_total, id_fornecedor) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            throw new PersistenciaException("Erro ao inserir compra no banco de dados: " + ex.getMessage(), ex);
        }
        return e;
    }

    @Override
    public List<Compra> pesquisarTodos() {
        List<Compra> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.status, c.data_compra, c.previsao_entrega, c.volume_total, c.valor_frete, c.valor_total, "
                   + "c.id_fornecedor, f.nome AS nome_fornecedor "
                   + "FROM compras c "
                   + "JOIN fornecedores f ON f.id = c.id_fornecedor "
                   + "ORDER BY c.id";
        try (Connection con = ConexaoFactory.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            for (Compra c : lista) {
                carregarItens(con, c);
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao pesquisar compra no banco de dados: " + ex.getMessage(), ex);
        }
        return lista;
    }

    @Override
    public void excluir(Compra e) {
        String sql = "DELETE FROM compras WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, e.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir compra (verifique se há registros ligados a ele): " + ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(long id, Compra e) {
        String sql = "UPDATE compras SET status=?, data_compra=?, previsao_entrega=?, volume_total=?, valor_frete=?, valor_total=?, id_fornecedor=? WHERE id=?";
        e.setId(id);
        try (Connection con = ConexaoFactory.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement stmt = con.prepareStatement(sql)) {
                    preencher(stmt, e);
                    stmt.setLong(8, id);
                    stmt.executeUpdate();
                }
                try (PreparedStatement del = con.prepareStatement("DELETE FROM itens_compra WHERE id_compra=?")) {
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
            throw new PersistenciaException("Erro ao atualizar compra no banco de dados: " + ex.getMessage(), ex);
        }
    }
}