package com.company.dal;

import com.company.model.Cat;
import com.company.util.ConnectionPool;
import com.company.util.ObjectExtractionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatDAL implements CrudDAL<Long, Cat> {
    public static final CatDAL instance = new CatDAL();

    private final ConnectionPool connectionPool;


    private CatDAL() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish connection with database");
        }
    }


    public Long create(final Cat cat) {
        Connection connection = null;
        try {
            String sqlStatement = "INSERT INTO cats (name, age) VALUES(?,?)";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cat.getName());
            preparedStatement.setFloat(2, cat.getAge());
            preparedStatement.executeUpdate();
            ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                throw new RuntimeException("Failed to retrieve auto-incremented id");
            }

            return generatedKeysResult.getLong(1);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create a new book");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Cat read(Long id) {
        Connection connection = null;
        try {
            String sqlStatement = "SELECT * FROM cats WHERE idcats = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtil.resultToCat(result);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to return cat");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void update(Cat cat) {
        Connection connection = null;
        try {
            String sqlStatement = "UPDATE cats " +
                    "SET " +
                    "name = ?, " +
                    "age = ? " +
                    "WHERE idcats = ? ;";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cat.getName());
            preparedStatement.setFloat(2, cat.getAge());
            preparedStatement.setLong(3, cat.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("failed to update the cat");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = null;
        try {
            String sqlStatement = "DELETE FROM cats WHERE idcats = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete a cat");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Cat> readAll() {
        Connection connection = null;
        try {
            String sqlStatement = "SELECT * FROM cats";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            ResultSet result = preparedStatement.executeQuery();
            List<Cat> cats = new ArrayList<>();

            while (result.next()) {
                cats.add(ObjectExtractionUtil.resultToCat(result));
            }

            return cats;
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to return a cats list");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}