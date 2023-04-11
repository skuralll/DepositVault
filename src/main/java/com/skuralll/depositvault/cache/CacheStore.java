package com.skuralll.depositvault.cache;

import java.sql.Time;
import java.util.UUID;

public class CacheStore {

  private TimerCache<UUID> check_cache;
  private TimerCache<UUID> unlock_cache;
  private TimerCache<UUID> ui_cache;
  private NormalCache<UUID, Time> lock_cache;
  private NormalCache<UUID, Time> extend_cache;

  public CacheStore() {
    check_cache = new TimerCache<UUID>();
    unlock_cache = new TimerCache<UUID>();
    ui_cache = new TimerCache<UUID>();
    lock_cache = new NormalCache<UUID, Time>();
    extend_cache = new NormalCache<UUID, Time>();
  }

  public TimerCache<UUID> getCheckCommandCache() {
    return check_cache;
  }

  public TimerCache<UUID> getUnlockCommandCache() {
    return unlock_cache;
  }

  public TimerCache<UUID> getUICommandCache() {
    return ui_cache;
  }

  public NormalCache<UUID, Time> getLockCommandCache() {
    return lock_cache;
  }

  public NormalCache<UUID, Time> getExtendCommandCache() {
    return extend_cache;
  }

}
