package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RelationService {

  private RelationRepository relationRepository;
  private DomainToRelationRepository domainToRelationRepository;

  public RelationService(
    RelationRepository _relationRepository,
    DomainToRelationRepository _domainToRelationRepository
  ) {
    this.relationRepository = _relationRepository;
    this.domainToRelationRepository = _domainToRelationRepository;
  }

  /**
   * 新しい関連を作成します。
   *
   * @param sourceDomainId 関連元ドメインのID
   * @param destinationDomainId 関連先ドメインのID
   * @return 新しいRelation
   */
  @Transactional
  public Relation createNewRelation(Long sourceDomainId, Long destinationDomainId) {
    Relation newRelation = new Relation();
    DomainToRelation source = new DomainToRelation(sourceDomainId, newRelation.getId());
    DomainToRelation destination = new DomainToRelation(destinationDomainId, newRelation.getId());

    relationRepository.register(newRelation);
    domainToRelationRepository.register(source);
    domainToRelationRepository.register(destination);

    return newRelation;
  }
}
