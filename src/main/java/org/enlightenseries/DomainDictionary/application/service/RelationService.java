package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

  /**
   * １つの関連を取得します。
   *
   * @param id 関連のUUID
   * @return 関連
   */
  public Relation findBy(UUID id) {
    return relationRepository.findBy(id);
  }

  /**
   * 指定された関連を削除します。
   *
   * @param relationId 関連のID（DomainToRelationのIDではない）
   * @return 削除に成功したRelation
   */
  public Relation deleteRelation(UUID relationId) {
    Relation target = findBy(relationId);

    domainToRelationRepository.deleteRelationBy(relationId);

    relationRepository.delete(relationId);

    return target;
  }
}
