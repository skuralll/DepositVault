package com.skuralll.depositvault.cache;

public class CacheStore {

  private CheckCache check_cache;

  public CacheStore() {
    check_cache = new CheckCache();
  }

  public CheckCache getCheckCache() {
    return check_cache;
  }
  
}
