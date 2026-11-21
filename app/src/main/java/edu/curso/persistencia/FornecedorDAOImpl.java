package edu.curso.persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.curso.entidade.Fornecedor;

public class FornecedorDAOImpl implements FornecedorDAO {

    private void preencher(PreparedStatement stmt, Fornecedor e) throws SQLException {
        stmt.setString(1, e.getNome());
        stmt.setString(2, e.getRazaoSocial());
        stmt.setString(3, e.getCnpj());
        stmt.setString(4, e.getTelefone());
        stmt.setString(5, e.getEmail());
        stmt.setString(6, e.getEndereco());
    }

    private Fornecedor mapear(ResultSet rs) throws SQLException {
        Fornecedor e = new Fornecedor();
        e.setId(rs.getLong("id"));
        e.setNome(rs.getString("nome"));
        e.setRazaoSocial(rs.getString("razao_social"));
        e.setCnpj(rs.getString("cnpj"));
        e.setTelefone(rs.getString("telefone"));
        e.setEmail(rs.getString("email"));
        e.setEndereco(rs.getString("endereco"));
        return e;
    }

    @Override
    public Fornecedor salvar(Fornecedor e) {
        String sql = "INSERT INTO fornecedores (nome, razao_social, cnpj, telefone, email, endereco) VALUES (?, ?, ?, ?, ?, ?)";
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
            throw new PersistenciaException("Erro ao inserir fornecedor no banco de dados: " + ex.getMessage(), ex);
        }
        return e;
    }

    @Override
    public List<Fornecedor> pesquisarTodos() {
        List<Fornecedor> lista = new ArrayList<>();
        String sql = "SELECT id, nome, razao_social, cnpj, telefone, email, endereco FROM fornecedores ORDER BY id";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao pesquisar fornecedor no banco de dados: " + ex.getMessage(), ex);
        }
        return lista;
    }

    @Override
    public void excluir(Fornecedor e) {
        String sql = "DELETE FROM fornecedores WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, e.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir fornecedor (verifique se há registros ligados a ele): " + ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(long id, Fornecedor e) {
        String sql = "UPDATE fornecedores SET nome=?, razao_social=?, cnpj=?, telefone=?, email=?, endereco=? WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            preencher(stmt, e);
            stmt.setLong(7, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao atualizar fornecedor no banco de dados: " + ex.getMessage(), ex);
        }
    }
}
