package com.skuralll.depositvault.db;

import com.skuralll.depositvault.DepositVault;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

  public final static String DB_VERSION = "1";

  // Database connection information
  private String host;
  private int port;
  private String user;
  private String password;
  private String database;

  // connection
  private Connection connection;

  public Database(String host, int port, String user, String password, String database) {
    this.host = host;
    this.port = port;
    this.user = user;
    this.password = password;
    this.database = database;
    this.connection = null;
  }

  // open sql connection
  public boolean connect() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      this.connection = DriverManager.getConnection(
          "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user,
          password);
      createTables();
      return true;
    } catch (SQLException | ClassNotFoundException e) {
      DepositVault.getInstance().getLogger().severe("Could not connect to MySQL server! because: "
          + e.getMessage());
    }
    return false;
  }

  // create tables if not exist
  private void createTables() throws SQLException {
    Statement statement = connection.createStatement();
    // db meta table
    statement.executeUpdate(
        "CREATE TABLE IF NOT EXISTS `meta` (" +
            "`key`   TEXT NOT NULL," +
            "`value` TEXT NOT NULL" +
            ")"
    );
    statement.executeUpdate(
        "INSERT INTO `meta` SELECT 'dbversion','" + DB_VERSION + "' "
            + " WHERE NOT EXISTS (SELECT `key` FROM `meta` WHERE `key` = 'dbversion')");
  }

}
