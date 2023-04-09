package com.skuralll.depositvault.config;

public enum LockUnit {
  DAY("d"),
  HOUR("h"),
  MINUTE("m"),
  SECOND("s");

  private final String name;

  LockUnit(String ch) {
    this.name = ch;
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
}
