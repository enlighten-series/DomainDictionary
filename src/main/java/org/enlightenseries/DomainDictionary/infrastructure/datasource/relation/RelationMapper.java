package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.apache.ibatis.annotations.Mapper;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;

@Mapper
public interface RelationMapper {

  void insert(Relation relation);

  void createTable();
}
