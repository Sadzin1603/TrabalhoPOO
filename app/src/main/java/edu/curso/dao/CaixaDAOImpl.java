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

public class CaixaDAOImpl implements CaixaDAO {

    private void preencher(PreparedStatement stmt, Caixa e) throws SQLException {
        stmt.setString(1, e.getNome());
        stmt.setString(2, e.getCpf());
        stmt.setString(3, e.getTelefone());
        stmt.setString(4, e.getEmail());
        stmt.setDate(5, e.getDataNascimento() == null ? null : Date.valueOf(e.getDataNascimento()));
        stmt.setDouble(6, e.getSalario());
        stmt.setString(7, e.getTurno());
    }

    private Caixa mapear(ResultSet rs) throws SQLException {
        Caixa e = new Caixa();
        e.setId(rs.getLong("id"));
        e.setNome(rs.getString("nome"));
        e.setCpf(rs.getString("cpf"));
        e.setTelefone(rs.getString("telefone"));
        e.setEmail(rs.getString("email"));
        e.setDataNascimento(rs.getDate("data_nascimento") == null ? null : rs.getDate("data_nascimento").toLocalDate());
        e.setSalario(rs.getDouble("salario"));
        e.setTurno(rs.getString("turno"));
        return e;
    }

    @Override
    public Caixa salvar(Caixa e) {
        String sql = "INSERT INTO caixas (nome, cpf, telefone, email, data_nascimento, salario, turno) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            throw new PersistenciaException("Erro ao inserir caixa no banco de dados: " + ex.getMessage(), ex);
        }
        return e;
    }

    @Override
    public List<Caixa> pesquisarTodos() {
        List<Caixa> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, telefone, email, data_nascimento, salario, turno FROM caixas ORDER BY id";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao pesquisar caixa no banco de dados: " + ex.getMessage(), ex);
        }
        return lista;
    }

    @Override
    public void excluir(Caixa e) {
        String sql = "DELETE FROM caixas WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, e.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir caixa (verifique se há registros ligados a ele): " + ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(long id, Caixa e) {
        String sql = "UPDATE caixas SET nome=?, cpf=?, telefone=?, email=?, data_nascimento=?, salario=?, turno=? WHERE id=?";
        try (Connection con = ConexaoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            preencher(stmt, e);
            stmt.setLong(8, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao atualizar caixa no banco de dados: " + ex.getMessage(), ex);
        }
    }
}
