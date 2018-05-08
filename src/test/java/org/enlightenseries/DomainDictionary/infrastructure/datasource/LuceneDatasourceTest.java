package org.enlightenseries.DomainDictionary.infrastructure.datasource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.enlightenseries.DomainDictionary.infrastructure.config.LuceneProperties;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
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
public class LuceneDatasourceTest {

  @Autowired
  private LuceneDatasource luceneDatasource;

  @Autowired
  private LuceneProperties luceneProperties;

  @Before
  public void setup() throws Exception {
    luceneDatasource.deleteAll();
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
    luceneDatasource.regist(domain);

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
    luceneDatasource.regist(domain);
    Exception occured = null;
    try {
      luceneDatasource.regist(domainDetail);
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
    luceneDatasource.regist(domain);
    List<Long> nameResult = luceneDatasource.search("ドメイン");
    List<Long> descriptionResult = luceneDatasource.search("フォーマット");
    List<Long> existentialResult = luceneDatasource.search("説明");
    List<Long> formatResult = luceneDatasource.search("理由");
    List<Long> nohitResult = luceneDatasource.search("マット");

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
    boolean first = luceneDatasource.existDomain(1L);

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
    boolean before = luceneDatasource.existDomain(domain.getId());
    luceneDatasource.regist(domain);
    List<Long> nameResult = luceneDatasource.search("ドメイン");
    boolean after = luceneDatasource.existDomain(domain.getId());

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
    luceneDatasource.regist(domain);

    // do
    domain.setName("新しいドメイン");
    domain.setFormat("新しいフォーマット");
    domain.setDescription("新しい説明");
    domain.setExistential("新しい理由");
    luceneDatasource.update(domain);
    List<Long> newResult = luceneDatasource.search("新しい");
    List<Long> nameResult = luceneDatasource.search("ドメイン");
    List<Long> descriptionResult = luceneDatasource.search("フォーマット");
    List<Long> existentialResult = luceneDatasource.search("説明");
    List<Long> formatResult = luceneDatasource.search("理由");
    List<Long> nohitResult = luceneDatasource.search("マット");

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
    luceneDatasource.regist(domain);

    // do
    List<Long> beforeResult = luceneDatasource.search("ドメイン");
    luceneDatasource.delete(1L);
    List<Long> afterResult = luceneDatasource.search("ドメイン");

    // expect
    assertThat(beforeResult.get(0)).isEqualTo(1L);
    assertThat(afterResult.size()).isEqualTo(0);
  }

  @Test
  public void deleteAll() throws Exception {
    // when
    Domain domain1 = new Domain(
      1L,
      "ドメイン名1",
      "フォーマット",
      "説明",
      "存在理由",
      new Date(),
      new Date()
    );
    Domain domain2 = new Domain(
      2L,
      "ドメイン名2",
      "フォーマット",
      "説明",
      "存在理由",
      new Date(),
      new Date()
    );

    // do
    luceneDatasource.regist(domain1);
    luceneDatasource.regist(domain2);
    List<Long> beforeDeleteAll = luceneDatasource.search("ドメイン");
    luceneDatasource.deleteAll();
    List<Long> subject = luceneDatasource.search("ドメイン");
    luceneDatasource.regist(domain1);
    luceneDatasource.regist(domain2);
    List<Long> afterDeleteAll = luceneDatasource.search("ドメイン");

    // expect
    assertThat(beforeDeleteAll.size()).isEqualTo(2);
    assertThat(subject.size()).isEqualTo(0);
    assertThat(afterDeleteAll.size()).isEqualTo(2);
  }
}
