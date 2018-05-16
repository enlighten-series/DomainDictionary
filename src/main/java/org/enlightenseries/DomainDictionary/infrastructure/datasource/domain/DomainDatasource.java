package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.index.IndexWriter;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.LuceneDatasource;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.dao.DomainExport;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.dao.DomainMetaUser;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.user.UserMapper;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class DomainDatasource implements DomainRepository {

  private DomainMapper domainMapper;
  private UserMapper userMapper;

  private LuceneDatasource luceneDatasource;

  private final SimpleDateFormat importExportDateFormat = new SimpleDateFormat("yy/MM/dd hh:mm:ss");

  public DomainDatasource(
    DomainMapper _domainMapper,
    UserMapper _userMapper,
    LuceneDatasource _luceneDatasource
  ) {
    this.domainMapper = _domainMapper;
    this.userMapper = _userMapper;
    this.luceneDatasource = _luceneDatasource;
  }

  @Override
  public List<Domain> list() {
    return this.domainMapper.selectAll();
  }

  @Override
  public List<Domain> listLatestUpdated(int maxCount) {
    return this.domainMapper.selectLatestUpdated(maxCount);
  }

  @Override
  public DomainDetail findDomainDetailBy(Long id) {
    Domain base = this.domainMapper.select(id);
    if (base == null) {
      return null;
    }

    DomainDetail domainDetail = new DomainDetail(base);
    DomainMetaUser domainMetaUser = this.domainMapper.selectMetaUser(id);
    if (domainMetaUser != null) {
      domainDetail.setCreatedBy(this.userMapper.selectById(domainMetaUser.getCreatedBy()));
      domainDetail.setUpdatedBy(this.userMapper.selectById(domainMetaUser.getUpdatedBy()));
    }
    return domainDetail;
  }

  @Override
  public DomainSummary findDomainSummaryBy(Long id) {
    return this.domainMapper.selectSummary(id);
  }

  @Override
  public void registerDomainDetail(DomainDetail domainDetail) {
    this.domainMapper.insert(domainDetail);

    DomainMetaUser domainMetaUser = new DomainMetaUser();
    domainMetaUser.setId(domainDetail.getId());
    if (domainDetail.getCreatedBy() != null) {
      domainMetaUser.setCreatedBy(domainDetail.getCreatedBy().getId());
    }
    if (domainDetail.getUpdatedBy() != null) {
      domainMetaUser.setUpdatedBy(domainDetail.getUpdatedBy().getId());
    }
    this.domainMapper.insertMetaUser(domainMetaUser);
  }

  @Override
  public void updateDomainDetail(Long id, DomainDetail domainDetail) {
    this.domainMapper.update(id, domainDetail);

    DomainMetaUser domainMetaUser = new DomainMetaUser();
    domainMetaUser.setId(domainDetail.getId());
    if (domainDetail.getUpdatedBy() != null) {
      domainMetaUser.setUpdatedBy(domainDetail.getUpdatedBy().getId());
    }
    this.domainMapper.updateMetaUser(id, domainMetaUser);
  }

  @Override
  public void delete(Long id) {
    this.domainMapper.deleteMetaUser(id);
    this.domainMapper.delete(id);
  }

  @Override
  public void createTable() {
    this.domainMapper.createTable();
    this.domainMapper.createTableMetaUser();
  }

  @Override
  public void export(CSVPrinter printer) throws IOException {
    printer.printRecord("Domain start");

    domainMapper.exportAll(context -> {
      DomainExport domain = context.getResultObject();
      try {
        printer.printRecord(
          domain.getDomain().getId(),
          domain.getDomain().getName(),
          domain.getDomain().getFormat(),
          domain.getDomain().getDescription(),
          domain.getDomain().getExistential(),
          importExportDateFormat.format(domain.getDomain().getCreated()),
          importExportDateFormat.format(domain.getDomain().getUpdated()),
          domain.getDomainMetaUser().getCreatedBy(),
          domain.getDomainMetaUser().getUpdatedBy()
        );
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    printer.printRecord("Domain end");
  }

  @Override
  public void import_0_2_X(CSVParser parser) throws ApplicationException, ParseException {
    domainMapper.deleteAllDomain();
    domainMapper.deleteAllDomainMetaUser();

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

  @Override
  public void import_0_3_X(CSVParser parser) throws ApplicationException, ParseException {
    domainMapper.deleteAllDomain();
    domainMapper.deleteAllDomainMetaUser();

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

  @Override
  public void import_0_4_X(CSVParser parser) throws Exception {
    domainMapper.deleteAllDomain();
    domainMapper.deleteAllDomainMetaUser();
    luceneDatasource.deleteAll();

    IndexWriter importWriter = luceneDatasource.getWriterForImport();
    try {

      boolean proceed = false;
      for (CSVRecord record : parser) {
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

        // 1行分のデータ登録
        Domain domain = new Domain(
          Long.valueOf(record.get(0)),
          record.get(1),
          record.get(2),
          record.get(3),
          record.get(4),
          importExportDateFormat.parse(record.get(5)),
          importExportDateFormat.parse(record.get(6))
        );
        domainMapper.insertForImport(domain);

        DomainMetaUser domainMetaUser = new DomainMetaUser(
          domain.getId(),
          record.get(7).isEmpty() ? null : Long.valueOf(record.get(7)),
          record.get(8).isEmpty() ? null : Long.valueOf(record.get(8))
        );
        domainMapper.insertMetaUser(domainMetaUser);

        luceneDatasource.registOneForImprot(importWriter, domain);
      }

      throw new ApplicationException("Domainの終了位置が見つかりませんでした。");
    } finally {
      if (importWriter != null) {
        importWriter.commit();
        importWriter.close();
      }
    }
  }
}
