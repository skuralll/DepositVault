package com.skuralll.depositvault.model;

public enum LockUnit {
  DAY("d", 86400000),
  HOUR("h", 3600000),
  MINUTE("m", 60000),
  SECOND("s", 1000);

  private final String name;
  private final long millis;

  LockUnit(String ch, int millis) {
    this.name = ch;
    this.millis = millis;
  }

  // get from name
  public static LockUnit fromChar(String ch) {
    for (LockUnit unit : values()) {
      if (unit.name.equals(ch)) {
        return unit;
      }
    }
    return null;
  }

  // get char
  public String getName() {
    return name;
  }

  // get mills
  public long getMillis() {
    return millis;
  }

}
