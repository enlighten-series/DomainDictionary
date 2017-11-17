package org.enlightenseries.DomainDictionary.domain.model.metadata;

public interface MetadataRepository {
  Metadata findByKey(String key);

  void registor(Metadata metadata);

  void update(Metadata metadata);
}
