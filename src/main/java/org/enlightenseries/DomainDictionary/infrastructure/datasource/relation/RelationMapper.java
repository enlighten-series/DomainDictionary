package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ResultHandler;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;

import java.util.UUID;

@Mapper
public interface RelationMapper {

  Relation select(UUID id);

  void insert(Relation relation);

  void delete(UUID id);

  void createTable();

  void exportAll(ResultHandler<Relation> resultHandler);

  void deleteAllForImport();

  void insertForImport(Relation relation);
}
