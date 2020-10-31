package br.com.pedidos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Regis Gaboardi (@gmail.com)
 * Provided with Love and IntelliJ IDEA for SistemaDePedidosWeb.
 * 09/10/2020
 */

public class ConnectionFactory {

    public Connection getConnection() throws SQLException {
        try {
            String timezone = "useTimezone=true&serverTimezone=America/Sao_Paulo";
            return DriverManager.getConnection(
                    "jdbc:mysql://35.198.31.4:3306/pedinte?",
                    "Amsterdam",
                    ".Netherlands#");
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getMessage());
        }
    }
}
