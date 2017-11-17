package org.enlightenseries.DomainDictionary.infrastructure.datasource.metadata;

import org.apache.ibatis.annotations.*;
import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;

@Mapper
public interface MetadataMapper {

  @Select("SELECT key, value, created, updated FROM metadata WHERE key=#{key}")
  Metadata select(String key);

  @Insert("INSERT INTO metadata (key, value, created, updated) values (#{key}, #{value}, #{created}, #{updated})")
  void insert(Metadata metadata);

  @Update("UPDATE metadata SET value=#{value}, updated=#{updated} WHERE key=#{key}")
  void update(Metadata metadata);
}
