package org.enlightenseries.DomainDictionary.domain.model.relation;

import org.apache.commons.csv.CSVParser;

import java.util.UUID;

public interface RelationRepository {

  Relation findBy(UUID id);

  void register(Relation relation);

  void delete(UUID id);

  void createTable();

  void import_0_2_X(CSVParser parser) throws Exception;
}
