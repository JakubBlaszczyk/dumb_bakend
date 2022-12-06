package com.pk.dumb_bakend.repository;

import com.pk.dumb_bakend.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRepository {
  private Connection databaseConnection;

  public UserRepository(Connection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  public User getByEmail(String email) {
    String sql = "SELECT idUser, email, nick, password, role FROM user WHERE email = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setString(1, email);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.isClosed()) {
        throw new RuntimeException("No data returned");
      }
      User user = new User();
      rs.next();
      user.setIdUser(rs.getInt("idUser"));
      user.setEmail(rs.getString("email"));
      user.setNick(rs.getString("nick"));
      user.setPassword(rs.getString("password"));
      user.setRole(rs.getString("role"));
      return user;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public User get(Integer idUser) {
    String sql = "SELECT idUser, email, nick, password, role FROM user WHERE idUser = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, idUser);
      ResultSet rs = preparedStatement.executeQuery();

      User user = new User();

      if (rs.isClosed()) {
        throw new RuntimeException("No data returned");
      }
      rs.next();
      user.setIdUser(rs.getInt("idUser"));
      user.setEmail(rs.getString("email"));
      user.setNick(rs.getString("nick"));
      user.setPassword(rs.getString("password"));
      user.setRole(rs.getString("role"));
      return user;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public User create(User newUser) {
    String sql = "INSERT INTO user(idUser, email, nick, password, role)" + " VALUES(?,?,?,?,?)";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, newUser.getIdUser());
      preparedStatement.setString(2, newUser.getEmail());
      preparedStatement.setString(3, newUser.getNick());
      preparedStatement.setString(4, newUser.getPassword());
      preparedStatement.setString(5, newUser.getRole());

      preparedStatement.executeUpdate();
      return newUser;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public User update(User updatedUser) {
    String sql = "UPDATE spell SET email = ?, nick = ?, password = ?, role = ? WHERE" + " idUser = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setString(1, updatedUser.getEmail());
      preparedStatement.setString(2, updatedUser.getNick());
      preparedStatement.setString(3, updatedUser.getPassword());
      preparedStatement.setString(4, updatedUser.getRole());
      preparedStatement.setInt(5, updatedUser.getIdUser());
      preparedStatement.executeUpdate();
      return updatedUser;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public User delete(Integer userToDelete) {
    String sql = "DELETE FROM user WHERE idUser = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      User deletedUser = get(userToDelete);
      preparedStatement.setInt(1, userToDelete);
      preparedStatement.executeUpdate();
      return deletedUser;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public List<User> getAll() {
    String sql = "SELECT idUser, email, nick, password, role FROM user";
    try (Statement statement = this.databaseConnection.createStatement();
        ResultSet rs = statement.executeQuery(sql); ) {

      if (rs.isClosed()) {
        throw new RuntimeException("No data returned");
      }

      ArrayList<User> userList = new ArrayList<>();
      while (rs.next()) {
        User user = new User();
        user.setIdUser(rs.getInt("idUser"));
        user.setEmail(rs.getString("email"));
        user.setNick(rs.getString("nick"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        userList.add(user);
      }
      return userList;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return Collections.emptyList();
  }
}
