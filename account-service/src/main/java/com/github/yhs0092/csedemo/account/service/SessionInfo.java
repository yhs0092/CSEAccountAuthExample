package com.github.yhs0092.csedemo.account.service;

import java.util.UUID;

import javax.ws.rs.core.Response.Status;

import org.apache.servicecomb.swagger.invocation.exception.InvocationException;

public class SessionInfo {
  private String sessionId;

  private long nextRefreshTime = System.currentTimeMillis();

  public SessionInfo() {
    refreshSessionId();
  }

  public void refreshSessionId() {
    nextRefreshTime = System.currentTimeMillis() + SessionInfoConfig.getRefreshInterval();
    sessionId = UUID.randomUUID().toString();
  }

  public void validateSessionId() {
    if (SessionInfoConfig.isSessionTimeoutEnabled() && (System.currentTimeMillis() > nextRefreshTime)) {
      throw new InvocationException(Status.UNAUTHORIZED, "session timeout");
    }
  }

  public String getSessionId() {
    return sessionId;
  }

  public long getNextRefreshTime() {
    return nextRefreshTime;
  }
}
