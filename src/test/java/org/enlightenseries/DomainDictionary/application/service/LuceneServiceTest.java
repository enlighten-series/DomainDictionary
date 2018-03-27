package org.enlightenseries.DomainDictionary.application.service;

import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.RAMDirectory;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneServiceTest {

  private LuceneService luceneService;

  @Before
  public void setup() {
    luceneService = new LuceneService(
      new RAMDirectory(),
      new JapaneseAnalyzer()
    );
  }

  @Test
  public void registOne() throws IOException {
    // when
    Domain domain = new Domain(
      1L,
      "ドメイン名",
      "フォーマット",
      "説明",
      "存在理由",
      new Date(),
      new Date()
    );

    // do
    luceneService.regist(domain);

    // expect
    // no-op
  }

  @Test
  public void registSame() throws IOException {
    // when
    Domain domain = new Domain(
      1L,
      "ドメイン名",
      "フォーマット",
      "説明",
      "存在理由",
      new Date(),
      new Date()
    );
    DomainDetail domainDetail = new DomainDetail(domain);

    // do
    luceneService.regist(domain);
    Exception occured = null;
    try {
      luceneService.regist(domainDetail);
    } catch (Exception e) {
      occured = e;
    }

    // expect
    assertThat(occured).isNotNull();
    assertThat(occured instanceof ApplicationException).isTrue();
    assertThat(occured.getMessage()).isEqualTo("このドキュメントはすでに登録されています。");
  }

  @Test
  public void search() throws IOException, ParseException {
    // when
    Domain domain = new Domain(
      1L,
      "ドメイン名",
      "フォーマット",
      "説明",
      "存在理由",
      new Date(),
      new Date()
    );

    // do
    luceneService.regist(domain);
    List<Long> nameResult = luceneService.search("ドメイン");
    List<Long> descriptionResult = luceneService.search("フォーマット");
    List<Long> existentialResult = luceneService.search("説明");
    List<Long> formatResult = luceneService.search("理由");
    List<Long> nohitResult = luceneService.search("マット");

    // expect
    assertThat(nameResult.get(0)).isEqualTo(1L);
    assertThat(descriptionResult.get(0)).isEqualTo(1L);
    assertThat(existentialResult.get(0)).isEqualTo(1L);
    assertThat(formatResult.get(0)).isEqualTo(1L);
    assertThat(nohitResult.size()).isEqualTo(0);
  }
}
