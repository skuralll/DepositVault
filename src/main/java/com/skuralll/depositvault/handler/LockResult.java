package com.skuralll.depositvault.handler;

public enum LockResult {
  // lock
  LOCKED,
  SQL_ERROR,
  SUCCESS,
  // unlock
  NOT_LOCKED,
  NOT_OWNER,

}
