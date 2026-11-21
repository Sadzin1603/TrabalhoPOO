package edu.curso.persistencia;

import java.util.List;
import edu.curso.entidade.Fornecedor;

public interface FornecedorDAO {
    Fornecedor salvar(Fornecedor e);
    List<Fornecedor> pesquisarTodos();
    void excluir(Fornecedor e);
    void atualizar(long id, Fornecedor e);
}
