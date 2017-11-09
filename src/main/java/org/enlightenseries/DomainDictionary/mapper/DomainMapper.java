package org.enlightenseries.DomainDictionary.mapper;

import org.apache.ibatis.annotations.*;
import org.enlightenseries.DomainDictionary.domain.Domain;

import java.util.List;

@Mapper
public interface DomainMapper {

  @Select("SELECT id, name, format, description, existential FROM domain")
  List<Domain> selectAll();

  @Select("SELECT id, name, format, description, existential FROM domain WHERE id=#{id}")
  Domain select(Long id);

  @Insert("INSERT INTO domain (name, format, description, existential) values (#{name}, #{format}, #{description}, #{existential})")
  @Options(useGeneratedKeys = true)
  void insert(Domain domain);

  @Update("UPDATE domain SET name=#{domain.name}, format=#{domain.format}, description=#{domain.description}, existential=#{domain.existential} WHERE id=#{id}")
  void update(@Param("id") Long id, @Param("domain") Domain domain);
}
