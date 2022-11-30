package com.pk.dumb_bakend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class JdbcService {

  private Connection connection;

  private JdbcService() {}

  public void createConnection(String url) {
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
