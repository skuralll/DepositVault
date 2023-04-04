package com.skuralll.depositvault.handler;

public enum LockResult {
  // lock
  LOCKED,
  NOT_ENOUGH_DEPOSIT,
  SQL_ERROR,
  SUCCESS,
  // unlock
  NOT_LOCKED,
  NOT_OWNER,

}
