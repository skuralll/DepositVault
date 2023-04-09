package com.skuralll.depositvault.cache;

public class TimerCache<T> extends NormalCache<T, Long> {

  @Override
  public void put(T key, Long time) {
    cache.put(key, System.currentTimeMillis() + time);
  }

  @Override
  public boolean check(T key) {
    Long time = get(key);
    if (time == null)
      return false;
    if (time <= System.currentTimeMillis())
      return false;
    cache.invalidate(key);
    return true;
  }

}
