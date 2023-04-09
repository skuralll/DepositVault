package com.skuralll.depositvault.db;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.model.LockData;
import java.sql.Connection;
import java.sql.Date;
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
            "`expire` DATETIME" +
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
  public Integer getUserId(Player player) {
    try {
      Statement statement = connection.createStatement();
      PreparedStatement ps = connection.prepareStatement(
          "SELECT `user_id` FROM `user` WHERE `uuid` = ?");
      ps.setString(1, player.getUniqueId().toString());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        return rs.getInt("user_id");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @CheckForNull
  public String getUserName(Integer user_id) {
    try {
      Statement statement = connection.createStatement();
      PreparedStatement ps = connection.prepareStatement(
          "SELECT `name` FROM `user` WHERE `user_id` = ?");
      ps.setInt(1, user_id);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        return rs.getString("user_id");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
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
        return new LockData(
            rs.getInt("lock_id"),
            rs.getInt("user_id"),
            rs.getString("world"),
            rs.getInt("x"),
            rs.getInt("y"),
            rs.getInt("z"),
            rs.getDate("expire")
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean setLockData(Player player, Location location, Date expire) {
    // check user data and lock data
    createUserData(player);
    Integer user_id = getUserId(player);
    if (user_id == null)
      return false;
    if (getLockData(location) != null)
      return false;
    // query
    try {
      Statement statement = connection.createStatement();
      PreparedStatement ps = connection.prepareStatement(
          "INSERT INTO `locks` (`user_id`, `world`, `x`, `y`, `z`, `expire`) VALUES (?, ?, ?, ?, ?, ?)");
      ps.setInt(1, user_id);
      ps.setString(2, location.getWorld().getName());
      ps.setInt(3, location.getBlockX());
      ps.setInt(4, location.getBlockY());
      ps.setInt(5, location.getBlockZ());
      ps.setDate(6, expire);
      ps.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  // remove lock data
  public boolean removeLockData(Location location) {
    if (getLockData(location) == null)
      return false;
    try {
      Statement statement = connection.createStatement();
      PreparedStatement ps = connection.prepareStatement(
          "DELETE FROM `locks` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?");
      ps.setString(1, location.getWorld().getName());
      ps.setInt(2, location.getBlockX());
      ps.setInt(3, location.getBlockY());
      ps.setInt(4, location.getBlockZ());
      ps.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}
