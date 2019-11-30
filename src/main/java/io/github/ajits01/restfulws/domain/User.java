package io.github.ajits01.restfulws.domain;

import java.util.Date;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
  private Integer id;

  @Size(min = 2, message = "Name should be atleast 2 characters long")
  private String name;

  @Past private Date birthDate;
}
