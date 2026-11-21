package edu.curso.persistencia;

import java.util.List;
import edu.curso.entidade.Cliente;

public interface ClienteDAO {
    Cliente salvar(Cliente e);
    List<Cliente> pesquisarTodos();
    void excluir(Cliente e);
    void atualizar(long id, Cliente e);
}
