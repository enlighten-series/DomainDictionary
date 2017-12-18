package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelationMapper {

  void createTable();
}
