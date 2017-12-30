package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.apache.ibatis.annotations.Mapper;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;

import java.util.List;
import java.util.UUID;

@Mapper
public interface DomainToRelationMapper {

  List<DomainToRelation> selectDest(Long id);

  void insert(DomainToRelation domainToRelation);

  void deleteRelationBy(UUID relationId);

  void createTable();
}
