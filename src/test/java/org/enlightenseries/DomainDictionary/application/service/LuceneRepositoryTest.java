package org.enlightenseries.DomainDictionary.application.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.store.*;
import org.enlightenseries.DomainDictionary.infrastructure.config.LuceneProperties;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.LuceneRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneRepositoryTest {

  private LuceneRepository luceneRepository;

  @Autowired
  private LuceneProperties luceneProperties;

  @Before
  public void setup() throws IOException {
    Directory directory = new RAMDirectory();
    Analyzer analyzer = new JapaneseAnalyzer();

    luceneRepository = new LuceneRepository(directory, analyzer, luceneProperties);
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
    luceneRepository.regist(domain);

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
    luceneRepository.regist(domain);
    Exception occured = null;
    try {
      luceneRepository.regist(domainDetail);
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
    luceneRepository.regist(domain);
    List<Long> nameResult = luceneRepository.search("ドメイン");
    List<Long> descriptionResult = luceneRepository.search("フォーマット");
    List<Long> existentialResult = luceneRepository.search("説明");
    List<Long> formatResult = luceneRepository.search("理由");
    List<Long> nohitResult = luceneRepository.search("マット");

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
    boolean first = luceneRepository.existDomain(1L);

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
    boolean before = luceneRepository.existDomain(domain.getId());
    luceneRepository.regist(domain);
    List<Long> nameResult = luceneRepository.search("ドメイン");
    boolean after = luceneRepository.existDomain(domain.getId());

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
    luceneRepository.regist(domain);

    // do
    domain.setName("新しいドメイン");
    domain.setFormat("新しいフォーマット");
    domain.setDescription("新しい説明");
    domain.setExistential("新しい理由");
    luceneRepository.update(domain);
    List<Long> newResult = luceneRepository.search("新しい");
    List<Long> nameResult = luceneRepository.search("ドメイン");
    List<Long> descriptionResult = luceneRepository.search("フォーマット");
    List<Long> existentialResult = luceneRepository.search("説明");
    List<Long> formatResult = luceneRepository.search("理由");
    List<Long> nohitResult = luceneRepository.search("マット");

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
    luceneRepository.regist(domain);

    // do
    List<Long> beforeResult = luceneRepository.search("ドメイン");
    luceneRepository.delete(1L);
    List<Long> afterResult = luceneRepository.search("ドメイン");

    // expect
    assertThat(beforeResult.get(0)).isEqualTo(1L);
    assertThat(afterResult.size()).isEqualTo(0);
  }
}
