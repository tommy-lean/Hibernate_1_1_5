package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users(" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(45)," +
                    "lastName VARCHAR(45)," +
                    "age TINYINT )");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prStatement = connection.prepareStatement
                ("INSERT INTO users(name, lastName, age) VALUES (?, ?,?)")) {
            prStatement.setString(1, name);
            prStatement.setString(2, lastName);
            prStatement.setByte(3, age);
            prStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement prStatement = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            prStatement.setLong(1, id);
            prStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (PreparedStatement prStatement = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet set = prStatement.executeQuery();
            while (set.next()) {
                usersList.add(new User(set.getString("name"),
                        set.getString("lastName"),
                        set.getByte("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
