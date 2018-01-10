package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.domain.RelatedDomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService {
  DomainRepository domainRepository;
  DomainToRelationRepository domainToRelationRepository;
  RelationRepository relationRepository;

  public DomainService(
    DomainRepository _domainRepository,
    DomainToRelationRepository _domainToRelationRepository,
    RelationRepository _relationRepository
  ) {
    this.domainRepository = _domainRepository;
    this.domainToRelationRepository = _domainToRelationRepository;
    this.relationRepository = _relationRepository;
  }

  public List<Domain> list() {
    return this.domainRepository.list();
  }

  public Domain findBy(Long id) {
    return this.domainRepository.findBy(id);
  }

  public List<RelatedDomainSummary> findRelatedDomains(Long sourceDomainId) {
    List<RelatedDomainSummary> result = new ArrayList<>();
    List<DomainToRelation> relatedIds = this.domainToRelationRepository.selectDestinations(sourceDomainId);

    relatedIds.forEach(d2r -> {
      DomainSummary related = domainRepository.findDomainSummaryBy(d2r.getDomainId());
      result.add(
        new RelatedDomainSummary(
          d2r.getRelationId(),
          related.getId(),
          related.getName()
        )
      );
    });

    return result;
  }

  public DomainSummary findDomainSummaryBy(Long id) {
    return this.domainRepository.findDomainSummaryBy(id);
  }

  public void register(Domain domain) {
    this.domainRepository.register(domain);
  }

  public void update(Long id, Domain domain) {
    this.domainRepository.update(id, domain);
  }

  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) throws ApplicationException {
    // 関連を削除
    List<RelatedDomainSummary> relations = this.findRelatedDomains(id);
    relations.forEach(relatedDomainSummary -> {
      domainToRelationRepository.deleteRelationBy(relatedDomainSummary.getRelationId());
      relationRepository.delete(relatedDomainSummary.getRelationId());
    });

    // ドメイン本体を削除
    this.domainRepository.delete(id);
  }
}
