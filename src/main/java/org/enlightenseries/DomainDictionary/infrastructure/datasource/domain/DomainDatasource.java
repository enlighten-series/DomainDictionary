package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class DomainDatasource implements DomainRepository {
  DomainMapper domainMapper;
  private final SimpleDateFormat importExportDateFormat = new SimpleDateFormat("yy/MM/dd hh:mm:ss");

  public DomainDatasource(
    DomainMapper _domainMapper
  ) {
    this.domainMapper = _domainMapper;
  }

  public List<Domain> list() {
    return this.domainMapper.selectAll();
  }

  public Domain findBy(Long id) {
    return this.domainMapper.select(id);
  }

  @Override
  public DomainDetail findDomainDetailBy(Long id) {
    Domain base = findBy(id);
    if (base == null) {
      return null;
    }

    DomainDetail domainDetail = new DomainDetail(base);
    // TODO: 作成者・更新者を取得する
    domainDetail.setCreatedBy(new User(1L, "taro", "taro"));
    domainDetail.setUpdatedBy(new User(2L, "jiro", "jiro"));
    return domainDetail;
  }

  public DomainSummary findDomainSummaryBy(Long id) {
    return this.domainMapper.selectSummary(id);
  }

  public void register(Domain domain) {
    this.domainMapper.insert(domain);
  }

  public void update(Long id, Domain domain) {
    this.domainMapper.update(id, domain);
  }

  public void delete(Long id) {
    this.domainMapper.delete(id);
  }

  public void createTable() {
    this.domainMapper.createTable();
  }

  /**
   * TODO: テストを作成
   * @param printer
   * @throws IOException
   */
  public void export(CSVPrinter printer) throws IOException {
    printer.printRecord("Domain start");

    domainMapper.exportAll(context -> {
      Domain domain = context.getResultObject();
      try {
        printer.printRecord(
          domain.getId(),
          domain.getName(),
          domain.getFormat(),
          domain.getDescription(),
          domain.getExistential(),
          importExportDateFormat.format(domain.getCreated()),
          importExportDateFormat.format(domain.getUpdated())
        );
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    printer.printRecord("Domain end");
  }

  /**
   * TODO: テストを作成
   * @param parser
   */
  public void import_0_2_X(CSVParser parser) throws ApplicationException, ParseException {
    domainMapper.deleteAllForImport();

    boolean proceed = false;
    for(CSVRecord record : parser) {
      if (!proceed) {
        if (record.get(0).equals("Domain start")) {
          proceed = true;
          continue;
        }
        throw new ApplicationException("Domainの開始位置が見つかりませんでした。");
      }
      if (record.get(0).equals("Domain end")) {
        return;
      }

      Domain _new = new Domain(
        Long.valueOf(record.get(0)),
        record.get(1),
        record.get(2),
        record.get(3),
        record.get(4),
        importExportDateFormat.parse(record.get(5)),
        importExportDateFormat.parse(record.get(6))
      );
      domainMapper.insertForImport(_new);
    }

    throw new ApplicationException("Domainの終了位置が見つかりませんでした。");
  }
}
