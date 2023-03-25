package com.skuralll.depositvault.db;

import com.skuralll.depositvault.DepositVault;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

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
      return true;
    } catch (SQLException | ClassNotFoundException e) {
      DepositVault.getInstance().getLogger().severe("Could not connect to MySQL server! because: "
          + e.getMessage());
    }
    return false;
  }

}