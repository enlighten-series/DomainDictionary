package org.enlightenseries.DomainDictionary.domain.model.relation;

public interface RelationRepository {

  void register(Relation relation);

  void createTable();
}
