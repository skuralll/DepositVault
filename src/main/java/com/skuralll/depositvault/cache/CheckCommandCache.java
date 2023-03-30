package com.skuralll.depositvault.cache;

import java.util.UUID;

public class CheckCommandCache extends CacheBase<UUID, Long> {

  @Override
  public void put(UUID uuid, Long time) {
    cache.put(uuid, System.currentTimeMillis() + time);
  }

  @Override
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
