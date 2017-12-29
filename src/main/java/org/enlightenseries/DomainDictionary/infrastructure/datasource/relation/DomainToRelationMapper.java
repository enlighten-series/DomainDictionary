package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.apache.ibatis.annotations.Mapper;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;

import java.util.List;

@Mapper
public interface DomainToRelationMapper {

  List<DomainToRelation> selectDest(Long id);

  void insert(DomainToRelation domainToRelation);

  void createTable();
}
