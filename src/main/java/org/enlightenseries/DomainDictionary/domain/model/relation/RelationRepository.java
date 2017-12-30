package org.enlightenseries.DomainDictionary.domain.model.relation;

import java.util.UUID;

public interface RelationRepository {

  Relation findBy(UUID id);

  void register(Relation relation);

  void createTable();
}
