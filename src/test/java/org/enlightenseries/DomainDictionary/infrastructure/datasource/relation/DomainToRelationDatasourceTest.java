package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.enlightenseries.DomainDictionary.DomainDictionaryApplication;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainToRelationDatasourceTest {

  private DomainToRelationDatasource domainToRelationDatasource;

  @MockBean
  private DomainToRelationMapper domainToRelationMapperMock;

  @Before
  public void setup() {
    domainToRelationDatasource = spy(new DomainToRelationDatasource(domainToRelationMapperMock));
  }

  @Test
  public void registerDomainToRelation() {
    // when
    DomainToRelation assertData = new DomainToRelation(1L, UUID.randomUUID());

    // try
    domainToRelationDatasource.register(assertData);

    // expect
    verify(domainToRelationDatasource, times(1)).register(assertData);
  }
}
