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

  public JdbcService(String url, String meleeCsvPath) {
    createConnection(url);
    createMeleeDatabase(meleeCsvPath);
  }

  private void createMeleeDatabase(String meleeCsvPath) {
    final String SQL_DROP = "DROP TABLE IF EXISTS melee";
    final String SQL_CREATE =
        "CREATE TABLE melee (id int not null, name char(64), type char(64), strengthReq int, damage"
            + " int, location char(1024))";
    final String SQL_INSERT =
        "INSERT INTO melee VALUES(id, name, type, strengthReq, damage, location)"
            + " VALUES(?,?,?,?,?,?)";
    try (Statement statement = this.connection.createStatement()) {
      statement.executeUpdate(SQL_DROP);
      statement.executeUpdate(SQL_CREATE);
    } catch (SQLException e) {
      log.error(e.getMessage());
    }

    try (Reader reader = Files.newBufferedReader(Paths.get(meleeCsvPath));
        CSVReader csvReader = new CSVReader(reader);
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_INSERT)) {

      String[] csvLine;
      while ((csvLine = csvReader.readNext()) != null) {
        preparedStatement.setInt(1, Integer.parseInt(csvLine[0]));
        preparedStatement.setString(2, csvLine[1]);
        preparedStatement.setString(3, csvLine[2]);
        preparedStatement.setInt(4, Integer.parseInt(csvLine[3]));
        preparedStatement.setInt(5, Integer.parseInt(csvLine[4]));
        preparedStatement.setString(6, csvLine[5]);
        preparedStatement.executeUpdate();
      }

    } catch (IOException | CsvValidationException ex) {
      log.error(ex.getMessage());
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
  }

  private void createConnection(String url) {
    this.connection = null;
    try {
      this.connection = DriverManager.getConnection(url);
      log.info("Connection to database established");
    } catch (SQLException e) {
      log.info(e.getMessage());
    } finally {
      try {
        if (this.connection != null) {
          this.connection.close();
        }
      } catch (SQLException ex) {
        log.error(ex.getMessage(), ex);
      }
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
