package com.github.yhs0092.csedemo.account.service;

import java.util.UUID;

public class SessionInfo {
  private String sessionId;

  private long refreshInterval;

  private long nextRefreshTime = System.currentTimeMillis();

  public SessionInfo() {
    this(10_000L);
  }

  public SessionInfo(long refreshInterval) {
    this.refreshInterval = refreshInterval;
    refreshSessionId();
  }

  public void refreshSessionId() {
    nextRefreshTime += refreshInterval;
    sessionId = UUID.randomUUID().toString();
  }

  public boolean validateSessionId(String sessionId) {
    return sessionId.equals(this.sessionId)
        && (System.currentTimeMillis() < nextRefreshTime);
  }

  public String getSessionId() {
    return sessionId;
  }

  public long getRefreshInterval() {
    return refreshInterval;
  }

  public long getNextRefreshTime() {
    return nextRefreshTime;
  }
}
