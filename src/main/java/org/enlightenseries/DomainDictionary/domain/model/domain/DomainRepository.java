package org.enlightenseries.DomainDictionary.domain.model.domain;

import org.apache.commons.csv.CSVParser;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.apache.commons.csv.CSVPrinter;

import java.text.ParseException;
import java.util.List;

public interface DomainRepository {
  List<Domain> list();

  Domain findBy(Long id);

  DomainDetail findDomainDetailBy(Long id);

  DomainSummary findDomainSummaryBy(Long id);

  void register(Domain domain);

  void registerDomainDetail(DomainDetail domainDetail);

  void update(Long id, Domain domain);

  void updateDomainDetail(Long id, DomainDetail domainDetail);

  void delete(Long id);

  void createTable();

  void export(CSVPrinter printer) throws Exception;

  void import_0_2_X(CSVParser parser) throws Exception;
}
