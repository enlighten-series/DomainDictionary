package org.enlightenseries.DomainDictionary.domain.model.user;

public interface UserRepository {

  User findByUsername(String username);

  void register(User user);

  void update(User user);

  void delete(Long id);

  void createTable();
}
