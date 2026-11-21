package edu.curso.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.curso.entidade.Cliente;

public class ClienteDAOImpl implements ClienteDAO {

    private void preencher(PreparedStatement stmt, Cliente e) throws SQLException {
        stmt.setString(1, e.getNome());
        stmt.setString(2, e.getCpf());
        stmt.setString(3, e.getTelefone());
        stmt.setString(4, e.getEmail());
        stmt.setDate(5, e.getDataNascimento() == null ? null : Date.valueOf(e.getDataNascimento()));
        stmt.setString(6, e.getEndereco());
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente e = new Cliente();
        e.setId(rs.getLong("id"));
        e.setNome(rs.getString("nome"));
        e.setCpf(rs.getString("cpf"));
        e.setTelefone(rs.getString("telefone"));
        e.setEmail(rs.getString("email"));
        e.setDataNascimento(rs.getDate("data_nascimento") == null ? null : rs.getDate("data_nascimento").toLocalDate());
        e.setEndereco(rs.getString("endereco"));
        return e;
    }

    @Override
    public Cliente salvar(Cliente e) {
        String sql = "INSERT INTO clientes (nome, cpf, telefone, email, data_nascimento, endereco) VALUES (?, ?, ?, ?, ?, ?)";
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
            throw new PersistenciaException("Erro ao inserir cliente no banco de dados: " + ex.getMessage(), ex);
        }
        return e;
    }

    @Override
    public List<Cliente> pesquisarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, telefone, email, data_nascimento, endereco FROM clientes ORDER BY id";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao pesquisar cliente no banco de dados: " + ex.getMessage(), ex);
        }
        return lista;
    }

    @Override
    public void excluir(Cliente e) {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, e.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir cliente (verifique se há registros ligados a ele): " + ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(long id, Cliente e) {
        String sql = "UPDATE clientes SET nome=?, cpf=?, telefone=?, email=?, data_nascimento=?, endereco=? WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            preencher(stmt, e);
            stmt.setLong(7, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao atualizar cliente no banco de dados: " + ex.getMessage(), ex);
        }
    }
}
