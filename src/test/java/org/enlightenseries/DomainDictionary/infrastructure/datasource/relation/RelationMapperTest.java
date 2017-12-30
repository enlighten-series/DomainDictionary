package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setRemoveAssertJRelatedElementsFromStackTrace;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RelationMapperTest {

  @Autowired
  private RelationMapper relationMapper;

  @Before
  public void setup() {
  }

  @Test
  public void insertOne() {
    // when
    Relation assertData = new Relation();

    // try
    relationMapper.insert(assertData);
    Relation subject = relationMapper.select(assertData.getId());

    // expect
    assertThat(subject.getId().toString()).isEqualTo(assertData.getId().toString());
  }
}
