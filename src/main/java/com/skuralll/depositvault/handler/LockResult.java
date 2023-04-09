package com.skuralll.depositvault.handler;

public enum LockResult {
  // lock
  LOCKED,
  MAX_EXPIRE,
  SQL_ERROR,
  SUCCESS,
  // unlock
  NOT_LOCKED,
  NOT_OWNER,

}
