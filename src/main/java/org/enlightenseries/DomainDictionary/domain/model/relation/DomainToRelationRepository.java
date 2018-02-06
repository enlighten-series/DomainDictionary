package org.enlightenseries.DomainDictionary.domain.model.relation;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.util.List;
import java.util.UUID;

public interface DomainToRelationRepository {

  List<DomainToRelation> selectDestinations(Long sourceDomainId);

  void register(DomainToRelation domainToRelation);

  void deleteRelationBy(UUID relationId);

  void createTable();

  void export(CSVPrinter printer) throws Exception;

  void import_0_2_X(CSVParser parser) throws Exception;

  void import_0_3_X(CSVParser parser) throws Exception;
}
