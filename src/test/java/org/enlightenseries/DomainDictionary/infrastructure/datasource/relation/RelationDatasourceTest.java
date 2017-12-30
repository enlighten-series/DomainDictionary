package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.relation.RelationDatasource;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.relation.RelationMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RelationDatasourceTest {

  private RelationDatasource relationDatasource;

  @MockBean
  private RelationMapper relationMapperMock;

  @Before
  public void setup() {
    relationDatasource = spy(new RelationDatasource(relationMapperMock));
  }

  @Test
  public void registerRelation() throws Exception {
    // when
    Relation assertData = new Relation();

    // try
    relationDatasource.register(assertData);

    // expect
    verify(relationDatasource, times(1)).register(assertData);
  }

  @Test
  public void findOne() throws Exception {
    // when
    Relation assertData = new Relation();
    when(relationMapperMock.select(assertData.getId())).thenReturn(new Relation(UUID.fromString(assertData.getId().toString())));

    // try
    Relation subject = relationDatasource.findBy(assertData.getId());

    // expect
    verify(relationMapperMock, times(1)).select(assertData.getId());
    assertThat(subject.getId().toString()).isEqualTo(assertData.getId().toString());
  }
}
