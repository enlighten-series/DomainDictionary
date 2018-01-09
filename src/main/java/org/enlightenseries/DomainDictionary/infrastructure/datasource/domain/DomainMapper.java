package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.ResultHandler;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;

import java.util.List;

@Mapper
public interface DomainMapper {

  List<Domain> selectAll();

  Domain select(Long id);

  DomainSummary selectSummary(Long id);

  void insert(Domain domain);

  void update(@Param("id") Long id, @Param("domain") Domain domain);

  void delete(Long id);

  void createTable();

  void exportAll(ResultHandler<Domain> resultHandler);

  void insertForImport(Domain domain);
}
