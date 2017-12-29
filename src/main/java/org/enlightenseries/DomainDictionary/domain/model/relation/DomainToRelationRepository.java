package org.enlightenseries.DomainDictionary.domain.model.relation;

import java.util.List;

public interface DomainToRelationRepository {

  List<DomainToRelation> selectDestinations(Long sourceDomainId);

  void register(DomainToRelation domainToRelation);

  void createTable();
}
