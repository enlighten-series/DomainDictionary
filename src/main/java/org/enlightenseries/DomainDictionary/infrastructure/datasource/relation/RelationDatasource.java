package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RelationDatasource implements RelationRepository {

  private RelationMapper relationMapper;

  public void RelationDatasource(
    RelationMapper _relationMapper
  ) {
    this.relationMapper = _relationMapper;
  }

  public void createTable() {
    this.relationMapper.createTable();
  }
}
