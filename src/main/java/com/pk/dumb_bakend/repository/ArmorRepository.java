package com.pk.dumb_bakend.repository;

import com.pk.dumb_bakend.model.Armor;
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
public class ArmorRepository {
  private Connection databaseConnection;

  public ArmorRepository(Connection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  public Armor get(Integer id) {
    String sql = "SELECT id, name, meleeRes, rangedRes, fireRes, magicRes FROM armor WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery(sql);

      Armor armor = new Armor();
      rs.next();
      armor.setIdArmor(rs.getInt("id"));
      armor.setName(rs.getString("name"));
      armor.setMeleeRes(rs.getInt("meleeRes"));
      armor.setRangedRes(rs.getInt("rangedRes"));
      armor.setFireRes(rs.getInt("fireRes"));
      armor.setMagicRes(rs.getInt("magicRes"));
      return armor;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Armor create(Armor newArmor) {
    String sql =
        "INSERT INTO armor(id, name, meleeRes, rangedRes, fireRes, magicRes) VALUES(?,?,?,?,?,?)";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, newArmor.getIdArmor());
      preparedStatement.setString(2, newArmor.getName());
      preparedStatement.setInt(3, newArmor.getMeleeRes());
      preparedStatement.setInt(4, newArmor.getRangedRes());
      preparedStatement.setInt(5, newArmor.getFireRes());
      preparedStatement.setInt(6, newArmor.getMagicRes());
      preparedStatement.executeUpdate();
      return newArmor;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Armor update(Armor updatedArmor) {
    String sql =
        "UPDATE armor SET name = ?, meleeRes = ?, rangedRes = ?, fireRes = ?, magicRes = ? WHERE id"
            + " = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setString(1, updatedArmor.getName());
      preparedStatement.setInt(2, updatedArmor.getMeleeRes());
      preparedStatement.setInt(3, updatedArmor.getRangedRes());
      preparedStatement.setInt(4, updatedArmor.getFireRes());
      preparedStatement.setInt(5, updatedArmor.getMagicRes());
      preparedStatement.setInt(6, updatedArmor.getIdArmor());
      preparedStatement.executeUpdate();
      return updatedArmor;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public Armor delete(Integer armorToDelete) {
    String sql = "DELETE FROM armor WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      Armor deletedArmor = get(armorToDelete);
      preparedStatement.setInt(1, armorToDelete);
      preparedStatement.executeUpdate();
      return deletedArmor;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public List<Armor> getAll() {
    String sql = "SELECT id, name, meleeRes, rangedRes, fireRes, magicRes FROM armor";
    try (Statement statement = this.databaseConnection.createStatement();
        ResultSet rs = statement.executeQuery(sql); ) {

      ArrayList<Armor> armorList = new ArrayList<>();
      while (rs.next()) {
        Armor armor = new Armor();
        armor.setIdArmor(rs.getInt("id"));
        armor.setName(rs.getString("name"));
        armor.setMeleeRes(rs.getInt("meleeRes"));
        armor.setRangedRes(rs.getInt("rangedRes"));
        armor.setFireRes(rs.getInt("fireRes"));
        armor.setMagicRes(rs.getInt("magicRes"));
        armorList.add(armor);
      }
      return armorList;
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return Collections.emptyList();
  }
}
