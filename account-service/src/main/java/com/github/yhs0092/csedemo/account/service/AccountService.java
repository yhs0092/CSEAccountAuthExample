package com.github.yhs0092.csedemo.account.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestSchema(schemaId = "account")
@RequestMapping("/account")
public class AccountService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

  @Inject
  private AccountDataManager accountDataManager;

  @Inject
  private AccessRightManager accessRightManager;

  private Map<String, Account> sessionAccountMap = new HashMap<>();

  @GetMapping("/account")
  public Account getAccount(@RequestParam("accountName") String accountName) {
    LOGGER.info("getAccount() is called, accountName = [{}]", accountName);
    Account account = accountDataManager.queryAccount(accountName);
    if (null == account) {
      throw new InvocationException(Status.NOT_FOUND, "specified account not found");
    }
    return account;
  }

  @PostMapping("/account")
  public boolean addAccount(@RequestBody Account account) {
    LOGGER.info("addAccount() is called, account = [{}]", account);
    return accountDataManager.addAccount(account);
  }

  @PostMapping("/login")
  public SessionInfo login(@RequestParam("accountName") String accountName, @RequestParam("password") String password) {
    LOGGER.info("login() is called, account = [{}], password = [{}]", accountName, password);
    Account account = accountDataManager.queryAccount(accountName);
    if (null == account || !password.equals(account.getPassword())) {
      throw new InvocationException(Status.UNAUTHORIZED, "login failed");
    }

    sessionAccountMap.remove(account.getSessionInfo().getSessionId());
    account.getSessionInfo().refreshSessionId();
    sessionAccountMap.put(account.getSessionInfo().getSessionId(), account);
    return account.getSessionInfo();
  }

  @GetMapping("/verifyRight")
  public boolean verifyRight(@RequestParam("operation") String operation, @RequestParam("sessionId") String sessionId) {
    LOGGER.info("verifyRight() is called, operaton = [{}], sessionId = [{}]", operation, sessionId);
    if (null == sessionId) {
      throw new InvocationException(Status.UNAUTHORIZED, "no session id");
    }
    Account account = sessionAccountMap.get(sessionId);
    if (null == account) {
      throw new InvocationException(Status.UNAUTHORIZED, "session invalid");
    }
    account.getSessionInfo().validateSessionId();
    accessRightManager.checkAccessRight(operation, account);
    return true;
  }

  @PutMapping("/blackList")
  public void setBlackList(@RequestBody BlackListRequest blackListRequest) {
    LOGGER.info("setBlackList() is called, blackListRequest = [{}]", blackListRequest);
    accessRightManager.setBlackList(blackListRequest.getOperationName(), blackListRequest.getAccountNames());
  }
}
