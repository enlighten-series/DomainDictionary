package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainService {
  DomainRepository domainRepository;

  public DomainService(
    DomainRepository _domainRepository
  ) {
    this.domainRepository = _domainRepository;
  }

  public List<Domain> list() {
    return this.domainRepository.list();
  }

  public Domain findBy(Long id) {
    return this.domainRepository.findBy(id);
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
