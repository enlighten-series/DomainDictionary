package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.application.service.RelationService;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DomainDictionaryApplication.class)
public class RelationResourceTest {

  @MockBean
  private RelationService relationServiceMock;

  private MockMvc relationResourceMockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    RelationResource relationResource = new RelationResource(relationServiceMock);
    relationResourceMockMvc = MockMvcBuilders.standaloneSetup(relationResource).build();
  }

  @Test
  public void createRelation() throws Exception {
    String json = "{" +
      "\"source\": 1," +
      "\"destination\": 2" +
      "}";
    Relation assertData = new Relation();

    when(relationServiceMock.createNewRelation(anyLong(), anyLong())).thenReturn(assertData);

    // do
    relationResourceMockMvc.perform(MockMvcRequestBuilders
      .post("/api/relations")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
    )
    .andExpect(status().isCreated())
    .andExpect(header().string("Location", "/api/relations/" + assertData.getId().toString()))
    .andExpect(jsonPath("$.id").value(Matchers.equalTo(assertData.getId().toString())))
    .andExpect(jsonPath("$.source").value(Matchers.equalTo(1)))
    .andExpect(jsonPath("$.destination").value(Matchers.equalTo(2)));

    verify(relationServiceMock, times(1)).createNewRelation(1L, 2L);
  }

  @Test
  public void createRelationMissParameterName() throws Exception {
    // when
    String json = "{" +
      "\"sourceId\": 1," +
      "\"destinationId\": 2" +
      "}";
    Relation assertData = new Relation();

    when(relationServiceMock.createNewRelation(anyLong(), anyLong())).thenReturn(assertData);

    // try
    relationResourceMockMvc.perform(MockMvcRequestBuilders
      .post("/api/relations")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
    )
    // expect
    .andExpect(status().isBadRequest());

    // TODO: jsonデータもassert

    verify(relationServiceMock, times(0)).createNewRelation(1L, 2L);
  }

  @Test
  public void deleteRelation() throws Exception {
    // when
    Relation assertData = new Relation();
    when(relationServiceMock.deleteRelation(assertData.getId())).thenReturn(assertData);

    // try
    relationResourceMockMvc.perform(MockMvcRequestBuilders
      .delete("/api/relations/" + assertData.getId().toString())
    )
    .andExpect(status().isOk());

    // expect
    verify(relationServiceMock, times(1)).deleteRelation(assertData.getId());
  }
}
