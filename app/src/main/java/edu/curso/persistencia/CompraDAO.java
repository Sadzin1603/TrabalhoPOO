package edu.curso.persistencia;

import java.util.List;
import edu.curso.entidade.Compra;

public interface CompraDAO {
    Compra salvar(Compra e);
    List<Compra> pesquisarTodos();
    void excluir(Compra e);
    void atualizar(long id, Compra e);
}
