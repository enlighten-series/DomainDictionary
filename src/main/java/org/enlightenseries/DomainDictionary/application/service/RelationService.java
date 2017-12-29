package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.springframework.stereotype.Service;

@Service
public class RelationService {

  /**
   * 新しい関連を作成します。
   *
   * @param sourceDomainId 関連元ドメインのID
   * @param destinationDomainId 関連先ドメインのID
   * @return 新しいRelation
   */
  public Relation createNewRelation(Long sourceDomainId, Long destinationDomainId) {
    Relation newRelation = new Relation();

    return newRelation;
  }
}
