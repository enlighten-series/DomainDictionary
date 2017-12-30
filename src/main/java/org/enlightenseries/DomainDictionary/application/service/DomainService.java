package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.domain.RelatedDomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService {
  DomainRepository domainRepository;
  DomainToRelationRepository domainToRelationRepository;

  public DomainService(
    DomainRepository _domainRepository,
    DomainToRelationRepository _domainToRelationRepository
  ) {
    this.domainRepository = _domainRepository;
    this.domainToRelationRepository = _domainToRelationRepository;
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

  public void delete(Long id) {
    this.domainRepository.delete(id);
  }
}
