package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DomainDictionaryApplication.class)
public class DomainServiceTest {

  @Autowired
  private DomainService domainService;

  @MockBean
  private DomainRepository domainRepository;

  private Domain assertData;

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
  }

  @Test
  public void findOneDomain() throws Exception {
    when(domainRepository.findBy(1L)).thenReturn(assertData);

    Domain subject = this.domainService.findBy(1L);

    assertThat(subject.getId()).isEqualTo(assertData.getId());
    assertThat(subject.getName()).isEqualTo(assertData.getName());
    assertThat(subject.getFormat()).isEqualTo(assertData.getFormat());
    assertThat(subject.getDescription()).isEqualTo(assertData.getDescription());
    assertThat(subject.getExistential()).isEqualTo(assertData.getExistential());
    assertThat(subject.getCreated()).isEqualTo(assertData.getCreated());
    assertThat(subject.getUpdated()).isEqualTo(assertData.getUpdated());
  }

}
