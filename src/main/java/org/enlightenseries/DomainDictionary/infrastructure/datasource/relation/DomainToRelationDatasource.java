package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Repository
public class DomainToRelationDatasource implements DomainToRelationRepository {

  private DomainToRelationMapper domainToRelationMapper;

  public DomainToRelationDatasource(
    DomainToRelationMapper _domainToRelationMapper
  ) {
    this.domainToRelationMapper = _domainToRelationMapper;
  }

  public List<DomainToRelation> selectDestinations(Long sourceDomainId) {
    return this.domainToRelationMapper.selectDest(sourceDomainId);
  }

  public void register(DomainToRelation domainToRelation) {
    this.domainToRelationMapper.insert(domainToRelation);
  }

  public void deleteRelationBy(UUID relationId) {
    this.domainToRelationMapper.deleteRelationBy(relationId);
  }

  public void createTable() {
    this.domainToRelationMapper.createTable();
  }

  @Override
  public void export(CSVPrinter printer) throws Exception {
    printer.printRecord("DomainToRelation start");

    domainToRelationMapper.exportAll(context -> {
      DomainToRelation domainToRelation = context.getResultObject();
      try {
        printer.printRecord(
          domainToRelation.getId(),
          domainToRelation.getDomainId(),
          domainToRelation.getRelationId()
        );
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    printer.printRecord("DomainToRelation end");
  }

  public void import_0_2_X(CSVParser parser) throws Exception {
    domainToRelationMapper.deleteAllForImport();

    // TODO: インポート実行
  }
}
