package com.github.yhs0092.csedemo.edge.auth;

import java.util.concurrent.CompletableFuture;

public interface AuthService {
  CompletableFuture<Boolean> verifyRight(String operation, String sessionId);
}
