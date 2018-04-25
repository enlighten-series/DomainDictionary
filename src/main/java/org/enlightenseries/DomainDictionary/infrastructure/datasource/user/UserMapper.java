package org.enlightenseries.DomainDictionary.infrastructure.datasource.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ResultHandler;
import org.enlightenseries.DomainDictionary.domain.model.user.User;

@Mapper
public interface UserMapper {

  User selectByUsername(String username);

  User selectById(Long id);

  void insert(User user);

  void update(User user);

  void delete(Long id);

  void createTable();

  void exportAll(ResultHandler<User> resultHandler);
}
