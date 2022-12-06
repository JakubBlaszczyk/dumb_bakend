package com.pk.dumb_bakend.repository;

import com.pk.dumb_bakend.model.Melee;
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
public class MeleeRepository {
  private Connection databaseConnection;

  public MeleeRepository(Connection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  public Melee get(Integer id) {
    String sql = "SELECT id, name, type, strengthReq, damage, location FROM melee WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.isClosed()) {
        throw new RuntimeException("No data returned");
      }

      Melee melee = new Melee();
      rs.next();
      melee.setIdWeapon(rs.getInt("id"));
      melee.setName(rs.getString("name"));
      melee.setType(rs.getString("type"));
      melee.setStrengthReq(rs.getInt("strengthReq"));
      melee.setDamage(rs.getInt("damage"));
      melee.setLocation(rs.getString("location"));
      return melee;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Melee create(Melee newMelee) {
    String sql =
        "INSERT INTO melee(id, name, type, strengthReq, damage, location) VALUES(?,?,?,?,?,?)";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, newMelee.getIdWeapon());
      preparedStatement.setString(2, newMelee.getName());
      preparedStatement.setString(3, newMelee.getType());
      preparedStatement.setInt(4, newMelee.getStrengthReq());
      preparedStatement.setInt(5, newMelee.getDamage());
      preparedStatement.setString(6, newMelee.getLocation());
      preparedStatement.executeUpdate();
      return newMelee;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Melee update(Melee updatedMelee) {
    String sql =
        "UPDATE melee SET name = ?, type = ?, strengthReq = ?, damage = ?, location = ? WHERE id ="
            + " ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setString(1, updatedMelee.getName());
      preparedStatement.setString(2, updatedMelee.getType());
      preparedStatement.setInt(3, updatedMelee.getStrengthReq());
      preparedStatement.setInt(4, updatedMelee.getDamage());
      preparedStatement.setString(5, updatedMelee.getLocation());
      preparedStatement.setInt(6, updatedMelee.getIdWeapon());
      preparedStatement.executeUpdate();
      return updatedMelee;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Melee delete(Integer meleeToDelete) {
    String sql = "DELETE FROM melee WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      Melee deletedMelee = get(meleeToDelete);
      preparedStatement.setInt(1, meleeToDelete);
      preparedStatement.executeUpdate();
      return deletedMelee;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public List<Melee> getAll() {
    String sql = "SELECT id, name, type, strengthReq, damage, location FROM melee";
    try (Statement statement = this.databaseConnection.createStatement();
        ResultSet rs = statement.executeQuery(sql); ) {

      ArrayList<Melee> meleeList = new ArrayList<>();
      while (rs.next()) {
        Melee melee = new Melee();
        melee.setIdWeapon(rs.getInt("id"));
        melee.setName(rs.getString("name"));
        melee.setType(rs.getString("type"));
        melee.setStrengthReq(rs.getInt("strengthReq"));
        melee.setDamage(rs.getInt("damage"));
        melee.setLocation(rs.getString("location"));
        meleeList.add(melee);
      }
      return meleeList;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return Collections.emptyList();
  }
}
