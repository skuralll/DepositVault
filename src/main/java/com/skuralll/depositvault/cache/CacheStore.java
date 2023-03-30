package com.skuralll.depositvault.cache;

public class CacheStore {

  private CheckCommandCache check_cache;
  private LockCommandCache lock_cache;

  public CacheStore() {
    check_cache = new CheckCommandCache();
    lock_cache = new LockCommandCache();
  }

  public CheckCommandCache getCheckCommandCache() {
    return check_cache;
  }

  public LockCommandCache getLockCommandCache() {
    return lock_cache;
  }

}
