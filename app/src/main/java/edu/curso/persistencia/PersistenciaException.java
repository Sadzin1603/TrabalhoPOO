package edu.curso.persistencia;

public class PersistenciaException extends RuntimeException {
    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
