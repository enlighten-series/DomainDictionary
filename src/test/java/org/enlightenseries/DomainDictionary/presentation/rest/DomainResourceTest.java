package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.application.service.DomainService;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DomainDictionaryApplication.class)
public class DomainResourceTest {

  @MockBean
  private DomainService domainService;

  private MockMvc domainResourceMockMvc;

  /**
   * assert data
   */
  private int assertJsonDomainId;
  private Domain assertDomain;
  private List<DomainSummary> assertRelatedDomains;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    DomainResource domainResource = new DomainResource(domainService);
    domainResourceMockMvc = MockMvcBuilders.standaloneSetup(domainResource).build();
  }

  @Before
  public void createAssertData() {
    assertJsonDomainId = 1;

    assertDomain = new Domain();
    assertDomain.setId(1L);
    assertDomain.setName("ドメイン名");
    assertDomain.setFormat("フォーマット");
    assertDomain.setDescription("説明書きです");
    assertDomain.setExistential(
      "# こんにちは¥r¥n" +
        "¥r" +
        "- 世界" +
        "`こーど`"
    );
    assertDomain.setCreated(new Date("2016/2/29 23:59:59"));
    assertDomain.setUpdated(new Date("2017/12/31 12:34:56"));

    assertRelatedDomains = new ArrayList<>();
    assertRelatedDomains.add(new DomainSummary(
      2L,
      "related_domain%$\"'"
    ));
    assertRelatedDomains.add(new DomainSummary(
      3L,
      "関連ドメイン"
    ));
  }

  @Test
  public void getOneDomain() throws Exception {
    when(domainService.findBy(1L)).thenReturn(assertDomain);
    when(domainService.findRelatedDomains(1L)).thenReturn(assertRelatedDomains);

    domainResourceMockMvc.perform(get("/api/domains/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(Matchers.anyOf(
        Matchers.equalTo((Number) assertDomain.getId()),
        Matchers.equalTo((Number) assertDomain.getId().intValue())
      )));
  }

}
