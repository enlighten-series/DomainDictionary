package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.dao.DomainMetaUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainDatasourceTest {

  @Autowired
  private DomainDatasource domainDatasource;

  @Autowired
  private DomainMapper domainMapper;

  @Before
  public void setup() {
    domainMapper.deleteAllDomain();
    domainMapper.deleteAllDomainMetaUser();
  }

  @Test
  public void export() throws IOException {
    // when
    DomainDetail domain = new DomainDetail();
    domain.setId(1L);
    domain.setName("ドメイン");
    domain.setDescription("説明");
    domain.setExistential("存在");
    domain.setFormat("フォーマット");
    domain.setCreated(new Date());
    domain.setUpdated(new Date());
    User user = new User();
    user.setId(1L);
    domain.setCreatedBy(user);
    domain.setUpdatedBy(user);

    PipedInputStream pipeInput = new PipedInputStream();
    BufferedReader reader = new BufferedReader(
      new InputStreamReader(pipeInput));
    BufferedOutputStream out = new BufferedOutputStream(
      new PipedOutputStream(pipeInput));

    // do
    domainDatasource.registerDomainDetail(domain);
    DomainDetail registeredDetail = domainDatasource.findDomainDetailBy(domain.getId());
    try (BufferedWriter bw = new BufferedWriter(
      new OutputStreamWriter(out, StandardCharsets.UTF_8))) {
      CSVPrinter printer = CSVFormat.RFC4180.print(bw);
      // 各Repositoryにファイルへのエクスポートを依頼する（大量データ処理はインフラに依存するため）
      domainDatasource.export(printer);
    }

    // expect
    assertThat(registeredDetail.getName()).isEqualTo("ドメイン");
    assertThat(registeredDetail.getCreatedBy().getUsername()).isEqualTo("admin");
    assertThat(reader.readLine()).isEqualTo("Domain start");
    Optional.of(reader.readLine()).ifPresent(stmt -> {
      assertThat(stmt).contains(",ドメイン,フォーマット,説明,存在,");
    });
    assertThat(reader.readLine()).isEqualTo("Domain end");
  }
}
