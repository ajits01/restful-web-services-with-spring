package io.github.ajits01.restfulws.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonFilter("FilteringTestBean_filter")
public class FilteringTestBean {
  private String name;
  private int id;
  //  @JsonIgnore
  private String ssn;
  private String designation;
}
