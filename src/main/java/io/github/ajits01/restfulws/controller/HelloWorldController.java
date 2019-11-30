package io.github.ajits01.restfulws.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
public class HelloWorldController {

  @GetMapping(path = "/hello")
  public String hello(@RequestParam(name = "name", defaultValue = "World") String name) {
    return String.format("Hello %s.", name);
  }

  @GetMapping(path = "/bean/hello/{name}")
  public HelloBean beanResource(@PathVariable String name) {
    return new HelloBean("Greetings ", name);
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class HelloBean {
  private String greeting;
  private String name;
}