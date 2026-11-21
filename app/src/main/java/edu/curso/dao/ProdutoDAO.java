package edu.curso.dao;

import java.util.List;
import edu.curso.entidade.Produto;

public interface ProdutoDAO {
    Produto salvar(Produto e);
    List<Produto> pesquisarTodos();
    void excluir(Produto e);
    void atualizar(long id, Produto e);
}
