package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.application.service.RelationService;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.junit.Before;
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
      .andExpect(status().isCreated());

    verify(relationServiceMock, times(1)).createNewRelation(1L, 2L);
  }
}
