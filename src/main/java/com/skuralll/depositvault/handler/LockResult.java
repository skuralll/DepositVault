package com.skuralll.depositvault.handler;

public enum LockResult {
  // lock
  LOCKED,
  MAX_EXPIRE,
  NOT_ENOUGH_MONEY,
  SQL_ERROR,
  SUCCESS,
  // unlock
  NOT_LOCKED,
  NOT_OWNER,

}
