package io.github.ajits01.restfulws.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
  private Integer id;
  private String name;
  private Date birthDate; 
}
