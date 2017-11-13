package org.enlightenseries.DomainDictionary.domain.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domain {
  private Long id;
  private String name;
  private String format;
  private String description;
  private String existential;
}
