package org.enlightenseries.DomainDictionary.domain.model.metadata;

public interface MetadataRepository {
  Metadata findByKey(String key);

  void register(Metadata metadata);

  void update(Metadata metadata);

  void createTable();
}
