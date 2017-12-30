package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DomainDictionaryApplication.class)
public class RelationServiceTest {

  private RelationService relationService;

  @MockBean
  private RelationRepository relationRepositoryMock;

  @MockBean
  private DomainToRelationRepository domainToRelationRepositoryMock;

  @Captor
  private ArgumentCaptor<Relation> relationCaptor;

  @Captor
  private ArgumentCaptor<DomainToRelation> domainToRelationCaptor;

  @Before
  public void setUp() throws Exception {
    relationService = spy(new RelationService(relationRepositoryMock, domainToRelationRepositoryMock));
  }

  @Test
  public void createNewRelation() {
    // when
    Long expectSourceId = 1L;
    Long expectDestinationId = 2L;
    Relation expectRelation = new Relation();
    DomainToRelation expectSource = new DomainToRelation(expectSourceId, expectRelation.getId());
    DomainToRelation expectDestination = new DomainToRelation(expectDestinationId, expectRelation.getId());

    // do
    relationService.createNewRelation(expectSourceId, expectDestinationId);

    // expect
    verify(relationRepositoryMock, times(1)).register(relationCaptor.capture());
    verify(domainToRelationRepositoryMock, times(2)).register(domainToRelationCaptor.capture());

    Relation relation = relationCaptor.getValue();
    List<DomainToRelation> domainToRelations = domainToRelationCaptor.getAllValues();

    assertThat(domainToRelations.size()).isEqualTo(2);

    DomainToRelation first = domainToRelations.get(0);
    DomainToRelation second = domainToRelations.get(1);

    assertThat(first.getDomainId()).isEqualTo(expectSourceId);
    assertThat(second.getDomainId()).isEqualTo(expectDestinationId);
    assertThat(first.getRelationId().toString()).isEqualTo(second.getRelationId().toString());

    assertThat(first.getRelationId().toString()).isEqualTo(relation.getId().toString());
    assertThat(second.getRelationId().toString()).isEqualTo(relation.getId().toString());
  }

  @Test
  public void findRelation() throws Exception {
    // when
    Relation assertData = new Relation();
    when(relationRepositoryMock.findBy(assertData.getId())).thenReturn(new Relation(UUID.fromString(assertData.getId().toString())));

    // try
    Relation subject = relationService.findBy(assertData.getId());

    // expect
    assertThat(subject.getId().toString()).isEqualTo(assertData.getId().toString());
  }

  @Test
  public void deleteRelation() throws Exception {
    // when
    Relation assertData = new Relation();

    // try
    relationService.deleteRelation(assertData.getId());

    // expect
    verify(relationRepositoryMock, times(1)).delete(assertData.getId());
    verify(domainToRelationRepositoryMock, times(1)).deleteRelationBy(assertData.getId());
  }
}
