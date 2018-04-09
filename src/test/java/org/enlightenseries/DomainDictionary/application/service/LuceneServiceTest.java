package org.enlightenseries.DomainDictionary.application.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.store.*;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneServiceTest {

  private LuceneService luceneService;

  @Before
  public void setup() throws IOException {
    Directory directory = new RAMDirectory();
    Analyzer analyzer = new JapaneseAnalyzer();

    luceneService = new LuceneService(directory, analyzer);
  }

  @Test
  public void registOne() throws Exception {
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
  public void registSame() throws Exception {
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
    assertThat(occured.getMessage()).isEqualTo("このドメインはすでにインデックスに登録されています。");
  }

  @Test
  public void search() throws Exception {
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

  @Test
  public void firstRead() throws Exception {
    // when
    // no-op

    // do
    boolean first = luceneService.existDomain(1L);

    // expect
    assertThat(first).isFalse();
  }

  @Test
  public void existDomain() throws Exception {
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
    boolean before = luceneService.existDomain(domain.getId());
    luceneService.regist(domain);
    List<Long> nameResult = luceneService.search("ドメイン");
    boolean after = luceneService.existDomain(domain.getId());

    // expect
    assertThat(before).isFalse();
    assertThat(nameResult.get(0)).isEqualTo(1L);
    assertThat(after).isTrue();
  }

  @Test
  public void update() throws Exception {
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
    luceneService.regist(domain);

    // do
    domain.setName("新しいドメイン");
    domain.setFormat("新しいフォーマット");
    domain.setDescription("新しい説明");
    domain.setExistential("新しい理由");
    luceneService.update(domain);
    List<Long> newResult = luceneService.search("新しい");
    List<Long> nameResult = luceneService.search("ドメイン");
    List<Long> descriptionResult = luceneService.search("フォーマット");
    List<Long> existentialResult = luceneService.search("説明");
    List<Long> formatResult = luceneService.search("理由");
    List<Long> nohitResult = luceneService.search("マット");

    // expect
    assertThat(newResult.get(0)).isEqualTo(1L);
    assertThat(nameResult.get(0)).isEqualTo(1L);
    assertThat(descriptionResult.get(0)).isEqualTo(1L);
    assertThat(existentialResult.get(0)).isEqualTo(1L);
    assertThat(formatResult.get(0)).isEqualTo(1L);
    assertThat(nohitResult.size()).isEqualTo(0);
  }

  @Test
  public void delete() throws Exception {
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
    luceneService.regist(domain);

    // do
    List<Long> beforeResult = luceneService.search("ドメイン");
    luceneService.delete(1L);
    List<Long> afterResult = luceneService.search("ドメイン");

    // expect
    assertThat(beforeResult.get(0)).isEqualTo(1L);
    assertThat(afterResult.size()).isEqualTo(0);
  }
}
