package com.github.yhs0092.csedemo.provider.service;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netflix.config.DynamicProperty;

@RestSchema(schemaId = "hello")
@RequestMapping("/hello")
public class HelloService {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

  private String helloGreeting = "Hello";

  private DynamicProperty sayHiGreetingConfig = DynamicProperty.getInstance("config.provider.sayHiGreeting");

  public HelloService() {
    DynamicProperty helloGreetingConfig = DynamicProperty.getInstance("config.provider.sayHelloGreeting");
    helloGreetingConfig.addCallback(() -> helloGreeting = helloGreetingConfig.getString());
  }

  @RequestMapping(path = "/sayHello", method = RequestMethod.GET)
  public String sayHello() {
    LOGGER.info(
        "sayHello() is called, sessionId = [{}]", ContextUtils.getInvocationContext().getContext("x-session-id"));
    return helloGreeting;
  }

  @RequestMapping(path = "/sayHi", method = RequestMethod.GET)
  public String sayHi() {
    LOGGER.info(
        "sayHi() is called, sessionId = [{}]", ContextUtils.getInvocationContext().getContext("x-session-id"));
    return sayHiGreetingConfig.getString("Hi");
  }
}
