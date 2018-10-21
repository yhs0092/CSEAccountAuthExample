package com.github.yhs0092.csedemo.edge.auth;

import java.util.concurrent.CompletableFuture;

import javax.ws.rs.core.Response.Status;

import org.apache.servicecomb.core.Handler;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.provider.pojo.Invoker;
import org.apache.servicecomb.swagger.invocation.AsyncResponse;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;

public class AuthHandler implements Handler {

  private final AuthService authService;

  public AuthHandler() {
    authService = Invoker.createProxy("account-service", "account", AuthService.class);
  }

  @Override
  public void handle(Invocation invocation, AsyncResponse asyncResponse) throws Exception {
    if (isNoAuthOperation(invocation)) {
      invocation.next(asyncResponse);
      return;
    }

    String sessionId = invocation.getContext("x-session-id");
    if (null == sessionId) {
      throw new InvocationException(Status.UNAUTHORIZED, "no session id");
    }

    CompletableFuture<Boolean> verifyPassedAsyncResult = authService
        .verifyRight(invocation.getOperationMeta().getMicroserviceQualifiedName(),
            sessionId);
    verifyPassedAsyncResult.whenComplete((verifyPassed, exception) -> {
      if (null != exception) {
        asyncResponse.consumerFail(exception);
      }
      if (!verifyPassed) {
        asyncResponse.consumerFail(new InvocationException(Status.FORBIDDEN, "auth handler failed"));
      }
      try {
        invocation.next(asyncResponse);
      } catch (Exception e) {
        asyncResponse.consumerFail(e);
      }
    });
  }

  private boolean isNoAuthOperation(Invocation invocation) {
    return "account-service.account.login".equals(invocation.getOperationMeta().getMicroserviceQualifiedName())
        || "account-service.account.verifyRight".equals(invocation.getOperationMeta().getMicroserviceQualifiedName());
  }
}
