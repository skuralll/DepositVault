package com.skuralll.depositvault.model;

import java.sql.Date;

public class LockData {

  private int lock_id;
  private int user_id;
  private String world_name;
  private int x;
  private int y;
  private int z;
  private Date expire;

  public LockData(
      int lock_id,
      int user_id,
      String world_name,
      int x,
      int y,
      int z,
      Date expire_date
  ) {
    this.lock_id = lock_id;
    this.user_id = user_id;
    this.world_name = world_name;
    this.x = x;
    this.y = y;
    this.z = z;
    this.expire = expire_date;
  }

  @Override
  public String toString() {
    return "LockData{" +
        "lock_id=" + lock_id +
        ", user_id=" + user_id +
        ", world_name='" + world_name + '\'' +
        ", x=" + x +
        ", y=" + y +
        ", z=" + z +
        ", expire=" + expire.toString() +
        '}';
  }

  public Date getExpireDate() {
    return expire;
  }

  public int getLockId() {
    return lock_id;
  }

  public int getUserId() {
    return user_id;
  }

  public String getWorldName() {
    return world_name;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }
}
