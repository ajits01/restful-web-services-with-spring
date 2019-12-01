package io.github.ajits01.restfulws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
public class HelloWorldController {

  @Autowired public MessageSource messageSource;

  @GetMapping(path = "/hello")
  public String hello(@RequestParam(name = "name", defaultValue = "World") String name) {
    return String.format("Hello %s.", name);
  }

  @GetMapping(path = "/hello/{name}")
  public String helloI18n(@PathVariable String name) {
    String welcome = messageSource.getMessage("welcome", null, LocaleContextHolder.getLocale());
    return String.format("%s %s.", welcome, name);
  }

  @GetMapping(path = "/bean/hello/{name}")
  public HelloBean beanResource(@PathVariable String name) {
    return new HelloBean("Greetings ", name);
  }

  @GetMapping(path = "/i18n/hello/{name}")
  public String helloI18nSupport(@PathVariable String name) {
    String welcome = messageSource.getMessage("welcome", null, LocaleContextHolder.getLocale());
    String greeting = messageSource.getMessage("greeting", null, LocaleContextHolder.getLocale());
    return String.format("%s %s,\n%s", welcome, name, greeting);
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class HelloBean {
  private String greeting;
  private String name;
}