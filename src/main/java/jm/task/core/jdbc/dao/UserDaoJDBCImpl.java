package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id integer not null AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100), " +
                    "lastname VARCHAR(100), " +
                    "age integer)");
            System.out.println("Таблица создана");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE if exists users");
            System.out.println("Таблица удалена");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastname, age) VALUES(?,?,?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users where id";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("User удален");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age from users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUser.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUser;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM test.users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить");
        }
    }
}

