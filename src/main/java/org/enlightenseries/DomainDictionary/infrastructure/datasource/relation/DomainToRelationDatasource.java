package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DomainToRelationDatasource implements DomainToRelationRepository {

  private DomainToRelationMapper domainToRelationMapper;

  public DomainToRelationDatasource(
    DomainToRelationMapper _domainToRelationMapper
  ) {
    this.domainToRelationMapper = _domainToRelationMapper;
  }

  public List<DomainToRelation> selectDestinations(Long sourceDomainId) {
    return this.domainToRelationMapper.selectDest(sourceDomainId);
  }

  public void createTable() {
    this.domainToRelationMapper.createTable();
  }
}
