package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DomainDictionaryApplication.class)
public class DomainMapperTest {

  @Autowired
  DomainMapper domainMapper;

  Domain assertData;

  @Before
  public void setup() {
    assertData = new Domain();
    assertData.setName("ドメイン名");
    assertData.setFormat("フォーマット");
    assertData.setDescription("説明書きです");
    assertData.setExistential(
      "# こんにちは¥r¥n" +
      "¥r" +
      "- 世界" +
      "`こーど`"
    );
    assertData.setCreated(new Date("2016/2/29 23:59:59"));
    assertData.setUpdated(new Date("2017/12/31 12:34:56"));

    this.domainMapper.insert(assertData);
  }

  @Test
  public void selectOne() throws Exception {
    Domain subject = this.domainMapper.select(assertData.getId());

    assertThat(subject.getId()).isEqualTo(assertData.getId());
    assertThat(subject.getName()).isEqualTo(assertData.getName());
    assertThat(subject.getFormat()).isEqualTo(assertData.getFormat());
    assertThat(subject.getDescription()).isEqualTo(assertData.getDescription());
    assertThat(subject.getExistential()).isEqualTo(assertData.getExistential());
    assertThat(subject.getCreated()).isEqualTo(assertData.getCreated());
    assertThat(subject.getUpdated()).isEqualTo(assertData.getUpdated());
  }

}
