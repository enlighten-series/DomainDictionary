package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.UUID;

@Repository
public class RelationDatasource implements RelationRepository {

  private RelationMapper relationMapper;

  public RelationDatasource(
    RelationMapper _relationMapper
  ) {
    this.relationMapper = _relationMapper;
  }

  public Relation findBy(UUID id) {
    return this.relationMapper.select(id);
  }

  public void register(Relation relation) {
    this.relationMapper.insert(relation);
  }

  public void delete(UUID id) {
    this.relationMapper.delete(id);
  }

  public void createTable() {
    this.relationMapper.createTable();
  }

  @Override
  public void export(CSVPrinter printer) throws Exception {
    printer.printRecord("Relation start");

    relationMapper.exportAll(context -> {
      Relation relation = context.getResultObject();
      try {
        printer.printRecord(
          relation.getId()
        );
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    printer.printRecord("Relation end");
  }

  public void import_0_2_X(CSVParser parser) throws Exception {
    relationMapper.deleteAllForImport();

    // TODO: インポート実行
  }
}
