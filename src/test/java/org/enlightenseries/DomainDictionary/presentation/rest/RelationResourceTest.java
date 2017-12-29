package org.enlightenseries.DomainDictionary.presentation.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DomainDictionaryApplication.class)
public class RelationResourceTest {

  private MockMvc relationResourceMockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    RelationResource relationResource = new RelationResource();
    relationResourceMockMvc = MockMvcBuilders.standaloneSetup(relationResource).build();
  }

  @Test
  public void createRelation() throws Exception {
    Relation relation = new Relation();
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(relation);

    relationResourceMockMvc.perform(MockMvcRequestBuilders
        .post("/api/relations")
        .content(json)
      )
      .andExpect(status().isCreated());
  }
}
