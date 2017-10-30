package org.enlightenseries.DomainDictionary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Domain {
  private Long id;
  private String name;
  private String format;
  private String description;
  private String existential;
}
