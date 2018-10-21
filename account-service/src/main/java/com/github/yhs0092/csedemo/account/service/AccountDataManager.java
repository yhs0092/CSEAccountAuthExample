package com.github.yhs0092.csedemo.account.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.springframework.stereotype.Component;

@Component
public class AccountDataManager {
  private Map<String, Account> accountMap;

  public AccountDataManager() {
    accountMap = new HashMap<>();
    Account admin = new Account("admin", "admin");
    accountMap.put("admin", admin);
  }

  public boolean addAccount(Account account) {
    if (null != accountMap.get(account.getUserName())) {
      throw new InvocationException(Status.BAD_REQUEST, "account already exits");
    }
    accountMap.put(account.getUserName(), account);
    return true;
  }

  public Account queryAccount(String accountName) {
    return accountMap.get(accountName);
  }
}
