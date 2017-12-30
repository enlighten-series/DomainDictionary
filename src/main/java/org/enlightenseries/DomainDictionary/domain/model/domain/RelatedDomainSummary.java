package org.enlightenseries.DomainDictionary.domain.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatedDomainSummary {
  private UUID relationId;
  private Long domainId;
  private String name;
}
