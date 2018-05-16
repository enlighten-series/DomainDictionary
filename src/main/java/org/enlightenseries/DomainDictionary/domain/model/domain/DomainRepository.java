package org.enlightenseries.DomainDictionary.domain.model.domain;

import org.apache.commons.csv.CSVParser;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.apache.commons.csv.CSVPrinter;

import java.text.ParseException;
import java.util.List;

public interface DomainRepository {
  List<Domain> list();

  List<Domain> listLatestUpdated(int maxCount);

  DomainDetail findDomainDetailBy(Long id);

  DomainSummary findDomainSummaryBy(Long id);

  void registerDomainDetail(DomainDetail domainDetail);

  void updateDomainDetail(Long id, DomainDetail domainDetail);

  void delete(Long id);

  void createTable();

  void export(CSVPrinter printer) throws Exception;

  void import_0_2_X(CSVParser parser) throws Exception;

  void import_0_3_X(CSVParser parser) throws Exception;

  void import_0_4_X(CSVParser parser) throws Exception;
}
