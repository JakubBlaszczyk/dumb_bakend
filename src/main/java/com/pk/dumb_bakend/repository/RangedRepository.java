package com.pk.dumb_bakend.repository;

import com.pk.dumb_bakend.model.Ranged;
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
public class RangedRepository {
  private Connection databaseConnection;

  public RangedRepository(Connection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  public Ranged get(Integer id) {
    String sql = "SELECT id, name, requirement, damage, location FROM ranged WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery(sql);

      Ranged ranged = new Ranged();
      rs.next();
      ranged.setIdWeapon(rs.getInt("id"));
      ranged.setName(rs.getString("name"));
      ranged.setRequirement(rs.getInt("requirement"));
      ranged.setDamage(rs.getInt("damage"));
      ranged.setLocation(rs.getString("location"));
      return ranged;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Ranged create(Ranged newRanged) {
    String sql = "INSERT INTO ranged(id, name, requirement, damage, location) VALUES(?,?,?,?,?)";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, newRanged.getIdWeapon());
      preparedStatement.setString(2, newRanged.getName());
      preparedStatement.setInt(3, newRanged.getRequirement());
      preparedStatement.setInt(4, newRanged.getDamage());
      preparedStatement.setString(5, newRanged.getLocation());

      preparedStatement.executeUpdate();
      return newRanged;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Ranged update(Ranged updatedRanged) {
    String sql =
        "UPDATE ranged SET name = ?, requirement = ?, damage = ?, location = ? WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setString(1, updatedRanged.getName());
      preparedStatement.setInt(2, updatedRanged.getRequirement());
      preparedStatement.setInt(3, updatedRanged.getDamage());
      preparedStatement.setString(4, updatedRanged.getLocation());
      preparedStatement.setInt(5, updatedRanged.getIdWeapon());
      preparedStatement.executeUpdate();
      return updatedRanged;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Ranged delete(Integer rangedToDelete) {
    String sql = "DELETE FROM ranged WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      Ranged deletedRanged = get(rangedToDelete);
      preparedStatement.setInt(1, rangedToDelete);
      preparedStatement.executeUpdate();
      return deletedRanged;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public List<Ranged> getAll() {
    String sql = "SELECT id, name, requirement, damage, location FROM ranged";
    try (Statement statement = this.databaseConnection.createStatement();
        ResultSet rs = statement.executeQuery(sql); ) {

      ArrayList<Ranged> rangedList = new ArrayList<>();
      while (rs.next()) {
        Ranged ranged = new Ranged();
        ranged.setIdWeapon(rs.getInt("id"));
        ranged.setName(rs.getString("name"));
        ranged.setRequirement(rs.getInt("requirement"));
        ranged.setDamage(rs.getInt("damage"));
        ranged.setLocation(rs.getString("location"));
        rangedList.add(ranged);
      }
      return rangedList;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return Collections.emptyList();
  }
}
