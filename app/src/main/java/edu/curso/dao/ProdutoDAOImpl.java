package edu.curso.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.curso.entidade.Produto;

public class ProdutoDAOImpl implements ProdutoDAO {

    private void preencher(PreparedStatement stmt, Produto e) throws SQLException {
        stmt.setString(1, e.getNome());
        stmt.setString(2, e.getMarca());
        stmt.setDouble(3, e.getValorUnitario());
        stmt.setDouble(4, e.getValorAtacado());
        stmt.setInt(5, e.getQtdAtacado());
        stmt.setDate(6, e.getValidade() == null ? null : Date.valueOf(e.getValidade()));
    }

    private Produto mapear(ResultSet rs) throws SQLException {
        Produto e = new Produto();
        e.setId(rs.getLong("id"));
        e.setNome(rs.getString("nome"));
        e.setMarca(rs.getString("marca"));
        e.setValorUnitario(rs.getDouble("valor_unitario"));
        e.setValorAtacado(rs.getDouble("valor_atacado"));
        e.setQtdAtacado(rs.getInt("qtd_atacado"));
        e.setValidade(rs.getDate("validade") == null ? null : rs.getDate("validade").toLocalDate());
        return e;
    }

    @Override
    public Produto salvar(Produto e) {
        String sql = "INSERT INTO produtos (nome, marca, valor_unitario, valor_atacado, qtd_atacado, validade) VALUES (?, ?, ?, ?, ?, ?)";
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
            throw new PersistenciaException("Erro ao inserir produto no banco de dados: " + ex.getMessage(), ex);
        }
        return e;
    }

    @Override
    public List<Produto> pesquisarTodos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT id, nome, marca, valor_unitario, valor_atacado, qtd_atacado, validade FROM produtos ORDER BY id";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao pesquisar produto no banco de dados: " + ex.getMessage(), ex);
        }
        return lista;
    }

    @Override
    public void excluir(Produto e) {
        String sql = "DELETE FROM produtos WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, e.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir produto (verifique se há registros ligados a ele): " + ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(long id, Produto e) {
        String sql = "UPDATE produtos SET nome=?, marca=?, valor_unitario=?, valor_atacado=?, qtd_atacado=?, validade=? WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            preencher(stmt, e);
            stmt.setLong(7, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao atualizar produto no banco de dados: " + ex.getMessage(), ex);
        }
    }
}
