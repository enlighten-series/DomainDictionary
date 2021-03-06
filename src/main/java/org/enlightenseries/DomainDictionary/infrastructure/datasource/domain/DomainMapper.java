package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.ResultHandler;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.dao.DomainExport;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.dao.DomainMetaUser;

import java.util.List;

@Mapper
public interface DomainMapper {

  List<Domain> selectAll();

  List<Domain> selectLatestUpdated(int maxCount);

  Domain select(Long id);

  DomainMetaUser selectMetaUser(Long id);

  DomainSummary selectSummary(Long id);

  void insert(Domain domain);

  void insertMetaUser(DomainMetaUser domainMetaUser);

  void update(@Param("id") Long id, @Param("domain") Domain domain);

  void updateMetaUser(@Param("id") Long id, @Param("domainMetaUser") DomainMetaUser domainMetaUser);

  void delete(Long id);

  void deleteMetaUser(Long id);

  void createTable();

  void createTableMetaUser();

  void exportAll(ResultHandler<DomainExport> resultHandler);

  void deleteAllDomain();

  void deleteAllDomainMetaUser();

  void insertForImport(Domain domain);
}
