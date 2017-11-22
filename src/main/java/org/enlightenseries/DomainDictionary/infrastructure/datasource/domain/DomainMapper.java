package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.apache.ibatis.annotations.*;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;

import java.util.List;

@Mapper
public interface DomainMapper {

  List<Domain> selectAll();

  Domain select(Long id);

  void insert(Domain domain);

  void update(@Param("id") Long id, @Param("domain") Domain domain);

  void delete(Long id);

  void createTable();
}
