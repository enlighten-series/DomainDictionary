package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository
public class DomainDatasource implements DomainRepository {
  DomainMapper domainMapper;

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
   * @param exportFilePath
   * @throws IOException
   */
  public void export(String exportFilePath) throws IOException {
    File exportFile = new File(exportFilePath);

    try (BufferedWriter bw = new BufferedWriter(
      new OutputStreamWriter(new FileOutputStream(exportFile), StandardCharsets.UTF_8))) {

      exportFile.createNewFile();

      CSVPrinter p = CSVFormat.RFC4180.print(bw);

      domainMapper.exportAll(context -> {
        Domain domain = context.getResultObject();
        try {
          p.printRecord(
            domain.getId(),
            domain.getName(),
            domain.getFormat(),
            domain.getDescription(),
            domain.getExistential(),
            domain.getCreated(),
            domain.getUpdated()
          );
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

    } catch (IOException e) {
      throw e;
    }
  }
}
