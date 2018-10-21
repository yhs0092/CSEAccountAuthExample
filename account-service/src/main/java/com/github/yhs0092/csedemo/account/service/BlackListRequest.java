package com.github.yhs0092.csedemo.account.service;

import java.util.Set;

public class BlackListRequest {
  private String operationName;

  private Set<String> accountNames;

  public String getOperationName() {
    return operationName;
  }

  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }

  public Set<String> getAccountNames() {
    return accountNames;
  }

  public void setAccountNames(Set<String> accountNames) {
    this.accountNames = accountNames;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BlackListRequest{");
    sb.append("operationName='").append(operationName).append('\'');
    sb.append(", accountNames=").append(accountNames);
    sb.append('}');
    return sb.toString();
  }
}
