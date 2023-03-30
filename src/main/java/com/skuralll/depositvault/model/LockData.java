package com.skuralll.depositvault.model;

public class LockData {

  private int lock_id;
  private int user_id;
  private String world_name;
  private int x;
  private int y;
  private int z;
  private DepositData deposit_data;

  public LockData(
      int lock_id,
      int user_id,
      String world_name,
      int x,
      int y,
      int z,
      DepositData deposit_data
  ) {
    this.lock_id = lock_id;
    this.user_id = user_id;
    this.world_name = world_name;
    this.x = x;
    this.y = y;
    this.z = z;
    this.deposit_data = deposit_data;
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
        ", deposit_data=" + deposit_data.toString() +
        '}';
  }

  public DepositData getDepositData() {
    return deposit_data;
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
