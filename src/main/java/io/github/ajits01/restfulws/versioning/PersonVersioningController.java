package io.github.ajits01.restfulws.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

  /* Versioning using URI */

  @GetMapping("v1/person")
  public PersonV1 personV1() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping("v2/person")
  public PersonV2 personV2() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }

  /* Versioning using request params */

  @GetMapping(value = "/person/param", params = "version=1")
  public PersonV1 paramV1() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping(value = "/person/param", params = "version=2")
  public PersonV2 paramV2() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }

  /* Versioning using custom request headers */

  @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
  public PersonV1 headerV1() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
  public PersonV2 headerV2() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }

  /* Versioning using Accept header or MIME type versioning */

  @GetMapping(value = "/person/produces", produces = "application/vnd.api.v1+json")
  public PersonV1 producesV1() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping(value = "/person/produces", produces = "application/vnd.api.v2+json")
  public PersonV2 producesV2() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }
}
