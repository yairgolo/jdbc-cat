package com.company.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

    private static final int NUMBER_OF_CONNECTIONS = 5;
    private static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        System.out.println("Created new connection pool");
        openAllConnections();
    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    private void openAllConnections() throws SQLException {
        for (int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
            final Connection connection = DriverManager.getConnection(DbConfig.sqlUrl, DbConfig.sqlUser, DbConfig.sqlPassword);
            connections.push(connection);
        }
    }

    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUMBER_OF_CONNECTIONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (connections){
            if (connections.isEmpty()){
                System.out.println("waiting for an available connection");
            }
            while (connections.isEmpty()){
                connections.wait();
            }
            return connections.pop();
        }
    }

    public void returnConnection(final Connection connection) {
        synchronized (connections) {
            if (connection == null) {
                System.out.println("Attempt to return null connection terminated");
                return;
            }
            connections.push(connection);
            //System.out.println("return a connection, now there are " + connections.size());
            connections.notifyAll();
        }
    }
}
