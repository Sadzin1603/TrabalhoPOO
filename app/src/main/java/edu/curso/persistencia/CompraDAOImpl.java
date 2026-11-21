package edu.curso.persistencia;

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
        stmt.setLong(8, e.getProduto().getId());
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

        Produto produto = new Produto();
        produto.setId(rs.getLong("id_produto"));
        produto.setNome(rs.getString("nome_produto"));
        e.setProduto(produto);

        return e;
    }

    @Override
    public Compra salvar(Compra e) {
        String sql = "INSERT INTO compras (status, data_compra, previsao_entrega, volume_total, valor_frete, valor_total, id_fornecedor, id_produto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            throw new PersistenciaException("Erro ao inserir compra no banco de dados: " + ex.getMessage(), ex);
        }
        return e;
    }

    @Override
    public List<Compra> pesquisarTodos() {
        List<Compra> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.status, c.data_compra, c.previsao_entrega, c.volume_total, c.valor_frete, c.valor_total, "
                   + "c.id_fornecedor, f.nome AS nome_fornecedor, c.id_produto, pr.nome AS nome_produto "
                   + "FROM compras c "
                   + "JOIN fornecedores f ON f.id = c.id_fornecedor "
                   + "JOIN produtos pr ON pr.id = c.id_produto "
                   + "ORDER BY c.id";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
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
        String sql = "UPDATE compras SET status=?, data_compra=?, previsao_entrega=?, volume_total=?, valor_frete=?, valor_total=?, id_fornecedor=?, id_produto=? WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            preencher(stmt, e);
            stmt.setLong(9, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao atualizar compra no banco de dados: " + ex.getMessage(), ex);
        }
    }
}