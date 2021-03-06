package org.enlightenseries.DomainDictionary.domain.model.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainToRelation {
  private UUID id;
  private Long domainId;
  private UUID relationId;

  public DomainToRelation(Long domainId, UUID relationId) {
    this.id = UUID.randomUUID();
    this.domainId = domainId;
    this.relationId = relationId;
  }
}
