package org.enlightenseries.DomainDictionary.domain.model.domain;

import java.util.List;

public interface DomainRepository {
  List<Domain> list();

  Domain findBy(Long id);

  void register(Domain domain);

  void update(Long id, Domain domain);

  void delete(Long id);
}
