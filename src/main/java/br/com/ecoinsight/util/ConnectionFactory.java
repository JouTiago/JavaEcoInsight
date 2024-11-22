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
    private static final String USER = "RM556071";
    private static final String PASSWORD = "090298";

    private ConnectionFactory() {}



    public static ConnectionFactory realizarConexao() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do Oracle não encontrado.", e);
        }

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
