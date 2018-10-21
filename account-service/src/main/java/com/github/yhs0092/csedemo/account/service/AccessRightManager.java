package com.github.yhs0092.csedemo.account.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.springframework.stereotype.Component;

@Component
public class AccessRightManager {
  private Map<String, Set<String>> blackList = new HashMap<>();

  public AccessRightManager() {
    HashSet<String> accounts = new HashSet<>();
  }


  public void setBlackList(String operationName, Set<String> accountNames) {
    blackList.put(operationName, accountNames);
  }

  public void checkAccessRight(String operationName, Account account) {
    Set<String> forbiddenAccounts = blackList.get(operationName);
    if (null == forbiddenAccounts) {
      return;
    }
    if (forbiddenAccounts.contains(account.getUserName())) {
      throw new InvocationException(Status.FORBIDDEN, "account in blacklist");
    }
  }
}
