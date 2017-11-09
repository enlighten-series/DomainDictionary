package org.enlightenseries.DomainDictionary.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.enlightenseries.DomainDictionary.domain.Domain;

import java.util.List;

@Mapper
public interface DomainMapper {

  @Select("SELECT id, name, format, description, existential FROM domain")
  List<Domain> selectAll();

  @Select("SELECT id, name, format, description, existential FROM domain WHERE id = #{id}")
  Domain select(Long id);

  @Insert("INSERT INTO domain (name, format, description, existential) values (#{name}, #{format}, #{description}, #{existential})")
  @Options(useGeneratedKeys = true)
  void insert(Domain domain);
}
