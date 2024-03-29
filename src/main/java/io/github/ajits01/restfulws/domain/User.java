package io.github.ajits01.restfulws.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {

  @Id @GeneratedValue private Integer id;

  @Size(min = 2, message = "Name should be atleast 2 characters long")
  private String name;

  @Past private Date birthDate;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<Post> posts;
}
