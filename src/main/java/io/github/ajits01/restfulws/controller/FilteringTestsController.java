package io.github.ajits01.restfulws.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.github.ajits01.restfulws.domain.FilteringTestBean;

@RestController
public class FilteringTestsController {

  @GetMapping(path = "/filteredBean")
  public MappingJacksonValue filteredBeanResource() {

    FilteringTestBean filteringTestBean =
        new FilteringTestBean("John Doe", 111, "123-45-6789", "Architect");

    MappingJacksonValue mappingJacksonValue =
        filterObjectAndInclProps(filteringTestBean, new String[] {"name", "designation"});

    return mappingJacksonValue;
  }

  @GetMapping(path = "/filteredBeanList")
  public MappingJacksonValue filteredBeanListResource() {

    List<FilteringTestBean> list =
        Arrays.asList(
            new FilteringTestBean("John Doe", 111, "123-45-6789", "Architect"),
            new FilteringTestBean("Jane Doe", 113, "987-65-4321", "Developer"));

    MappingJacksonValue mappingJacksonValue =
        filterObjectAndInclProps(list, new String[] {"name", "designation"});

    return mappingJacksonValue;
  }

  private MappingJacksonValue filterObjectAndInclProps(
      Object objectToFilter, String... propsToKeep) {
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(objectToFilter);

    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(propsToKeep);

    FilterProvider filters =
        new SimpleFilterProvider().addFilter("FilteringTestBean_filter", filter);

    mappingJacksonValue.setFilters(filters);
    return mappingJacksonValue;
  }
}
