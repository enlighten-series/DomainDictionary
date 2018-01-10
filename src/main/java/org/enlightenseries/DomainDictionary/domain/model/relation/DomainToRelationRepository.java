package org.enlightenseries.DomainDictionary.domain.model.relation;

import org.apache.commons.csv.CSVParser;

import java.util.List;
import java.util.UUID;

public interface DomainToRelationRepository {

  List<DomainToRelation> selectDestinations(Long sourceDomainId);

  void register(DomainToRelation domainToRelation);

  void deleteRelationBy(UUID relationId);

  void createTable();

  void import_0_2_X(CSVParser parser) throws Exception;
}
