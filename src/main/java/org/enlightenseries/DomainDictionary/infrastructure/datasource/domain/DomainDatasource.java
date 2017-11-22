package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain;

import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DomainDatasource implements DomainRepository {
  DomainMapper domainMapper;

  public DomainDatasource(
    DomainMapper _domainMapper
  ) {
    this.domainMapper = _domainMapper;
  }

  public List<Domain> list() {
    return this.domainMapper.selectAll();
  }

  public Domain findBy(Long id) {
    return this.domainMapper.select(id);
  }

  public void register(Domain domain) {
    this.domainMapper.insert(domain);
  }

  public void update(Long id, Domain domain) {
    this.domainMapper.update(id, domain);
  }

  public void delete(Long id) {
    this.domainMapper.delete(id);
  }

  public void createTable() {
    this.domainMapper.createTable();
  }
}
