package org.enlightenseries.DomainDictionary.domain.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
  private String key;
  private String value;
  private Date created;
  private Date updated;
}
