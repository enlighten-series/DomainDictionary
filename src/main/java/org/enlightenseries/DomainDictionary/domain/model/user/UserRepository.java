package org.enlightenseries.DomainDictionary.domain.model.user;

import org.apache.commons.csv.CSVPrinter;

public interface UserRepository {

  User findByUsername(String username);

  void register(User user);

  void update(User user);

  void delete(Long id);

  void createTable();

  void export(CSVPrinter printer) throws Exception;
}
