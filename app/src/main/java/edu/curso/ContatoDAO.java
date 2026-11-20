package edu.curso;

import java.util.List;

public interface ContatoDAO { 
    Contato salvar(Contato c);
    public List<Contato> pesquisarTodos();
    public void excluir(Contato c);
    public void atualizar(long id, Contato c);
}