package com.skuralll.depositvault.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckForNull;

public class NormalCache<K, V> {

  protected Cache<K, V> cache;

  public NormalCache() {
    cache = CacheBuilder.newBuilder()
        .maximumSize(100)
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .build();
  }

  public void put(K key, V value) {
    cache.put(key, value);
  }

  @CheckForNull
  public V get(K key) {
    return cache.getIfPresent(key);
  }

  public boolean check(K key) {
    V value = get(key);
    if (value == null)
      return false;
    cache.invalidate(key);
    return true;
  }

  @CheckForNull
  public V pop(K key) {
    V value = get(key);
    cache.invalidate(key);
    return value;
  }

}
