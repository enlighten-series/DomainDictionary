package org.enlightenseries.DomainDictionary.infrastructure.datasource.metadata;

import org.apache.ibatis.annotations.*;
import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;

@Mapper
public interface MetadataMapper {

  Metadata select(String key);

  void insert(Metadata metadata);

  void update(Metadata metadata);

  void createTable();
}
