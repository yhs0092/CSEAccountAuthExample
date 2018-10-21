package com.github.yhs0092.csedemo.account.service;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestSchema(schemaId = "account")
@RequestMapping("/account")
public class AccountService {
  @Inject
  private AccountDataManager accountDataManager;

  @GetMapping("/account")
  public Account getAccount(@RequestParam("accountName") String accountName) {
    Account account = accountDataManager.queryAccount(accountName);
    if (null == account) {
      throw new InvocationException(Status.NOT_FOUND, "specified account not found");
    }
    return account;
  }

  @PostMapping("/account")
  public boolean addAccount(@RequestBody Account account) {
    return accountDataManager.addAccount(account);
  }

  @PostMapping("/login")
  public SessionInfo login(@RequestParam("accountName") String accountName, @RequestParam("password") String password) {
    Account account = accountDataManager.queryAccount(accountName);
    if (null == account || !password.equals(account.getPassword())) {
      throw new InvocationException(Status.UNAUTHORIZED, "login failed");
    }
    account.getSessionInfo().refreshSessionId();
    return account.getSessionInfo();
  }
}
