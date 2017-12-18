package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DomainToRelationDatasource implements DomainToRelationRepository {

  private DomainToRelationMapper domainToRelationMapper;

  public DomainToRelationDatasource(
    DomainToRelationMapper _domainToRelationMapper
  ) {
    this.domainToRelationMapper = _domainToRelationMapper;
  }

  public void createTable() {
    this.domainToRelationMapper.createTable();
  }
}
