package org.enlightenseries.DomainDictionary.domain.model.domain;

import java.io.IOException;
import java.util.List;

public interface DomainRepository {
  List<Domain> list();

  Domain findBy(Long id);

  DomainSummary findDomainSummaryBy(Long id);

  void register(Domain domain);

  void update(Long id, Domain domain);

  void delete(Long id);

  void createTable();

  void export(String exportFilePath) throws IOException;
}
