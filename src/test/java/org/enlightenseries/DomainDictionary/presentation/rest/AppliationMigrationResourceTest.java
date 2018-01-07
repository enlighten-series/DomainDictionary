package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.application.usecase.ApplicationMigration;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppliationMigrationResourceTest {

  @MockBean
  private ApplicationMigration applicationMigrationUsecaseMock;

  private MockMvc applicationMigrationResourceMockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    ApplicationMigrationResource applicationMigrationResource = new ApplicationMigrationResource();
    applicationMigrationResourceMockMvc = MockMvcBuilders.standaloneSetup(applicationMigrationResource).build();
  }

  @Test
  public void getStatusNotGenerated() throws Exception {
    // when
    when(applicationMigrationUsecaseMock.isGeneratingExportFile()).thenReturn(false);
    when(applicationMigrationUsecaseMock.getExportFileGeneratedDate()).thenReturn(null);

    // try
    applicationMigrationResourceMockMvc.perform(get("/api/application-migration/export/status"))

      // expect
      .andExpect(status().isOk())

      .andExpect(jsonPath("$.nowGenerating").value(Matchers.equalTo(false)))
      .andExpect(jsonPath("$.latestGeneratedDate").value(Matchers.equalTo("")));
  }

  @Test
  public void requestStartGenerating() {

  }

  @Test
  public void downloadNotExistGeneratedFile() {

  }

  @Test
  public void getStatusGenerated() {

  }

  @Test
  public void downloadExistGeneratedFile() {

  }

  @Test
  public void requetReStartGenerating() {

  }

}
