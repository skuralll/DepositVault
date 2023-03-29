package com.skuralll.depositvault.model;

public class DepositData {

  private Integer interval;
  private Double payment;
  private Double min_pay;
  private Double deposit;

  public DepositData(Integer interval, Double payment, Double min_pay, Double deposit) {
    this.interval = interval;
    this.payment = payment;
    this.min_pay = min_pay;
    this.deposit = deposit;
  }

  @Override
  public String toString() {
    return "DepositData{" +
        "interval=" + interval +
        ", payment=" + payment +
        ", min_pay=" + min_pay +
        ", deposit=" + deposit +
        '}';
  }

  public Integer getInterval() {
    return interval;
  }

  public Double getPayment() {
    return payment;
  }

  public Double getMin_pay() {
    return min_pay;
  }

  public Double getDeposit() {
    return deposit;
  }
}
