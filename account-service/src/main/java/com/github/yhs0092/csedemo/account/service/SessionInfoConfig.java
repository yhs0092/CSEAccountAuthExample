package com.github.yhs0092.csedemo.account.service;

import org.slf4j.LoggerFactory;

import com.netflix.config.DynamicProperty;

public class SessionInfoConfig {
  private static long refreshInterval;

  static {
    DynamicProperty refreshIntervalConfig = DynamicProperty.getInstance("config.session.refresh.interval");
    refreshInterval = refreshIntervalConfig.getLong(30_000L);
    refreshIntervalConfig.addCallback(() -> {
      refreshInterval = refreshIntervalConfig.getLong();
      LoggerFactory.getLogger(SessionInfoConfig.class)
          .info("session refresh interval changed to [{}]", refreshInterval);
    });
  }

  public static long getRefreshInterval() {
    return refreshInterval;
  }

  public static boolean isSessionTimeoutEnabled() {
    return DynamicProperty.getInstance("config.session.refresh.enabled").getBoolean(false);
  }
}
