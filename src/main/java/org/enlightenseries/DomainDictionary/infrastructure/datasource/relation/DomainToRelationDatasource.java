package org.enlightenseries.DomainDictionary.infrastructure.datasource.relation;

import org.apache.commons.csv.CSVParser;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DomainToRelationDatasource implements DomainToRelationRepository {

  private DomainToRelationMapper domainToRelationMapper;

  public DomainToRelationDatasource(
    DomainToRelationMapper _domainToRelationMapper
  ) {
    this.domainToRelationMapper = _domainToRelationMapper;
  }

  public List<DomainToRelation> selectDestinations(Long sourceDomainId) {
    return this.domainToRelationMapper.selectDest(sourceDomainId);
  }

  public void register(DomainToRelation domainToRelation) {
    this.domainToRelationMapper.insert(domainToRelation);
  }

  public void deleteRelationBy(UUID relationId) {
    this.domainToRelationMapper.deleteRelationBy(relationId);
  }

  public void createTable() {
    this.domainToRelationMapper.createTable();
  }

  public void import_0_2_X(CSVParser parser) throws Exception {
    domainToRelationMapper.deleteAllForImport();

    // TODO: インポート実行
  }
}
