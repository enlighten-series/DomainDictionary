package org.enlightenseries.DomainDictionary.domain.model.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainToRelation {
  private Long id;
  private Long domainId;
  private Long relationId;
}
