package com.skuralll.depositvault.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckForNull;

public class CheckCache {

  private Cache<UUID, Long> cache;

  public CheckCache() {
    cache = CacheBuilder.newBuilder()
        .maximumSize(100)
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .build();
  }

  public void put(UUID uuid, Long time) {
    cache.put(uuid, System.currentTimeMillis() + time);
  }

  @CheckForNull
  public Long get(UUID uuid) {
    return cache.getIfPresent(uuid);
  }

  public boolean check(UUID uuid) {
    Long time = get(uuid);
    if (time == null)
      return false;
    if (time <= System.currentTimeMillis())
      return false;
    cache.invalidate(uuid);
    return true;
  }

}
