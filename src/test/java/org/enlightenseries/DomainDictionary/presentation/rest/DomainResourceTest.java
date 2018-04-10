package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.application.service.DomainService;
import org.enlightenseries.DomainDictionary.application.usecase.DomainUsecase;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.domain.RelatedDomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DomainDictionaryApplication.class)
public class DomainResourceTest {

  @MockBean
  private DomainService domainService;

  @MockBean
  private DomainUsecase domainUsecase;

  private MockMvc domainResourceMockMvc;

  /**
   * assert data
   */
  private int assertJsonDomainId;
  private DomainDetail assertDomain;
  private List<RelatedDomainSummary> assertRelatedDomains;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    DomainResource domainResource = new DomainResource(domainService, domainUsecase);
    domainResourceMockMvc = MockMvcBuilders.standaloneSetup(domainResource).build();
  }

  @Before
  public void createAssertData() {
    assertJsonDomainId = 1;

    assertDomain = new DomainDetail(new Domain());
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

    Relation relation = new Relation();
    assertRelatedDomains = new ArrayList<>();
    assertRelatedDomains.add(new RelatedDomainSummary(
      relation.getId(),
      2L,
      "related_domain%$\"'"
    ));
    assertRelatedDomains.add(new RelatedDomainSummary(
      relation.getId(),
      3L,
      "関連ドメイン"
    ));
  }

  @Test
  public void getOneDomainExist() throws Exception {
    // when
    when(domainService.findDomainDetailBy(1L)).thenReturn(assertDomain);
    when(domainService.findRelatedDomains(1L)).thenReturn(assertRelatedDomains);

    // try
    domainResourceMockMvc.perform(get("/api/domains/1"))

    // expect
      .andExpect(status().isOk())

      .andExpect(jsonPath("$.id").value(Matchers.anyOf(
        Matchers.equalTo((Number) assertDomain.getId()),
        Matchers.equalTo((Number) assertDomain.getId().intValue())
      )))
      .andExpect(jsonPath("$.name").value(Matchers.equalTo(assertDomain.getName())))
      .andExpect(jsonPath("$.format").value(Matchers.equalTo(assertDomain.getFormat())))
      .andExpect(jsonPath("$.description").value(Matchers.equalTo(assertDomain.getDescription())))
      .andExpect(jsonPath("$.existential").value(Matchers.equalTo(assertDomain.getExistential())))
      .andExpect(jsonPath("$.created").value(Matchers.equalTo(assertDomain.getCreated().getTime())))
      .andExpect(jsonPath("$.updated").value(Matchers.equalTo(assertDomain.getUpdated().getTime())))

      .andExpect(jsonPath("$.relatedDomains[0].relationId").value(Matchers.equalTo(assertRelatedDomains.get(0).getRelationId().toString())))
      .andExpect(jsonPath("$.relatedDomains[0].domainId").value(Matchers.anyOf(
        Matchers.equalTo(assertRelatedDomains.get(0).getDomainId()),
        Matchers.equalTo(assertRelatedDomains.get(0).getDomainId().intValue())
      )))
      .andExpect(jsonPath("$.relatedDomains[0].name").value(assertRelatedDomains.get(0).getName()))
      .andExpect(jsonPath("$.relatedDomains[1].relationId").value(Matchers.equalTo(assertRelatedDomains.get(1).getRelationId().toString())))
      .andExpect(jsonPath("$.relatedDomains[1].domainId").value(Matchers.anyOf(
        Matchers.equalTo(assertRelatedDomains.get(1).getDomainId()),
        Matchers.equalTo(assertRelatedDomains.get(1).getDomainId().intValue())
      )))
      .andExpect(jsonPath("$.relatedDomains[1].name").value(assertRelatedDomains.get(1).getName()));
  }

  @Test
  public void getOneDomainNotFound() throws Exception {
    when(domainService.findBy(1L)).thenReturn(assertDomain);
    when(domainService.findRelatedDomains(1L)).thenReturn(assertRelatedDomains);

    domainResourceMockMvc.perform(get("/api/domains/2"))
      .andExpect(status().isNotFound());
  }

  @Test
  public void createDomain() throws Exception {
    // when
    Long expectId = 1L;
    String expectName = "なまえです";
    String expectDescription = "説明です";
    String expectExistential = "存在しています";
    String json = "{" +
      "\"name\": \"" + expectName + "\"," +
      "\"description\": \"" + expectDescription + "\"," +
      "\"existential\": \"" + expectExistential + "\"" +
      "}";
    Domain expectDomain = new Domain();
    expectDomain.setId(expectId);
    expectDomain.setName(expectName);
    expectDomain.setDescription(expectDescription);
    expectDomain.setExistential(expectExistential);
    DomainDetail expectDetail = new DomainDetail(expectDomain);

    when(domainUsecase.register(anyObject())).thenReturn(expectDetail);

    // try
    domainResourceMockMvc.perform(MockMvcRequestBuilders
      .post("/api/domains")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
    )
      // expect
      .andExpect(status().isCreated());

    // TODO: jsonデータもassert
  }

  @Test
  public void createDomainBadParameter() throws Exception {
    // when
    String json = "{" +
      "\"description\": \"こんにちは\"," +
      "\"existential\": \"こんにちは\"" +
      "}";

    // try
    domainResourceMockMvc.perform(MockMvcRequestBuilders
      .post("/api/domains")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
    )
    // expect
    .andExpect(status().isBadRequest());

    // TODO: jsonデータもassert
  }

  @Test
  public void keywordSearch() throws Exception {
    // when
    List<DomainSummary> assertData = new ArrayList<>();
    assertData.add(new DomainSummary(1L, "お客様名称"));
    assertData.add(new DomainSummary(2L, "お客様住所"));
    String subjectKeyword = "お客様";
    when(domainUsecase.keywordSearch(subjectKeyword)).thenReturn(assertData);

    // try
    domainResourceMockMvc.perform(
      get("/api/domains/_search")
        .param("keyword", subjectKeyword)
    )

      // expect
      .andExpect(status().isOk())

      .andExpect(jsonPath("$[0].id").value(Matchers.anyOf(
        Matchers.equalTo((Number) assertData.get(0).getId()),
        Matchers.equalTo((Number) assertData.get(0).getId().intValue())
      )))
      .andExpect(jsonPath("$[0].name").value(Matchers.equalTo(assertData.get(0).getName())))

      .andExpect(jsonPath("$[1].id").value(Matchers.anyOf(
        Matchers.equalTo((Number) assertData.get(1).getId()),
        Matchers.equalTo((Number) assertData.get(1).getId().intValue())
      )))
      .andExpect(jsonPath("$[1].name").value(Matchers.equalTo(assertData.get(1).getName())))
    ;

    // expect method call
    verify(domainUsecase, times(1)).keywordSearch(subjectKeyword);
  }

}
