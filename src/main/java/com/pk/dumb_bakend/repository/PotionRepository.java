package com.pk.dumb_bakend.repository;

import com.pk.dumb_bakend.model.Potion;
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
public class PotionRepository {
  private Connection databaseConnection;

  public PotionRepository(Connection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  public Potion get(Integer id) {
    String sql = "SELECT id, name, effect, location FROM potions WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery(sql);

      Potion potion = new Potion();
      rs.next();
      potion.setIdPotion(rs.getInt("id"));
      potion.setName(rs.getString("name"));
      potion.setEffect(rs.getString("effect"));
      potion.setName(rs.getString("name"));
      potion.setLocation(rs.getString("location"));
      return potion;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Potion create(Potion newPotion) {
    String sql = "INSERT INTO potions(id, name, effect, location) VALUES(?,?,?,?)";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, newPotion.getIdPotion());
      preparedStatement.setString(2, newPotion.getName());
      preparedStatement.setString(3, newPotion.getEffect());
      preparedStatement.setString(4, newPotion.getLocation());
      preparedStatement.executeUpdate();
      return newPotion;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Potion update(Potion updatedPotion) {
    String sql = "UPDATE potions SET name = ?, effect = ?, location = ? WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setString(1, updatedPotion.getName());
      preparedStatement.setString(2, updatedPotion.getEffect());
      preparedStatement.setString(3, updatedPotion.getLocation());
      preparedStatement.setInt(4, updatedPotion.getIdPotion());
      preparedStatement.executeUpdate();
      return updatedPotion;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Potion delete(Integer potionToDelete) {
    String sql = "DELETE FROM potions WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      Potion deletedPotion = get(potionToDelete);
      preparedStatement.setInt(1, potionToDelete);
      preparedStatement.executeUpdate();
      return deletedPotion;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public List<Potion> getAll() {
    String sql = "SELECT id, name, effect, location FROM potions";
    try (Statement statement = this.databaseConnection.createStatement();
        ResultSet rs = statement.executeQuery(sql); ) {

      ArrayList<Potion> potionList = new ArrayList<>();
      while (rs.next()) {
        Potion potion = new Potion();
        potion.setIdPotion(rs.getInt("id"));
        potion.setName(rs.getString("name"));
        potion.setEffect(rs.getString("effect"));
        potion.setLocation(rs.getString("location"));
        potionList.add(potion);
      }
      return potionList;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return Collections.emptyList();
  }
}
