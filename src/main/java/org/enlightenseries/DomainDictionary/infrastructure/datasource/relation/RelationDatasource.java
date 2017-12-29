package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RelationDatasource implements RelationRepository {

  private RelationMapper relationMapper;

  public RelationDatasource(
    RelationMapper _relationMapper
  ) {
    this.relationMapper = _relationMapper;
  }

  public void register(Relation relation) {
    this.relationMapper.insert(relation);
  }

  public void createTable() {
    this.relationMapper.createTable();
  }
}
