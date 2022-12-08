package com.pk.dumb_bakend;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class JdbcService {

  private Connection connection;

  public JdbcService(
      String url,
      String armorCsvPath,
      String meleeCsvPath,
      String potionCsvPath,
      String rangedCsvPath,
      String spellCsvPath,
      String userCsvPath) {
    createConnection(url);
    createArmorDatabase(armorCsvPath);
    createMeleeDatabase(meleeCsvPath);
    createPotionDatabase(potionCsvPath);
    createRangedDatabase(rangedCsvPath);
    createSpellDatabase(spellCsvPath);
    createUserDatabase(userCsvPath);
  }

  private void createArmorDatabase(String armorCsvPath) {
    final String SQL_DROP = "DROP TABLE IF EXISTS armor";
    final String SQL_CREATE =
        "CREATE TABLE armor (id int not null, name char(64), meleeRes int, rangedRes int, fireRes"
            + " int, magicRes int)";
    final String SQL_INSERT =
        "INSERT INTO armor(id, name, meleeRes, rangedRes, fireRes, magicRes) VALUES(?,?,?,?,?,?)";
    try (Statement statement = this.connection.createStatement()) {
      statement.executeUpdate(SQL_DROP);
      statement.executeUpdate(SQL_CREATE);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    try (Reader reader = Files.newBufferedReader(Paths.get(armorCsvPath));
        CSVReader csvReader = new CSVReader(reader);
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_INSERT)) {

      String[] csvRead;
      String[] csvLine;
      while ((csvRead = csvReader.readNext()) != null) {
        csvLine = csvRead[0].split(";", 0);
        preparedStatement.setInt(1, Integer.parseInt(csvLine[0]));
        preparedStatement.setString(2, csvLine[1]);
        preparedStatement.setInt(3, Integer.parseInt(csvLine[2]));
        preparedStatement.setInt(4, Integer.parseInt(csvLine[3]));
        preparedStatement.setInt(5, Integer.parseInt(csvLine[4]));
        preparedStatement.setInt(6, Integer.parseInt(csvLine[5]));
        preparedStatement.executeUpdate();
      }

    } catch (IOException | CsvValidationException ex) {
      log.error(ex.getMessage(), ex);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void createMeleeDatabase(String meleeCsvPath) {
    final String SQL_DROP = "DROP TABLE IF EXISTS melee";
    final String SQL_CREATE =
        "CREATE TABLE melee (id int not null, name char(64), type char(64), strengthReq int, damage"
            + " int, location char(1024))";
    final String SQL_INSERT =
        "INSERT INTO melee(id, name, type, strengthReq, damage, location)" + " VALUES(?,?,?,?,?,?)";
    try (Statement statement = this.connection.createStatement()) {
      statement.executeUpdate(SQL_DROP);
      statement.executeUpdate(SQL_CREATE);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    try (Reader reader = Files.newBufferedReader(Paths.get(meleeCsvPath));
        CSVReader csvReader = new CSVReader(reader);
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_INSERT)) {

      String[] csvRead;
      String[] csvLine;
      while ((csvRead = csvReader.readNext()) != null) {
        csvLine = csvRead[0].split(";", 0);
        preparedStatement.setInt(1, Integer.parseInt(csvLine[0]));
        preparedStatement.setString(2, csvLine[1]);
        preparedStatement.setString(3, csvLine[2]);
        preparedStatement.setInt(4, Integer.parseInt(csvLine[3]));
        preparedStatement.setInt(5, Integer.parseInt(csvLine[4]));
        preparedStatement.setString(6, csvLine[5]);
        preparedStatement.executeUpdate();
      }

    } catch (IOException | CsvValidationException ex) {
      log.error(ex.getMessage(), ex);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void createPotionDatabase(String potionCsvPath) {
    final String SQL_DROP = "DROP TABLE IF EXISTS potion";
    final String SQL_CREATE =
        "CREATE TABLE potion (id int not null, name char(64), effect char(64), location"
            + " char(1024))";
    final String SQL_INSERT = "INSERT INTO potion(id, name, effect, location)" + " VALUES(?,?,?,?)";
    try (Statement statement = this.connection.createStatement()) {
      statement.executeUpdate(SQL_DROP);
      statement.executeUpdate(SQL_CREATE);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    try (Reader reader = Files.newBufferedReader(Paths.get(potionCsvPath));
        CSVReader csvReader = new CSVReader(reader);
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_INSERT)) {

      String[] csvRead;
      String[] csvLine;
      while ((csvRead = csvReader.readNext()) != null) {
        csvLine = csvRead[0].split(";", 0);
        preparedStatement.setInt(1, Integer.parseInt(csvLine[0]));
        preparedStatement.setString(2, csvLine[1]);
        preparedStatement.setString(3, csvLine[2]);
        preparedStatement.setString(4, csvLine[3]);
        preparedStatement.executeUpdate();
      }

    } catch (IOException | CsvValidationException ex) {
      log.error(ex.getMessage(), ex);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void createRangedDatabase(String rangedCsvPath) {
    final String SQL_DROP = "DROP TABLE IF EXISTS ranged";
    final String SQL_CREATE =
        "CREATE TABLE ranged (id int not null, name char(64), requirement int, damage int, location"
            + " char(1024))";
    final String SQL_INSERT =
        "INSERT INTO ranged(id, name, requirement, damage, location)" + " VALUES(?,?,?,?,?)";
    try (Statement statement = this.connection.createStatement()) {
      statement.executeUpdate(SQL_DROP);
      statement.executeUpdate(SQL_CREATE);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    try (Reader reader = Files.newBufferedReader(Paths.get(rangedCsvPath));
        CSVReader csvReader = new CSVReader(reader);
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_INSERT)) {

      String[] csvRead;
      String[] csvLine;
      while ((csvRead = csvReader.readNext()) != null) {
        csvLine = csvRead[0].split(";", 0);
        preparedStatement.setInt(1, Integer.parseInt(csvLine[0]));
        preparedStatement.setString(2, csvLine[1]);
        preparedStatement.setInt(3, Integer.parseInt(csvLine[2]));
        preparedStatement.setInt(4, Integer.parseInt(csvLine[3]));
        preparedStatement.setString(5, csvLine[4]);
        preparedStatement.executeUpdate();
      }

    } catch (IOException | CsvValidationException ex) {
      log.error(ex.getMessage(), ex);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void createSpellDatabase(String userCsvPath) {
    final String SQL_DROP = "DROP TABLE IF EXISTS spell";
    final String SQL_CREATE =
        "CREATE TABLE spell (id int not null, name char(64), effect char(64), manaCost int,"
            + " requiredLevel int, location char(1024))";
    final String SQL_INSERT =
        "INSERT INTO spell (id, name, effect, manaCost, requiredLevel, location)"
            + " VALUES(?,?,?,?,?,?)";
    try (Statement statement = this.connection.createStatement()) {
      statement.executeUpdate(SQL_DROP);
      statement.executeUpdate(SQL_CREATE);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    try (Reader reader = Files.newBufferedReader(Paths.get(userCsvPath));
        CSVReader csvReader = new CSVReader(reader);
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_INSERT)) {

      String[] csvRead;
      String[] csvLine;
      while ((csvRead = csvReader.readNext()) != null) {
        csvLine = csvRead[0].split(";", 0);
        preparedStatement.setInt(1, Integer.parseInt(csvLine[0]));
        preparedStatement.setString(2, csvLine[1]);
        preparedStatement.setString(3, csvLine[2]);
        preparedStatement.setString(4, csvLine[3]);
        preparedStatement.setInt(5, Integer.parseInt(csvLine[4]));
        preparedStatement.setString(6, csvLine[5]);
        preparedStatement.executeUpdate();
      }

    } catch (IOException | CsvValidationException ex) {
      log.error(ex.getMessage(), ex);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void createUserDatabase(String userCsvPath) {
    final String SQL_DROP = "DROP TABLE IF EXISTS user";
    final String SQL_CREATE =
        "CREATE TABLE user (idUser int not null, email char(64), nick char(64), password char(64),"
            + " role char(64))";
    final String SQL_INSERT =
        "INSERT INTO user (idUser, email, nick, password, role)" + " VALUES(?,?,?,?,?)";
    try (Statement statement = this.connection.createStatement()) {
      statement.executeUpdate(SQL_DROP);
      statement.executeUpdate(SQL_CREATE);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    try (Reader reader = Files.newBufferedReader(Paths.get(userCsvPath));
        CSVReader csvReader = new CSVReader(reader);
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_INSERT)) {

      String[] csvRead;
      String[] csvLine;
      while ((csvRead = csvReader.readNext()) != null) {
        csvLine = csvRead[0].split(";", 0);
        preparedStatement.setInt(1, Integer.parseInt(csvLine[0]));
        preparedStatement.setString(2, csvLine[1]);
        preparedStatement.setString(3, csvLine[2]);
        preparedStatement.setString(4, csvLine[3]);
        preparedStatement.setString(5, csvLine[4]);
        preparedStatement.executeUpdate();
      }
    } catch (IOException | CsvValidationException ex) {
      log.error(ex.getMessage(), ex);
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void createConnection(String url) {
    this.connection = null;
    try {
      this.connection = DriverManager.getConnection(url);
      log.info("Connection to database established");
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
  }

  public void closeConnection() {
    try {
      this.connection.close();
    } catch (SQLException ex) {
      log.error(ex.getMessage(), ex);
    }
  }
}
