package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
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

    // インポートデータなし
  }

  @Override
  public void import_0_3_X(CSVParser parser) throws ApplicationException {
    domainToRelationMapper.deleteAllForImport();

    boolean proceed = false;
    for(CSVRecord record : parser) {
      if (!proceed) {
        if (record.get(0).equals("DomainToRelation start")) {
          proceed = true;
          continue;
        }
        throw new ApplicationException("DomainToRelationの開始位置が見つかりませんでした。");
      }
      if (record.get(0).equals("DomainToRelation end")) {
        return;
      }

      DomainToRelation _new = new DomainToRelation(
        UUID.fromString(record.get(0)),
        Long.valueOf(record.get(1)),
        UUID.fromString(record.get(2))
      );
      domainToRelationMapper.insertForImport(_new);
    }

    throw new ApplicationException("DomainToRelationの終了位置が見つかりませんでした。");
  }
}
