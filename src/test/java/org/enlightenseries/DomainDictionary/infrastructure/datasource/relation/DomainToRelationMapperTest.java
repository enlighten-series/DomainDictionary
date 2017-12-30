package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainToRelationMapperTest {

  @Autowired
  private DomainToRelationMapper domainToRelationMapper;

  @Before
  public void setup() {
  }

  @Test
  public void insertOneRelationSet() {
    // when
    Long expectSourceId = 1L;
    Long expectDestinationId = 2L;
    Relation expectRelation = new Relation();
    DomainToRelation expectSource = new DomainToRelation(expectSourceId, expectRelation.getId());
    DomainToRelation expectDestination = new DomainToRelation(expectDestinationId, expectRelation.getId());

    // try
    domainToRelationMapper.insert(expectSource);
    domainToRelationMapper.insert(expectDestination);
    List<DomainToRelation> fromSource = domainToRelationMapper.selectDest(expectSourceId);
    List<DomainToRelation> fromDestination = domainToRelationMapper.selectDest(expectDestinationId);

    // expect
    assertThat(fromSource.size()).isEqualTo(1);
    assertThat(fromSource.get(0).getDomainId()).isEqualTo(expectDestinationId);
    assertThat(fromSource.get(0).getRelationId().toString()).isEqualTo(expectDestination.getRelationId().toString());
    assertThat(fromDestination.size()).isEqualTo(1);
    assertThat(fromDestination.get(0).getDomainId()).isEqualTo(expectSourceId);
    assertThat(fromDestination.get(0).getRelationId().toString()).isEqualTo(expectDestination.getRelationId().toString());
  }
}
