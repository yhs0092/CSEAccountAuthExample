package com.github.yhs0092.csedemo.edge.auth;

import javax.servlet.http.Cookie;

import org.apache.servicecomb.common.rest.filter.HttpServerFilter;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.apache.servicecomb.swagger.invocation.Response;

public class SessionToContextFilter implements HttpServerFilter {

  @Override
  public int getOrder() {
    return 0;
  }

  @Override
  public Response afterReceiveRequest(Invocation invocation, HttpServletRequestEx httpServletRequestEx) {
    Cookie[] cookies = httpServletRequestEx.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("x-session-id")) {
        invocation.addContext("x-session-id", cookie.getValue());
        return null;
      }
    }
    return null;
  }
}
