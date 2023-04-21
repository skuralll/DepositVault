package com.skuralll.depositvault.handler;

public enum LockResult {
  // success
  SUCCESS_LOCK,
  SUCCESS_UNLOCK,
  SUCCESS_EXTEND,
  // lock
  LOCKED,
  MAX_EXPIRE,
  NOT_ENOUGH_MONEY,
  // unlock
  NOT_LOCKED,
  NOT_OWNER,
  // others
  SQL_ERROR,
}
