package com.skuralll.depositvault.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CheckCache {

  private Cache<UUID, Long> cache;

  public CheckCache() {
    cache = CacheBuilder.newBuilder()
        .maximumSize(100)
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .build();
  }

  public void put(UUID uuid, Long time) {
    cache.put(uuid, time);
  }

}
