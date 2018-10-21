package com.github.yhs0092.csedemo.account.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Account {
  String userName;

  String password;

  @JsonIgnore
  SessionInfo sessionInfo = new SessionInfo();

  public Account() {

  }

  public Account(String userName, String password) {
    this.userName = userName;
    this.password = password;
    sessionInfo = new SessionInfo();
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public SessionInfo getSessionInfo() {
    return sessionInfo;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Account{");
    sb.append("userName='").append(userName).append('\'');
    sb.append(", password='").append(password).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

