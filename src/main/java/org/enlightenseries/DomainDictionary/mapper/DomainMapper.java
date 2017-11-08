package org.enlightenseries.DomainDictionary.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.enlightenseries.DomainDictionary.domain.Domain;

import java.util.List;

@Mapper
public interface DomainMapper {

  @Select("SELECT id, name, format, description, existential FROM domain")
  List<Domain> selectAll();
}
