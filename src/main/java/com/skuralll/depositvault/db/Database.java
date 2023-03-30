package com.skuralll.depositvault.db;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.model.DepositData;
import com.skuralll.depositvault.model.LockData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.CheckForNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
            "`key`   VARCHAR(32) PRIMARY KEY," +
            "`value` TEXT" +
            ")"
    );
    statement.executeUpdate(
        "INSERT INTO `meta` SELECT 'dbversion','" + DB_VERSION + "' "
            + " WHERE NOT EXISTS (SELECT `key` FROM `meta` WHERE `key` = 'dbversion')");

    // user table
    statement.executeUpdate(
        "CREATE TABLE IF NOT EXISTS `user` (" +
            "`user_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
            "`uuid` CHAR(36) UNIQUE," +
            "`name` VARCHAR(16)" +
            ")"
    );

    // lock-data table
    statement.executeUpdate(
        "CREATE TABLE IF NOT EXISTS `locks` (" +
            "`lock_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
            "`user_id` INT," +
            "`world` VARCHAR(50)," +
            "`x` INT," +
            "`y` INT," +
            "`z` INT," +
            "`interval` INT," +
            "`payment` DOUBLE," +
            "`deposit` DOUBLE" +
            ")"
    );
  }

  // update user data
  public boolean createUserData(Player player) {
    try {
      Statement statement = connection.createStatement();
      // update user data
      PreparedStatement ps = connection.prepareStatement(
          "INSERT INTO `user` (`uuid`, `name`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `name` = ?");
      ps.setString(1, player.getUniqueId().toString());
      ps.setString(2, player.getName());
      ps.setString(3, player.getName());
      ps.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @CheckForNull
  public LockData getLockData(Location location) {
    try {
      Statement statement = connection.createStatement();
      PreparedStatement ps = connection.prepareStatement(
          "SELECT * FROM `locks` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?");
      ps.setString(1, location.getWorld().getName());
      ps.setInt(2, location.getBlockX());
      ps.setInt(3, location.getBlockY());
      ps.setInt(4, location.getBlockZ());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        DepositData deposi_data = new DepositData(
            rs.getInt("interval"),
            rs.getDouble("payment"),
            rs.getDouble("deposit"),
            0d
        );
        return new LockData(
            rs.getInt("lock_id"),
            rs.getInt("user_id"),
            rs.getString("world"),
            rs.getInt("x"),
            rs.getInt("y"),
            rs.getInt("z"),
            deposi_data
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean setLockData(Player player, Location location, DepositData depositData) {
    try {
      Statement statement = connection.createStatement();
      createUserData(player);
      // TODO
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}
