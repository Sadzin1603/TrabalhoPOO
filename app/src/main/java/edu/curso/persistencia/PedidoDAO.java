package edu.curso.persistencia;

import java.util.List;
import edu.curso.entidade.Pedido;

public interface PedidoDAO {
    Pedido salvar(Pedido e);
    List<Pedido> pesquisarTodos();
    void excluir(Pedido e);
    void atualizar(long id, Pedido e);
}
