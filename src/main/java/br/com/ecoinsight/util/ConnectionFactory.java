package br.com.ecoinsight.util;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;

@Singleton
public class ConnectionFactory {
    private static ConnectionFactory conexao;
    private final String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private final String user = "RM555698";
    private final String password = "fiap24";

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

    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
