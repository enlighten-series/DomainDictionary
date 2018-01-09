package org.enlightenseries.DomainDictionary.domain.model.domain;

import org.apache.commons.csv.CSVParser;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;

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

  void import_0_2_X(CSVParser parser) throws ApplicationException;
}
