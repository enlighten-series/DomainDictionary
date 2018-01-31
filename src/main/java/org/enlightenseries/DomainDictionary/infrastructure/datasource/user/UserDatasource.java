package org.enlightenseries.DomainDictionary.infrastructure.datasource.user;

import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.enlightenseries.DomainDictionary.domain.model.user.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserDatasource implements UserRepository {

  private UserMapper userMapper;

  public UserDatasource(
    UserMapper _userMapper
  ) {
    this.userMapper = _userMapper;
  }

  @Override
  public User findByUsername(String username) {
    return userMapper.selectByUsername(username);
  }

  @Override
  public void register(User user) {
    userMapper.insert(user);
  }

  @Override
  public void update(User user) {
    userMapper.update(user);
  }

  @Override
  public void delete(Long id) {
    userMapper.delete(id);
  }

  @Override
  public void createTable() {
    userMapper.createTable();
  }
}
