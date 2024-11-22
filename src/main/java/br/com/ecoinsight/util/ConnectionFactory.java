package br.com.ecoinsight.util;

import br.com.ecoinsight.exception.DatabaseException;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class ConnectionFactory {
    private static volatile ConnectionFactory conexao;
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "RM555698";
    private static final String PASSWORD = "fiap24";

    private ConnectionFactory() {}

    public static ConnectionFactory realizarConexao() {
        if (conexao == null) {
            synchronized (ConnectionFactory.class) {
                if (conexao == null) {
                    conexao = new ConnectionFactory();
                }
            }
        }
        return conexao;
    }

    public Connection getConnection() throws DatabaseException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao conectar ao banco de dados.", e);
        }
    }
}
