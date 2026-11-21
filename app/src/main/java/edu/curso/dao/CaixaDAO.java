package edu.curso.dao;

import java.util.List;
import edu.curso.entidade.Caixa;

public interface CaixaDAO {
    Caixa salvar(Caixa e);
    List<Caixa> pesquisarTodos();
    void excluir(Caixa e);
    void atualizar(long id, Caixa e);
}
