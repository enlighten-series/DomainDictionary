package org.enlightenseries.DomainDictionary.domain.model.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Relation {
  private UUID id;

  public Relation() {
    this.id = UUID.randomUUID();
  }
}
