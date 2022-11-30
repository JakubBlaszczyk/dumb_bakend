package com.pk.dumb_bakend.repository;

import com.pk.dumb_bakend.model.Spell;
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
public class SpellRepository {
  private Connection databaseConnection;

  public SpellRepository(Connection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  public Spell get(Integer id) {
    String sql =
        "SELECT id, name, effect, manaCost, requiredLevel, location FROM spell WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery(sql);

      Spell spell = new Spell();
      rs.next();
      spell.setIdSpell(rs.getInt("id"));
      spell.setName(rs.getString("name"));
      spell.setEffect(rs.getString("effect"));
      spell.setManaCost(rs.getString("manaCost"));
      spell.setRequiredLevel(rs.getInt("requiredLevel"));
      spell.setLocation(rs.getString("location"));
      return spell;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  public Spell create(Spell newSpell) {
    String sql =
        "INSERT INTO spell(id, name, effect, manaCost, requiredLevel, location)"
            + " VALUES(?,?,?,?,?,?)";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setInt(1, newSpell.getIdSpell());
      preparedStatement.setString(2, newSpell.getName());
      preparedStatement.setString(3, newSpell.getEffect());
      preparedStatement.setString(4, newSpell.getManaCost());
      preparedStatement.setInt(5, newSpell.getRequiredLevel());
      preparedStatement.setString(6, newSpell.getLocation());

      preparedStatement.executeUpdate();
      return newSpell;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  public Spell update(Spell updatedSpell) {
    String sql =
        "UPDATE spell SET name = ?, effect = ?, manaCost = ?, requiredLevel = ?, location = ? WHERE"
            + " id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      preparedStatement.setString(1, updatedSpell.getName());
      preparedStatement.setString(2, updatedSpell.getEffect());
      preparedStatement.setString(3, updatedSpell.getManaCost());
      preparedStatement.setInt(4, updatedSpell.getRequiredLevel());
      preparedStatement.setString(5, updatedSpell.getLocation());
      preparedStatement.setInt(6, updatedSpell.getIdSpell());
      preparedStatement.executeUpdate();
      return updatedSpell;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  public Spell delete(Integer spellToDelete) {
    String sql = "DELETE FROM spell WHERE id = ?";
    try (PreparedStatement preparedStatement = this.databaseConnection.prepareStatement(sql)) {
      Spell deletedSpell = get(spellToDelete);
      preparedStatement.setInt(1, spellToDelete);
      preparedStatement.executeUpdate();
      return deletedSpell;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  public List<Spell> getAll() {
    String sql = "SELECT id, name, effect, manaCost, requiredLevel, location FROM spell";
    try (Statement statement = this.databaseConnection.createStatement();
        ResultSet rs = statement.executeQuery(sql); ) {

      ArrayList<Spell> spellList = new ArrayList<>();
      while (rs.next()) {
        Spell spell = new Spell();
        spell.setIdSpell(rs.getInt("id"));
        spell.setName(rs.getString("name"));
        spell.setEffect(rs.getString("effect"));
        spell.setManaCost(rs.getString("manaCost"));
        spell.setRequiredLevel(rs.getInt("requiredLevel"));
        spell.setLocation(rs.getString("location"));
        spellList.add(spell);
      }
      return spellList;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return Collections.emptyList();
  }
}
