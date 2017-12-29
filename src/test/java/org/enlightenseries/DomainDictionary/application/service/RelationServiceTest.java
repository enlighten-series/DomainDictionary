package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DomainDictionaryApplication.class)
public class RelationServiceTest {

  @Autowired
  private RelationService relationService;

  @MockBean
  private RelationRepository relationRepositoryMock;

  @MockBean
  private DomainToRelationRepository domainToRelationRepositoryMock;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void createNewRelation() {
    Long expectSourceId = 1L;
    Long expectDestinationId = 2L;
    Relation expectRelation = new Relation();
    DomainToRelation expectSource = new DomainToRelation(UUID.randomUUID(), expectSourceId, expectRelation.getId());
    DomainToRelation expectDestination = new DomainToRelation(UUID.randomUUID(), expectDestinationId, expectRelation.getId());

    // do
    relationService.createNewRelation(1L, 2L);

    verify(relationRepositoryMock, times(1)).register(expectRelation);
    verify(domainToRelationRepositoryMock, times(1)).register(expectSource);
    verify(domainToRelationRepositoryMock, times(1)).register(expectDestination);
  }
}
