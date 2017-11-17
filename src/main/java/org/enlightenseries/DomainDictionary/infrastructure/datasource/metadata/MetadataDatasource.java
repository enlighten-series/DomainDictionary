package org.enlightenseries.DomainDictionary.infrastructure.datasource.metadata;

import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;
import org.enlightenseries.DomainDictionary.domain.model.metadata.MetadataRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MetadataDatasource implements MetadataRepository{

  private MetadataMapper metadataMapper;

  public MetadataDatasource(
    MetadataMapper _metadataMapper
  ) {
    this.metadataMapper = _metadataMapper;
  }

  @Override
  public Metadata findByKey(String key) {
    return this.metadataMapper.select(key);
  }

  @Override
  public void registor(Metadata metadata) {
    this.metadataMapper.insert(metadata);
  }

  @Override
  public void update(Metadata metadata) {
    this.metadataMapper.update(metadata);
  }
}
