package edu.curso.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    private static final String URL = "jdbc:mariadb://localhost:3306/gestao_mercado";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456";

    private ConexaoFactory() {
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new PersistenciaException("Driver do MariaDB não encontrado", e);
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao conectar no banco de dados: " + e.getMessage(), e);
        }
    }
}
