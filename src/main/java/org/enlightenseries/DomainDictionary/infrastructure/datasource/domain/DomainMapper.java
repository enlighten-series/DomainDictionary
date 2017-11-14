package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.apache.ibatis.annotations.*;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;

import java.util.List;

@Mapper
public interface DomainMapper {

  @Select("SELECT id, name, format, description, existential, created, updated FROM domain")
  List<Domain> selectAll();

  @Select("SELECT id, name, format, description, existential, created, updated FROM domain WHERE id=#{id}")
  Domain select(Long id);

  @Insert("INSERT INTO domain (name, format, description, existential, created, updated) values (#{name}, #{format}, #{description}, #{existential}, #{created}, #{updated})")
  @Options(useGeneratedKeys = true)
  void insert(Domain domain);

  @Update("UPDATE domain SET name=#{domain.name}, format=#{domain.format}, description=#{domain.description}, existential=#{domain.existential}, updated=#{domain.updated} WHERE id=#{id}")
  void update(@Param("id") Long id, @Param("domain") Domain domain);

  @Delete("DELETE FROM domain WHERE id=#{id}")
  void delete(@Param("id") Long id);
}
